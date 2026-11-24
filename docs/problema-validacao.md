# Problema e validação (Anexos A e B)

> Responsável: **Pessoa D** (ver `docs/guias/pessoa-d-validacao.md`).
>
> **Atenção equipe:** os campos marcados com `[PREENCHER]` dependem de pesquisa real com pessoas. **Não inventem dados, números ou depoimentos**, e saída de IA não vale como validação (edital, "Situações que comprometem a avaliação"). Os textos já preenchidos vêm do plano da equipe (CLAUDE.md) e são **hipóteses** até serem confirmados pela validação.

## Anexo A: Ficha do problema

**Solução:** POOALAB Eventos (nome provisório, a equipe pode trocar) — inscrição e certificados de eventos acadêmicos

**Equipe, turma e integrantes:** [Turma: ESW-NOT-PIT-4S-T1 - Edenilton Oliveira Mota Filho, Gilmar Araújo Santos Júnior, Jade Evelyn Silva de Morais, Júlia Lessa Soares Ventura, Vítor Inácio.]

| Campo | Preenchimento |
|---|---|
| Público | Organizadores (professores, coordenação, ligas e centros acadêmicos) e participantes (estudantes) de palestras e minicursos acadêmicos. |
| Situação | Durante a etapa de registro de presença e, principalmente, no período pós-evento em que o estudante aguarda a emissão do certificado de participação. |
| Problema | Embora a entrada física nos eventos ocorra sem grandes filas, os participantes não têm segurança de que sua presença foi realmente contabilizada e enfrentam demora excessiva ou ausência de envio dos certificados após o término da atividade. |
| Impacto | Retrabalho para quem organiza, insegurança sobre a validação da frequência, espera de meses (chegando de um ano para o outro) para receber o certificado e atraso para contabilizar horas complementares. |
| Alternativa atual | O registro de presença é feito por QR code na entrada, sem filas nem lotação nos relatos. O certificado é disponibilizado depois pela organização, sem prazo claro para o participante. |
| Hipótese crítica | Se a presença for registrada de forma confiável no evento, o certificado poderá ser gerado automaticamente logo depois, reduzindo a espera e a insegurança do participante. |
| Evidência | Levantamento realizado com estudantes da instituição (BES 5º sem. noturno, Jornalismo 4º sem. matutino, Direito 6º sem. noturno): os 3 relataram entrada tranquila, mas 100% apontaram demora ou não recebimento do certificado (incluindo espera de um ano) e indicaram o registro de presença e a entrega do certificado como as etapas que geram mais insegurança. |
| Recorte | O fluxo principal do MVP seria inscrição → presença → emissão do certificado, com foco na emissão automática após a presença confirmada. Os relatórios ficam como apoio ao organizador. |

## Anexo B: Registro de validação

Uma linha por entrevista, observação, teste ou fonte. **Sem nome das pessoas**, só perfil.

| Data e perfil | Hipótese | Evidência observada | Decisão |
|---|---|---|---|
| 11/11/2026 — Estudante de Bacharelado em Engenharia de Software, 5º semestre (noturno) | Imaginávamos que os estudantes enfrentavam grandes filas e dificuldade física para entrar e registrar presença nos eventos. | Relatou que não teve muita dificuldade na entrada, mas ainda não recebeu o certificado do último evento, já chegou a receber um certificado apenas no ano seguinte e apontou o **registro de presença** como a etapa de maior insegurança. | **Alterar** o foco do problema: reduzir a ênfase em filas físicas na portaria e priorizar a garantia imediata de que a presença foi validada e vinculada ao certificado. |
| 11/11/2026 — Estudante de Jornalismo, 4º semestre (matutino) | Imaginávamos que a desorganização e a hiperlotação na entrada eram as principais queixas dos participantes. | Informou que a entrada foi tranquila (leitura de QR Code e sem hiperlotação), mas que o certificado demora muito para ficar disponível, apontando a **entrega do certificado** como a etapa que gera mais insegurança. | **Manter** a integração automática entre a confirmação de presença  e a emissão de certificados como o fluxo central do MVP. |
| 11/11/2026 — Estudante de Direito, 6º semestre (noturno) | Imaginávamos que o horário apertado do turno noturno gerava tumulto e atrasos no credenciamento de entrada dos eventos. | Relatou que o acesso ao evento foi rápido e sem fila, porém destacou a falta de confirmação imediata da frequência e a demora de meses para a liberação do certificado para horas complementares. | **Manter** o vínculo direto entre o check-in validado e a geração automática do certificado, garantindo feedback imediato ao participante. |
| 23/11/2026 — Teste prático do MVP com estudante de BES, 5º semestre | Imaginávamos que um usuário externo conseguiria executar todo o fluxo (ver eventos, inscrição, presença, certificado e verificação de código) sem travamentos ou dúvidas. | O usuário concluiu 100% das tarefas levando de 1 a 2 minutos por etapa, sem travamentos, destacou a facilidade das listagens e confirmou a utilidade para evitar espera por certificados, mas ficou em dúvida sobre qual perfil estava operando o menu único. | **Manter** o fluxo principal validado e **registrar** a separação de perfis/menus com acessos mínimos como melhoria futura e limitação conhecida no README e no pitch.  |

**Síntese dos aprendizados:** As entrevistas com estudantes (BES, Jornalismo e Direito) revelaram que o gargalo real dos eventos acadêmicos não está na entrada física que ocorreu de forma rápida e sem hiperlotação, mas na insegurança sobre a confirmação efetiva da presença e na demora excessiva para receber os certificados (chegando de um ano para o outro). No teste prático do MVP, o usuário (BES, 5º semestre) conseguiu concluir todas as etapas do fluxo principal sem travamentos, levando de 1 a 2 minutos por tarefa, destacou a facilidade das listagens e confirmou a utilidade da geração imediata do certificado com verificação de código. Contudo, o teste evidenciou que o menu único gera dúvida sobre para qual perfil de usuário (participante ou organizador) cada ação se destina.

**Mudanças realizadas no problema ou na solução:** 
**No problema:** Reposicionamos o foco da narrativa e do pitch, deixando de enfatizar filas na portaria para priorizar a insegurança na validação da frequência e o atraso pós-evento na emissão dos certificados.
* **Na solução:** Mantivemos o fluxo principal enxuto e integrado (inscrição, registro de presença, emissão imediata de certificado e validação por código) como núcleo do MVP. A partir do retorno do teste prático, registramos a separação dos menus por perfil com acessos mínimos (visão de participante vs. visão de organizador) como limitação conhecida da versão atual e principal próximo passo de evolução.

## Sugestão de roteiro de entrevista (5 minutos)

Explique o objetivo acadêmico, peça permissão e não anote nome.

1. Você já organizou ou participou de palestra/minicurso na faculdade? Como foi a inscrição?
2. Já aconteceu algum problema na inscrição (duplicidade, vaga, prazo)? Conte o último caso.
3. Quanto tempo levou para receber o certificado? Precisou dele para alguma coisa?
4. Como você confere se um certificado é verdadeiro? (para organizadores/coordenação)
5. (Depois de mostrar o MVP) Isso resolveria? O que faltou ou atrapalhou?
