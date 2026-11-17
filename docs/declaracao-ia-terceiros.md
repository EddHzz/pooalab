# Anexo E — Declaração de uso de inteligência artificial e de componentes de terceiros

> Documento obrigatório. **A equipe deve revisar, completar e acrescentar linhas** para todo uso de IA feito por qualquer integrante durante o desenvolvimento.

**Solução:** POOALAB Eventos — inscrição e certificados de eventos acadêmicos

**Turma:** [PREENCHER] **Equipe:** [PREENCHER]

**Integrantes:** [PREENCHER: nomes completos]

## 1. Uso de inteligência artificial

Houve uso de IA? (X) Sim ( ) Não

| Ferramenta ou modelo | Finalidade ou tarefa | Parte afetada | Revisão e validação realizadas |
|---|---|---|---|
| Claude (Anthropic), via Claude Code | Gerar a estrutura base do projeto a partir do plano da equipe (CLAUDE.md): pom.xml, classes de domínio, repositório em memória, interfaces e esqueletos com TODO, testes da base e testes-contrato | `pom.xml`, `src/main/java/.../dominio`, `.../repositorio`, assinaturas e comentários das demais classes, testes em `src/test` | [PREENCHER: quem revisou, o que foi conferido, ex.: "lido em reunião em __/11; mvn test executado por ___"] |
| Claude (Anthropic), via Claude Code | Redigir rascunhos de documentação: README, guia de arquitetura, guias por integrante, modelos dos Anexos A–E e roteiro do pitch | `README.md`, `CLAUDE.md`, `docs/`, `demo/` | [PREENCHER] |
| [PREENCHER: ex. Claude/ChatGPT/Copilot] | [PREENCHER: o que cada integrante pediu] | [PREENCHER: arquivos] | [PREENCHER] |

**Observações sobre o uso de IA:** a IA **não** foi usada como evidência de validação. Os campos de validação (Anexo B) foram deixados em branco para a equipe preencher com pesquisa real. A implementação dos módulos marcados com `TODO Dev A/B/C` foi feita pelos integrantes [PREENCHER: confirmar e descrever se houve apoio de IA].

## 2. Componentes e recursos de terceiros

Houve uso de componentes de terceiros? (X) Sim ( ) Não

| Componente ou recurso | Origem ou link | Versão e licença | Função e alterações realizadas |
|---|---|---|---|
| JUnit 5 (junit-jupiter) | https://junit.org/junit5/ | 5.10.2, Eclipse Public License 2.0 | Testes automatizados. Sem alterações. |
| Apache Maven + plugins (surefire, jar) | https://maven.apache.org/ | 3.x / plugins 3.2.5 e 3.3.0, Apache License 2.0 | Build e execução dos testes. Sem alterações. |
| exec-maven-plugin (MojoHaus) | https://www.mojohaus.org/exec-maven-plugin/ | 3.1.0, Apache License 2.0 | Executar o programa com `mvn exec:java`. Sem alterações. |
| Java SE / OpenJDK | https://openjdk.org/ | 17+, GPLv2 com Classpath Exception | Plataforma de execução. |

## 3. Materiais ou código preexistentes

Houve material produzido antes do PooaLAB? ( ) Sim (X) Não

**Identificação e origem:** Não se aplica quanto a código. O processo e o sistema institucional da faculdade para eventos e certificados foram usados **apenas como referência do problema** ("alternativa atual"). Nada dele foi copiado (telas, textos, lógica ou dados). Todos os dados do projeto são fictícios.

**Contribuição nova realizada pela equipe:** todo o código e a documentação foram criados no período do PooaLAB.

## 4. Declaração da equipe

Declaramos que as informações acima são completas e verdadeiras; que revisamos e testamos as contribuições apoiadas por IA; que identificamos as fontes, licenças e partes preexistentes relevantes; que não apresentamos como autoria própria material de terceiros; e que não compartilhamos dados pessoais, credenciais ou informações restritas de forma indevida.

**Responsável pela submissão:** [PREENCHER]

**Ciência dos integrantes**

| Nome completo | Assinatura ou registro de ciência |
|---|---|
| [PREENCHER] | [PREENCHER] |
| [PREENCHER] | [PREENCHER] |
| [PREENCHER] | [PREENCHER] |
| [PREENCHER] | [PREENCHER] |
| [PREENCHER] | [PREENCHER] |

**Data:** ____/____/2026
