# POOALAB Eventos — Inscrição e certificados de eventos acadêmicos

Sistema de console em Java para organizar palestras e minicursos acadêmicos. O organizador cria o evento; o participante se inscreve (o sistema bloqueia duplicidade, vaga esgotada e prazo encerrado); a presença é registrada; ao encerrar o evento, os certificados de quem tem direito são emitidos automaticamente; e qualquer pessoa confere um certificado pelo código de verificação.

> **Estado atual: MVP completo.** Fluxo inscrição → presença → certificado funcionando no menu de console, com 47 testes automatizados passando (`mvn clean test`). Para rodar: `mvn -q compile exec:java` (detalhes em [Instalação e execução](#instalação-e-execução)). Quem fez cada parte: [docs/divisao-da-equipe.md](docs/divisao-da-equipe.md).

## Sumário

- [Problema, público e proposta de valor](#problema-público-e-proposta-de-valor)
- [Integrantes e turma](#integrantes-e-turma)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e execução](#instalação-e-execução)
- [Testes](#testes)
- [Dados de demonstração](#dados-de-demonstração)
- [Roteiro para reproduzir o fluxo principal](#roteiro-para-reproduzir-o-fluxo-principal)
- [Arquitetura e conceitos de POO](#arquitetura-e-conceitos-de-poo)
- [Como a equipe trabalha](#como-a-equipe-trabalha)
- [Limitações e próximos passos](#limitações-e-próximos-passos)
- [Fontes, licenças, terceiros e uso de IA](#fontes-licenças-terceiros-e-uso-de-ia)
- [Documentos da entrega](#documentos-da-entrega)

## Problema, público e proposta de valor

**Problema.** Para estudantes que participam de palestras e minicursos acadêmicos, que não têm certeza de que a presença foi registrada e esperam meses pelo certificado, a consequência é insegurança e atraso para comprovar horas complementares. Hoje a entrada é por QR code, mas o certificado é liberado depois pela organização, sem prazo claro.

**Público.** Organizadores de eventos acadêmicos (professores, coordenação, ligas, centros acadêmicos) e estudantes participantes.

**Proposta de valor.** O certificado sai sozinho no momento em que o evento é encerrado, só para quem cumpriu a regra de presença, e cada certificado traz um código que qualquer pessoa pode conferir.

Nas entrevistas, os estudantes disseram que a entrada nos eventos é tranquila; a insegurança está em confirmar a presença e receber o certificado. Por isso o MVP foca em presença confirmada e certificado emitido automaticamente.

> O sistema institucional da faculdade é só a "alternativa atual". Nada dele foi copiado e todos os dados do projeto são fictícios.

## Integrantes e turma

**Turma:** ESW-NOT-PIT-4S-T1.

| Nome completo | Papel | Responsabilidade |
|---|---|---|
| Edenilton Oliveira Mota Filho | Dev A (líder técnico) | Eventos e Observer, inscrição (Chain of Responsibility), relatórios (Streams), revisão dos PRs |
| Vitor Inácio | Dev B | Presença e elegibilidade (Strategy), certificados (Factory Method), emissão e verificação |
| Gilmar Araújo Santos Júnior | Dev C (integrador) | Menu de console, dados de demonstração, montagem e teste do fluxo completo |
| Júlia Lessa Soares Ventura | Pessoa D | Problema e validação com usuários reais (Anexos A e B) |
| Jade Evelyn Silva de Morais | Pessoa E | Pitch, teste de reprodutibilidade, vídeo de contingência, Anexo E e entrega |

Detalhes, cronograma e regras: [docs/divisao-da-equipe.md](docs/divisao-da-equipe.md).

## Tecnologias

| Tecnologia | Versão | Por quê |
|---|---|---|
| Java | 17 (LTS) | Linguagem da disciplina; recursos modernos usados no código, como `Stream.toList()` e pattern matching em `instanceof`. |
| Maven | 3.8 ou superior | Build padrão, gerencia o JUnit e roda os testes com um comando. |
| JUnit | 5.10.2 | Testes automatizados do fluxo principal e das regras. |

Sem frameworks e sem banco de dados: os dados ficam em memória (`RepositorioEmMemoria`). Interface: menu de console.

## Pré-requisitos

- **JDK 17 ou superior** (`java -version` deve mostrar 17+). Download: https://adoptium.net/
- **Maven 3.8+** (`mvn -version`). Download: https://maven.apache.org/download.cgi
- Internet **só na primeira compilação** (o Maven baixa o JUnit e os plugins).

## Instalação e execução

```bash
# 1. Obter o código
git clone https://github.com/EddHzz/pooalab.git
cd pooalab

# 2. Compilar e rodar os testes
mvn clean test

# 3a. Executar direto pelo Maven
mvn -q compile exec:java

# 3b. Ou gerar o .jar e executar
mvn -q clean package
java -jar target/pooalab-eventos-0.1.0.jar
```

No menu, digite **8** para ver a demonstração automática do fluxo completo e **0** para sair.

**Windows:** se os acentos aparecerem trocados no terminal, rode `chcp 65001` antes de executar.

## Testes

```bash
mvn test
```

| Teste | O que verifica | Estado |
|---|---|---|
| `EventoTest`, `ParticipanteTest` | Validações do domínio (vagas, carga, prazo, encerrar duas vezes, igualdade) | Passando |
| `RepositorioEmMemoriaTest` | Salvar, buscar, filtrar, lista imutável | Passando |
| `ServicoEventoTest` | Criar evento, código repetido, Observer avisado ao encerrar | Passando |
| `ServicoInscricaoTest` | Sucesso, duplicidade, vaga esgotada, prazo encerrado, último dia do prazo | Passando |
| `ElegibilidadeTest` | Regra dos 75% do minicurso e seleção da regra por tipo | Passando |
| `ServicoPresencaTest` | Check-in de inscrito, recusa sem inscrição ou com evento encerrado, lista de elegíveis | Passando |
| `FabricaCertificadoTest` | Cada fábrica devolve o tipo certo, texto e formato do código | Passando |
| `EmissorCertificadosTest` | Emite para elegíveis, palestrante e organizador; não emite para quem ficou abaixo de 75% | Passando |
| `ServicoVerificacaoTest` | Verificação por código | Passando |
| `ServicoRelatoriosTest` | Inscritos, presentes e elegíveis (Streams) | Passando |
| `DadosDemonstracaoTest` | Dados fictícios carregados sem duplicar | Passando |
| `MenuConsoleTest` | Roteiro pelo menu, entradas inválidas, demonstração automática | Passando |
| `FluxoPrincipalTest` | Fluxo completo de ponta a ponta | Passando |

Resultado: `Tests run: 47, Failures: 0, Errors: 0, Skipped: 0`.

## Dados de demonstração

Todos fictícios (nomes inventados, e-mails `@exemplo.edu.br`, matrículas `ALU-01`…). Não há login nem credenciais. Detalhes em [docs/guias/dev-c-console-integracao.md](docs/guias/dev-c-console-integracao.md).

| Código | Evento | Tipo | Vagas | Carga | Para mostrar |
|---|---|---|---|---|---|
| PL01 | Introdução a Testes Automatizados | Palestra | 30 | 2h | Fluxo normal |
| MC01 | Minicurso de Git na Prática | Minicurso | 3 | 8h | Vaga esgotada e regra dos 75% |
| PL02 | Carreira em Tecnologia | Palestra | 20 | 2h | Prazo encerrado |

## Roteiro para reproduzir o fluxo principal

Passo a passo completo em [demo/roteiro-demo.md](demo/roteiro-demo.md). A opção **8** do menu executa este resumo automaticamente:

1. Listar eventos (`PL01`, `MC01`, `PL02`).
2. Inscrever `ALU-03` em `MC01` (aceita) e `ALU-04` em `MC01` (**recusada: vagas esgotadas**).
3. Inscrever `ALU-01` em `PL01`, onde já está inscrito (**recusada: duplicada**), e `ALU-01` em `PL02` (**recusada: prazo encerrado**).
4. Check-in no `MC01`: `ALU-01` com 8h, `ALU-02` com 6h e `ALU-03` com 4h.
5. Relatório do `MC01`: 3 inscritos, 3 presentes, 2 elegíveis (mínimo de 75% da carga).
6. Encerrar `MC01`: certificados emitidos automaticamente (2 de participante, 1 de palestrante, 1 de organizador).
7. Verificar um código impresso (encontrado) e o código inventado `CERT-0000-0000` (não encontrado).

## Arquitetura e conceitos de POO

Guia de estudo completo: **[docs/arquitetura-e-conceitos.md](docs/arquitetura-e-conceitos.md)**.

```
br.edu.ucsal.eventos
├── dominio/       Evento, Participante, Inscricao, Presenca, Certificado (abstrata), enums, RegraNegocioException
├── repositorio/   Repositorio<T, ID> (interface) + RepositorioEmMemoria
├── evento/        ServicoEvento (criar/encerrar) + ObservadorEncerramento     -> Observer (sujeito)
├── inscricao/     ValidadorInscricao + Prazo, Vagas, Duplicidade, ServicoInscricao -> Chain of Responsibility
├── presenca/      RegraElegibilidade + Palestra, Minicurso, Seletor, Serviços -> Strategy
├── certificado/   FabricaCertificado + fábricas, EmissorCertificados, Verificacao -> Factory Method + Observer
├── relatorio/     ServicoRelatorios                                            -> Streams e Collections
└── app/           Main (monta tudo), MenuConsole, DadosDemonstracao
```

| Padrão | Onde | Por quê |
|---|---|---|
| Chain of Responsibility | `inscricao/` | Cada validação é um elo; o primeiro que recusa interrompe. Nova regra = nova classe. |
| Strategy | `presenca/` | Palestra: presença simples. Minicurso: ≥ 75% da carga. Sem if/else por tipo. |
| Factory Method | `certificado/` | Mesmo processo de emissão para participante, palestrante e organizador. |
| Observer | `evento/` → `certificado/` | Encerrar o evento dispara a emissão sem acoplar os módulos. |
| SOLID / DIP | todos os serviços | Dependem de interfaces e recebem tudo pelo construtor; só o `Main` conhece classes concretas. |

## Como a equipe trabalha

- **Quem faz o quê, cronograma e regras: [docs/divisao-da-equipe.md](docs/divisao-da-equipe.md).** Guias por papel: [docs/guias/](docs/guias/README.md). Contexto para IA: [CLAUDE.md](CLAUDE.md).
- 3 desenvolvedores (Dev A, B, C) e 2 integrantes em validação (Pessoa D) e pitch/entrega (Pessoa E). Todos fazem commits e todos estudam o código.
- Uma branch por tarefa (ex.: `dev-a/inscricao`, `pessoa-d/validacao`). Merge na `main` só com `mvn test` passando, via Pull Request revisado por outra pessoa.
- Commits pequenos e frequentes de cada integrante (o histórico é evidência do trabalho da equipe).
- Nunca versionar segredos, tokens ou dados pessoais.

## Limitações e próximos passos

**Limitações conhecidas**

- Dados em memória: tudo some ao fechar o programa.
- Só interface de console; sem login e sem controle de quem pode encerrar evento.
- O código de verificação é aleatório, mas não há assinatura criptográfica, e a verificação só funciona dentro da mesma execução.
- Não gera PDF nem envia e-mail.

**Próximos passos**

1. Persistência (arquivo ou banco), trocando só a implementação de `Repositorio`.
2. Certificado em PDF e envio por e-mail.
3. Interface web (por exemplo, com Servlets) reaproveitando os mesmos serviços.

## Fontes, licenças, terceiros e uso de IA

- Componentes de terceiros: JUnit 5 (EPL 2.0) e plugins Maven (Apache 2.0). Nenhum outro código de terceiros.
- Material preexistente: nenhum. O sistema da faculdade é só referência do problema e não foi reaproveitado.
- **Uso de IA:** a estrutura base e os rascunhos de documentação foram gerados com o Claude (Anthropic) a partir do plano da equipe; integrantes também usaram Claude e ChatGPT como apoio em Git, dúvidas e sugestões de código, sempre revisadas e testadas. Detalhes em **[docs/declaracao-ia-terceiros.md](docs/declaracao-ia-terceiros.md)** (Anexo E).

## Documentos da entrega

| Documento | Arquivo |
|---|---|
| Divisão da equipe e cronograma | [docs/divisao-da-equipe.md](docs/divisao-da-equipe.md) |
| Guia de estudo (arquitetura e POO) | [docs/arquitetura-e-conceitos.md](docs/arquitetura-e-conceitos.md) |
| Ficha do problema e validação (Anexos A e B) | [docs/problema-validacao.md](docs/problema-validacao.md) |
| Definição do MVP (Anexo C) | [docs/definicao-mvp.md](docs/definicao-mvp.md) |
| Roteiro do pitch (Anexo D) | [docs/roteiro-pitch.md](docs/roteiro-pitch.md) |
| Pitch final | [docs/pitch.pdf](docs/pitch.pdf) |
| Declaração de IA e terceiros (Anexo E) | [docs/declaracao-ia-terceiros.md](docs/declaracao-ia-terceiros.md) |
| Roteiro de demonstração | [demo/roteiro-demo.md](demo/roteiro-demo.md) |
| Vídeo de contingência | [Assistir no Google Drive](https://drive.google.com/file/d/1tQxGfInGanK05Tin3_x0nqelfiw8MtK8/view?usp=drivesdk) |
