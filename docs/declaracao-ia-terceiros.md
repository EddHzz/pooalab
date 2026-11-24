# Anexo E — Declaração de uso de inteligência artificial e de componentes de terceiros

**Solução:** POOALAB Eventos — inscrição e certificados de eventos acadêmicos

**Turma:** BES, 5º semestre, noturno · **Equipe:** POOALAB Eventos

**Integrantes:** Edenilton Oliveira Mota Filho, Vitor Inacio, Gilmar Araújo Santos Junior, Júlia Lessa Soares Ventura, Jade Evelyn Silva de Morais

## 1. Uso de inteligência artificial

Houve uso de IA? (X) Sim ( ) Não

| Ferramenta ou modelo | Finalidade ou tarefa | Parte afetada | Revisão e validação realizadas |
|---|---|---|---|
| Claude (Anthropic), via Claude Code | Gerar a estrutura base do projeto a partir do plano da equipe (CLAUDE.md): pom.xml, classes de domínio, repositório em memória, interfaces e esqueletos com TODO, testes da base e testes-contrato | `pom.xml`, `src/main/java/.../dominio`, `.../repositorio`, assinaturas e comentários das demais classes, testes em `src/test` | Revisada por Edenilton; `mvn test` executado no IntelliJ |
| Claude (Anthropic), via Claude Code | Redigir rascunhos de documentação: README, guia de arquitetura, guias por integrante, modelos dos Anexos A–E e roteiro do pitch | `README.md`, `CLAUDE.md`, `docs/`, `demo/` | Revisados e ajustados pela equipe |
| Claude (Anthropic) | Montar os slides do pitch a partir do roteiro (Anexo D) e dos dados reais dos Anexos A e B | `docs/pitch.pdf` | Conteúdo conferido pela equipe antes da entrega |
| Claude (Anthropic) — Edenilton (Dev A) | Apoio com Git, GitHub e IntelliJ (branches, commits, PRs); pontualmente, sugestões de código | `evento/`, `inscricao/`, `relatorio/` | Revisado e testado com `mvn test` antes de cada PR |
| ChatGPT (OpenAI) — Gilmar (Dev C) | Apoio em dúvidas de implementação, análise de erros, configuração do Maven e orientações de integração com Git | `app/`, `src/test/java/br/edu/ucsal/eventos/app/`, `pom.xml`, `demo/` | Sugestões revisadas pelo desenvolvedor; `mvn test` executado com os 47 testes passando |

**Observações sobre o uso de IA:** a IA **não** foi usada como evidência de validação. Os Anexos A e B foram preenchidos pela equipe com conversas reais com estudantes. A implementação das partes de cada desenvolvedor foi feita e commitada pelos próprios integrantes; o apoio de IA nessas partes está declarado na tabela acima. Os demais integrantes não usaram IA além do listado.

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

**Responsável pela submissão:** Edenilton Oliveira Mota Filho

**Ciência dos integrantes**

| Nome completo | Assinatura ou registro de ciência |
|---|---|
| Edenilton Oliveira Mota Filho | Ciente |
| Vitor Inacio | Ciente |
| Gilmar Araújo Santos Junior | Ciente |
| Júlia Lessa Soares Ventura | Ciente |
| Jade Evelyn Silva de Morais | Ciente |

**Data:** 23/11/2026
