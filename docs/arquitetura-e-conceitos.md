# Guia de estudo: arquitetura e conceitos de POO do POOALAB

Este guia é para **toda a equipe**. Na mostra, o professor pode perguntar qualquer parte a qualquer integrante, então todo mundo deve conseguir explicar o desenho geral, mesmo que só tenha programado um pedaço.

## 1. Visão geral em uma frase

O organizador cria eventos; participantes se inscrevem (passando por uma **cadeia de validações**); a presença é registrada; ao **encerrar** o evento, um **observador** é avisado e usa **fábricas** para emitir certificados de quem cumpre a **estratégia de elegibilidade** do tipo do evento; qualquer pessoa confere o certificado pelo código.

## 2. Pacotes e dependências

```
                 ┌──────────────┐
                 │     app      │  Main, MenuConsole, DadosDemonstracao
                 │ (monta tudo) │  (único que conhece classes concretas)
                 └──────┬───────┘
      ┌──────────┬──────┼─────────┬────────────┬────────────┐
      ▼          ▼      ▼         ▼            ▼            ▼
  inscricao   presenca  evento  certificado  relatorio  repositorio
      │          │      │  ▲        │            │         ▲
      │          │      │  └────────┘ (implementa ObservadorEncerramento)
      └──────────┴──────┴───────────┴────────────┴─────────┘
                              │ todos dependem de
                              ▼
                          dominio  (Evento, Participante, Inscricao, Presenca, Certificado)
```

Regra de ouro: **as setas apontam para o domínio e para interfaces**. O domínio não depende de ninguém.

## 3. Conceitos de POO e onde aparecem

| Conceito | Onde ver no código | Explicação curta |
|---|---|---|
| **Encapsulamento** | `Evento`, `Participante`, `Presenca` | Campos `private final`, só getters. O construtor valida; não existe objeto inválido. |
| **Abstração / classe abstrata** | `Certificado`, `ValidadorInscricao`, `FabricaCertificado` | Define o que é comum e deixa "buracos" (`abstract`) para as subclasses. |
| **Herança** | `CertificadoParticipante extends Certificado`, `ValidadorVagas extends ValidadorInscricao` | A subclasse reaproveita o comportamento da mãe e completa o que falta. |
| **Polimorfismo** | `regra.ehElegivel(...)`, `observador.eventoEncerrado(...)`, `certificado.gerarTexto()` | O mesmo chamado executa código diferente conforme o objeto real. |
| **Interface** | `Repositorio`, `RegraElegibilidade`, `ObservadorEncerramento`, `GeradorCodigoVerificacao` | Contrato sem implementação; permite trocar a peça sem mexer em quem usa. |
| **Generics** | `Repositorio<T, ID>`, `RepositorioEmMemoria<T, ID>` | Um repositório só para todas as entidades, com checagem de tipo em compilação. |
| **equals/hashCode** | `Participante` (matrícula), `Evento` (código) | Define quando dois objetos são "o mesmo"; necessário para buscas e Maps. |
| **Exceção de negócio** | `RegraNegocioException` | Erro esperado (vaga esgotada) com mensagem para o usuário; o menu captura e segue. |
| **Optional** | `Repositorio.buscarPorId`, `Evento.getPalestrante`, `ServicoVerificacao.verificar` | Deixa explícito que pode não haver valor, evitando `NullPointerException`. |
| **Enum** | `TipoEvento`, `TipoCertificado` | Conjunto fechado de valores; `EnumMap` no seletor de regras. |
| **Lambdas e referências de método** | `Evento::getCodigo`, `regra::ehElegivel`, `i -> i.getEvento().equals(evento)` | Passar comportamento como parâmetro. |
| **Streams e Collections** | `ServicoRelatorios`, `ServicoElegibilidade`, `Repositorio.buscar` | `filter`, `map`, `sorted`, `toList` para os relatórios. |
| **Método default em interface** | `Repositorio.buscar` | Toda implementação ganha o filtro sem reescrever. |

## 4. SOLID no projeto

| Princípio | Exemplo concreto |
|---|---|
| **S** (Responsabilidade única) | Cada validador cuida de uma regra; `ServicoVerificacao` só verifica; `EmissorCertificados` só emite. |
| **O** (Aberto/Fechado) | Nova validação = novo elo na cadeia. Novo tipo de evento = nova `RegraElegibilidade`. Nenhuma classe antiga é editada. |
| **L** (Substituição de Liskov) | Qualquer `Certificado` funciona onde se espera um `Certificado`; qualquer `Repositorio` funciona no lugar do em memória. |
| **I** (Segregação de interfaces) | Interfaces pequenas: `RegraElegibilidade` e `ObservadorEncerramento` têm um método cada. |
| **D** (Inversão de dependência) | Serviços recebem `Repositorio` (interface) e `Clock` pelo construtor; só o `Main` escolhe as classes concretas. |

## 5. Padrões de projeto (bônus POOA)

### Chain of Responsibility (`inscricao/`)

- **Problema:** três motivos independentes para recusar uma inscrição.
- **Solução:** cada motivo é um elo (`ValidadorPrazo`, `ValidadorVagas`, `ValidadorDuplicidade`). O primeiro que recusa interrompe.
- **Pergunta provável:** "E se surgir uma regra nova?" Resposta: cria-se uma subclasse de `ValidadorInscricao` e ela entra na cadeia no `Main`.

### Strategy (`presenca/`)

- **Problema:** a regra de certificado depende do tipo do evento.
- **Solução:** `RegraPalestra` e `RegraMinicurso` implementam `RegraElegibilidade`; o `SeletorRegraElegibilidade` devolve a certa.
- **Pergunta provável:** "Qual a diferença para um if?" Resposta: o if teria de ser editado a cada tipo novo e ficaria espalhado; com Strategy cada regra é testada isoladamente.

### Factory Method (`certificado/`)

- **Problema:** três tipos de certificado com o mesmo processo de emissão.
- **Solução:** `FabricaCertificado.emitir()` (fixo, `final`) chama `criar()` (abstrato); cada fábrica concreta cria o seu tipo.

### Observer (`evento/` + `certificado/`)

- **Problema:** ao encerrar o evento, os certificados devem sair sozinhos, mas o módulo de eventos não deve depender do de certificados.
- **Solução:** `ServicoEvento` mantém uma lista de `ObservadorEncerramento` e avisa todos; `EmissorCertificados` é um deles.

## 6. Por que o `Clock` é injetado?

Regras de prazo dependem de "hoje". Se o código chamasse `LocalDate.now()` direto, um teste que passa hoje poderia falhar amanhã. Com `Clock` no construtor, o programa usa o relógio real e os testes usam `Clock.fixed(...)`.

## 7. Caminho de uma inscrição recusada (para explicar na banca)

1. Menu lê `ALU-03` e `MC01` e chama `servicoInscricao.inscrever(participante, evento)`.
2. `ServicoInscricao` pega a data de hoje do `Clock` e chama `cadeia.validar(...)`.
3. `ValidadorPrazo` passa e repassa; `ValidadorVagas` conta 3 inscrições para 3 vagas e lança `RegraNegocioException("Vagas esgotadas...")`.
4. A exceção sobe até o menu, que mostra a mensagem e volta para as opções. Nada foi salvo.

## 8. Perguntas para treinar

1. Onde o projeto aplica DIP, e o que mudaria para usar um banco de dados?
2. Por que `Certificado` é abstrata e não uma interface?
3. Por que `validar` é `final` em `ValidadorInscricao`?
4. O que acontece se encerrar o mesmo evento duas vezes?
5. Como o sistema sabe que duas inscrições são duplicadas?
6. O que é testado automaticamente e o que não é?
7. Quais as limitações do MVP?
