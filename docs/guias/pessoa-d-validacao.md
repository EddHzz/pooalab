# Pessoa D: problema e validação com usuários reais

Sua parte vale **12 pontos** (Problema e validação) e sustenta o pitch. É também a única parte que **ninguém pode inventar**: o edital diz que evidência inventada ou feita por IA compromete a avaliação.

Arquivo principal: [`docs/problema-validacao.md`](../problema-validacao.md) (Anexos A e B).

## O que fazer

### 1. Entrevistas (até 19/11)

- Converse com **4 a 6 pessoas reais**: pelo menos 3 estudantes que já participaram de palestras ou minicursos, e 1 ou 2 pessoas que organizam eventos (professor, liga, centro acadêmico).
- Use o roteiro de 5 perguntas que está no fim do `problema-validacao.md`.
- **Ética:** explique que é um trabalho da disciplina, peça permissão, **não anote nomes** (só o perfil, como "estudante de ADS, 4º semestre"), não grave sem autorização.
- Anote o que a pessoa disse com as palavras dela, inclusive o que **contradiz** a nossa ideia. Contradição registrada é boa evidência.

### 2. Preencher o Anexo B (uma linha por entrevista)

| Data e perfil | Hipótese | Evidência observada | Decisão |
|---|---|---|---|
| 18/11/2026 — estudante de BES, 6º sem. | Certificado demora | "Esperei umas 3 semanas pelo do último minicurso" | Manter: emissão automática é o foco |

(O exemplo acima é só formato. Escreva o que **vocês** ouvirem.)

### 3. Preencher o Anexo A

Complete os campos `[PREENCHER]`: situação, impacto, alternativa atual, hipótese crítica e evidência, com base nas entrevistas.

### 4. Teste com usuário (20/11, quando o MVP rodar)

- Peça para 1 ou 2 pessoas usarem o menu **sozinhas**, com a tarefa: "inscreva-se no minicurso e depois confira o certificado pelo código".
- Não ajude, só observe. Anote onde a pessoa travou.
- Registre no Anexo B e passe para o Dev C o que precisa mudar (ex.: mensagem confusa).

### 5. Síntese

- Escreva a "Síntese dos aprendizados" e as "Mudanças realizadas" no fim do Anexo B.
- Escreva 2 ou 3 linhas para o README (seção "Validação") e passe para a Pessoa E os 1 ou 2 achados mais fortes para o slide de evidências.

## Como subir seus arquivos (commits)

Seus commits contam como histórico da equipe. Pode ser pelo site do GitHub:

1. Abra o repositório, entre em `docs/problema-validacao.md` e clique no lápis (Edit).
2. Edite, desça até "Commit changes", escreva uma mensagem (ex.: "Registra 3 entrevistas no Anexo B") e escolha **"Create a new branch"**.
3. Abra o Pull Request e peça para o Dev A aprovar.

Ou pelo terminal, igual aos devs: `git checkout -b pessoa-d/validacao`, editar, `git add`, `git commit`, `git push`.

## O que saber explicar na mostra

- Quem foi entrevistado (perfis), quantos, e o que mais apareceu.
- Qual hipótese mudou ou se confirmou.
- Por que o recorte do MVP é inscrição + certificado automático.
- Ler e entender o [guia de arquitetura](../arquitetura-e-conceitos.md): o professor pode perguntar do código para você também.
