# Dev C: console, dados de demonstração e integração

Você é quem **junta tudo**: monta os objetos no `Main`, faz o menu que será usado ao vivo na mostra, cria os dados fictícios e o teste do fluxo completo. Pode começar **desde o primeiro dia** (o menu e os dados não dependem das outras partes para serem escritos).

Para ver tudo que é seu: `grep -rn "TODO Dev C" src/`

Branches sugeridas: `dev-c/menu`, `dev-c/dados-demo`, `dev-c/fluxo-principal-test`.

## 1. Montagem (raiz de composição)

O `Main` é o único lugar que conhece as classes concretas. Recomendo criar uma classe `app/Aplicacao` que monta tudo e expõe os serviços; o `Main` e o teste de fluxo usam a mesma montagem.

```java
Clock relogio = Clock.systemDefaultZone();

Repositorio<Evento, String> eventos = new RepositorioEmMemoria<>(Evento::getCodigo);
Repositorio<Inscricao, String> inscricoes = new RepositorioEmMemoria<>(Inscricao::getId);
Repositorio<Presenca, String> presencas = new RepositorioEmMemoria<>(Presenca::getId);
Repositorio<Certificado, String> certificados = new RepositorioEmMemoria<>(Certificado::getCodigoVerificacao);

ValidadorInscricao cadeia = new ValidadorPrazo();
cadeia.encadear(new ValidadorVagas(inscricoes)).encadear(new ValidadorDuplicidade(inscricoes));

ServicoEvento servicoEvento = new ServicoEvento(eventos);
ServicoInscricao servicoInscricao = new ServicoInscricao(inscricoes, cadeia, relogio);
ServicoPresenca servicoPresenca = new ServicoPresenca(inscricoes, presencas);
ServicoElegibilidade elegibilidade = new ServicoElegibilidade(presencas, new SeletorRegraElegibilidade());
ServicoRelatorios relatorios = new ServicoRelatorios(inscricoes, presencas, elegibilidade);
ServicoVerificacao verificacao = new ServicoVerificacao(certificados);

GeradorCodigoVerificacao gerador = new GeradorCodigoAleatorio();
servicoEvento.adicionarObservador(new EmissorCertificados(elegibilidade, certificados,
        new FabricaCertificadoParticipante(gerador),
        new FabricaCertificadoPalestrante(gerador),
        new FabricaCertificadoOrganizador(gerador),
        relogio));
```

As fábricas concretas são criadas pelo Dev B. Até elas chegarem, deixe essa parte comentada.

## 2. `MenuConsole`

```
=== POOALAB - Eventos Acadêmicos ===
1. Listar eventos
2. Criar evento
3. Inscrever participante
4. Registrar presença (check-in)
5. Relatório do evento (inscritos, presentes, elegíveis)
6. Encerrar evento e emitir certificados
7. Verificar certificado por código
8. Executar demonstração automática
0. Sair
```

Para a demo não quebrar:

- Cada opção dentro de `try/catch (RegraNegocioException | IllegalArgumentException e)`, mostrando `"Não foi possível: " + e.getMessage()`. O menu **nunca** pode fechar por erro.
- Números com `Integer.parseInt(scanner.nextLine().trim())` dentro de `try/catch (NumberFormatException)`.
- Escolher participante e evento por código ou matrícula (`ALU-01`, `MC01`), listando as opções antes.
- Opção 8: roda o fluxo inteiro sozinha, imprimindo cada passo (contingência do pitch).

## 3. `DadosDemonstracao`

Só dados fictícios (e-mails `@exemplo.edu.br`).

| Código | Evento | Tipo | Vagas | Carga | Prazo |
|---|---|---|---|---|---|
| PL01 | Introdução a Testes Automatizados | Palestra | 30 | 2h | daqui a 7 dias |
| MC01 | Minicurso de Git na Prática | Minicurso | 3 | 8h | daqui a 7 dias |
| PL02 | Carreira em Tecnologia | Palestra | 20 | 2h | ontem ("prazo encerrado") |

Participantes `ALU-01` a `ALU-05` com nomes claramente fictícios, organizadora `ORG-01`, palestrante `PAL-01`. Deixe o MC01 com 2 inscritos (`ALU-01`, `ALU-02`) para mostrar a vaga esgotada ao vivo. Use datas relativas a `LocalDate.now(relogio)`.

Atenção: o PL02 tem prazo no passado, então não dá para inscrever ninguém nele pelo serviço (é justamente o que ele demonstra).

## 4. `FluxoPrincipalTest`

Caminho inteiro em um teste: criar evento, inscrever 2 pessoas, check-in (8h e 4h no minicurso), encerrar, conferir que só a primeira recebeu certificado de participante e que `verificar(codigo)` encontra o certificado. Use `Clock.fixed(...)`.

## 5. Integração

- Faça `git pull` da `main` com frequência e rode o menu a cada merge dos outros.
- Mantenha o `demo/roteiro-demo.md` igual ao seu menu (opções e códigos).
- Ao final, `mvn test` deve mostrar **0 Skipped**.

## O que saber explicar na mostra

- O que é a raiz de composição e por que só o `Main`/`Aplicacao` conhece classes concretas.
- Como o menu trata erros sem cair.
- Por que os dados são fictícios e as datas relativas.
- Como rodar a demonstração automática se algo der errado.
