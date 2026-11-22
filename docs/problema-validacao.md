# Problema e validação (Anexos A e B)

> Responsável: **Pessoa D** (ver `docs/guias/pessoa-d-validacao.md`).
>
> **Atenção equipe:** os campos marcados com `[PREENCHER]` dependem de pesquisa real com pessoas. **Não inventem dados, números ou depoimentos**, e saída de IA não vale como validação (edital, "Situações que comprometem a avaliação"). Os textos já preenchidos vêm do plano da equipe (CLAUDE.md) e são **hipóteses** até serem confirmados pela validação.

## Anexo A: Ficha do problema

**Solução:** POOALAB Eventos (nome provisório, a equipe pode trocar) — inscrição e certificados de eventos acadêmicos

**Equipe, turma e integrantes:** [PREENCHER: nome da equipe, turma e nome completo dos 5 integrantes]

| Campo | Preenchimento |
|---|---|
| Público | Organizadores (professores, coordenação, ligas e centros acadêmicos) e participantes (estudantes) de palestras e minicursos acadêmicos. |
| Situação | Durante a etapa de registro de presença e, principalmente, no período pós-evento em que o estudante aguarda a emissão do certificado de participação. |
| Problema | Embora a entrada física nos eventos ocorra sem grandes filas, os participantes não têm segurança de que sua presença foi realmente contabilizada e enfrentam demora excessiva ou ausência de envio dos certificados após o término da atividade. |
| Impacto | Retrabalho para quem organiza, insegurança sobre a validação da frequência, espera de meses (chegando de um ano para o outro) para receber o certificado e atraso para contabilizar horas complementares. |
| Alternativa atual | O registro de presença é feito por QR code na entrada, sem filas nem lotação nos relatos. O certificado é disponibilizado depois pela organização, sem prazo claro para o participante. |
| Hipótese crítica | Se a presença for registrada de forma confiável no evento, o certificado poderá ser gerado automaticamente logo depois, reduzindo a espera e a insegurança do participante. |
| Evidência | Levantamento realizado com estudantes da instituição (BES 5º sem. noturno e Jornalismo 4º sem. matutino): ambos relataram entrada tranquila, mas 100% apontaram demora ou não recebimento do certificado (incluindo espera de um ano) e indicaram o registro de presença e a entrega do certificado como as etapas que geram mais insegurança. |
| Recorte | O fluxo principal do MVP seria inscrição → presença → emissão do certificado, com foco na emissão automática após a presença confirmada. Os relatórios ficam como apoio ao organizador. |

## Anexo B: Registro de validação

Uma linha por entrevista, observação, teste ou fonte. **Sem nome das pessoas**, só perfil.

| Data e perfil | Hipótese | Evidência observada | Decisão |
|---|---|---|---|
| 03/11/2026 — Estudante de Bacharelado em Engenharia de Software, 5º semestre (noturno) | Imaginávamos que os estudantes enfrentavam grandes filas e dificuldade física para entrar e registrar presença nos eventos. | Relatou que não teve muita dificuldade na entrada, mas ainda não recebeu o certificado do último evento, já chegou a receber um certificado apenas no ano seguinte e apontou o **registro de presença** como a etapa de maior insegurança. | **Alterar** o foco do problema: reduzir a ênfase em filas físicas na portaria e priorizar a garantia imediata de que a presença foi validada e vinculada ao certificado. |
| 03/11/2026 — Estudante de Jornalismo, 4º semestre (matutino) | Imaginávamos que a desorganização e a hiperlotação na entrada eram as principais queixas dos participantes. | Informou que a entrada foi tranquila (leitura de QR Code e sem hiperlotação), mas que o certificado demora muito para ficar disponível, apontando a **entrega do certificado** como a etapa que gera mais insegurança. | **Manter** a integração automática entre a confirmação de presença  e a emissão de certificados como o fluxo central do MVP. |
| 03/11/2026 — Estudante de Direito, 6º semestre (noturno) | Imaginávamos que o horário apertado do turno noturno gerava tumulto e atrasos no credenciamento de entrada dos eventos. | Relatou que o acesso ao evento foi rápido e sem fila, porém destacou a falta de confirmação imediata da frequência e a demora de meses para a liberação do certificado para horas complementares. | **Manter** o vínculo direto entre o check-in validado e a geração automática do certificado, garantindo feedback imediato ao participante. |
| [__/__/2026 — teste do MVP com um usuário] | O fluxo é compreensível sem explicação? | [PREENCHER: o que a pessoa conseguiu ou não fazer] | [PREENCHER: o que mudou no MVP] |

**Síntese dos aprendizados:** [PREENCHER]

**Mudanças realizadas no problema ou na solução:** [PREENCHER]

## Sugestão de roteiro de entrevista (5 minutos)

Explique o objetivo acadêmico, peça permissão e não anote nome.

1. Você já organizou ou participou de palestra/minicurso na faculdade? Como foi a inscrição?
2. Já aconteceu algum problema na inscrição (duplicidade, vaga, prazo)? Conte o último caso.
3. Quanto tempo levou para receber o certificado? Precisou dele para alguma coisa?
4. Como você confere se um certificado é verdadeiro? (para organizadores/coordenação)
5. (Depois de mostrar o MVP) Isso resolveria? O que faltou ou atrapalhou?
