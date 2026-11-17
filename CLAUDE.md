# POOALAB: Inscrição e certificados de eventos acadêmicos

Contexto para o Claude Code (e para qualquer integrante que chegar agora). Este arquivo fica na raiz do repositório.

## Quem e o quê

- **Disciplina:** CST314, Programação Orientada a Objetos Avançada (UCSal), Prof. Mario Jorge Pereira, semestre 2026.2.
- **Atividade:** POOALAB, um MVP funcional executável, apresentado em mostra com pitch de até 5 minutos. A nota é coletiva (equipe de 5 pessoas).
- **Tecnologia:** Java 17, Maven, JUnit 5. Sem frameworks pesados e sem banco de dados (repositório em memória).
- **Idioma do código e da documentação:** português.
- **Estado atual:** base criada (pom.xml, domínio completo, repositório em memória, interfaces e esqueletos com `TODO Dev A/B/C`). A divisão está em `docs/divisao-da-equipe.md`.

## Problema escolhido

Para organizadores e participantes de eventos e palestras acadêmicas, que enfrentam inscrições desorganizadas e demora na emissão de certificados, a consequência é retrabalho para quem organiza e atraso para quem precisa comprovar horas. Hoje isso é resolvido com o processo da faculdade, que depende de etapas manuais e de um sistema institucional.

- O sistema atual pertence à faculdade. Ele é só a "alternativa atual" do problema.
- Não copiar nada dele (telas, textos, lógica) e não usar dados reais. Todo o código é novo e todos os dados são fictícios.
- Isso deve ser declarado no Anexo E (material preexistente: o sistema da faculdade é referência, não foi reaproveitado).

## MVP (fluxo principal)

1. O organizador cria um evento, com vagas, carga horária e tipo (palestra ou minicurso).
2. O participante se inscreve. O sistema bloqueia inscrição duplicada, vaga esgotada e prazo encerrado.
3. É registrada a presença (check-in).
4. Ao encerrar o evento, o sistema emite automaticamente o certificado de quem for elegível.
5. Qualquer pessoa confere um certificado pelo código de verificação.

Fora do escopo: login institucional, envio por e-mail, integração com sistemas da faculdade, banco de dados, interface gráfica. Interface do MVP: menu de console.

## Arquitetura

Pacotes (base: `br.edu.ucsal.eventos`):

```
dominio/       Evento, Participante, Inscricao, Presenca, Certificado (+ enums e RegraNegocioException)
repositorio/   interface Repositorio + RepositorioEmMemoria
evento/        ServicoEvento (criar/encerrar) e ObservadorEncerramento   <- acrescentado na base
inscricao/     validações de inscrição (Chain of Responsibility) + ServicoInscricao
presenca/      check-in e elegibilidade (Strategy)
certificado/   emissão, tipos (Factory Method), verificação, Observer
relatorio/     ServicoRelatorios com Streams (inscritos, presentes, elegíveis)   <- acrescentado na base
app/           console (Main), dados de demonstração
```

Os pacotes `evento/` e `relatorio/` foram acrescentados ao plano original para que o "sujeito" do Observer e os relatórios com Streams tivessem um lugar próprio, sem misturar responsabilidades.

Princípios:

- Dependências apontam para o domínio. Serviços dependem de interfaces (`Repositorio`), nunca de classes concretas (DIP), e recebem as dependências pelo construtor.
- Uma responsabilidade por classe (SRP). Novas regras entram como classes novas, sem editar as antigas (OCP).
- A data de "hoje" entra por `java.time.Clock` no construtor, para os testes fixarem o dia.

## Padrões de projeto (bônus POOA, só onde fizer sentido)

| Padrão | Onde | Por quê |
|---|---|---|
| Chain of Responsibility | `inscricao/` | Cada validação (prazo, vaga, duplicidade) é um elo. Quem rejeita interrompe a cadeia. |
| Strategy | `presenca/` | Regra de elegibilidade por tipo de evento (palestra: presença simples; minicurso: no mínimo 75% da carga horária). |
| Factory Method | `certificado/` | Tipos de certificado: participante, palestrante, organizador. |
| Observer | `evento/` + `certificado/` | Ao encerrar o evento, o módulo de certificados é notificado e emite. |
| Streams e Collections | `relatorio/`, `presenca/` | Inscritos, presentes e elegíveis. |

Evitar encaixe artificial de padrões: o bônus só vale se a aplicação for pertinente e a equipe souber explicar.

## Divisão do trabalho (3 devs + 2 integrantes fora do código)

| Papel | Entrega | Guia |
|---|---|---|
| Dev A (líder técnico) | `ServicoEvento` (sujeito do Observer), Chain de validações e `ServicoInscricao.inscrever`, `ServicoRelatorios` (Streams), pom.xml, revisão dos PRs | `docs/guias/dev-a-eventos-inscricao.md` |
| Dev B | Check-in e Strategy de elegibilidade; Factory de certificados, código de verificação, Observer de emissão e `verificar(codigo)` | `docs/guias/dev-b-presenca-certificados.md` |
| Dev C (integrador) | Menu de console, dados fictícios, montagem no Main, teste do fluxo completo | `docs/guias/dev-c-console-integracao.md` |
| Pessoa D | Validação com usuários reais (Anexos A e B), teste com usuário | `docs/guias/pessoa-d-validacao.md` |
| Pessoa E | Pitch em PDF, teste em ambiente limpo, vídeo de contingência, README final, Anexo E, entrega | `docs/guias/pessoa-e-pitch-entrega.md` |

Ordem: Dev A faz `ServicoEvento` primeiro; Dev B faz `presenca/` primeiro; Dev C começa o menu já e integra conforme as partes chegam. D e E começam em paralelo. Todos fazem commits e todos estudam o código.

## Git

- Uma branch por tarefa. Merge na main somente com o projeto compilando e os testes passando.
- Nunca versionar segredos, tokens ou dados pessoais.

## Regras de trabalho com o Claude Code

- Toda a equipe precisa entender e saber explicar cada trecho gerado. Prefira código simples e comentado quando a lógica não for óbvia.
- O uso de IA deve ser registrado no Anexo E (`docs/declaracao-ia-terceiros.md`): ferramenta, tarefa, parte afetada e revisão feita.
- Nunca inventar evidências de validação, números ou depoimentos. Se faltar dado, deixe um campo para a equipe preencher.
- Dados de demonstração sempre fictícios.
- Todo código novo vem com ao menos um teste do caminho principal.
- Rodar `mvn test` antes de cada merge.
