# Pessoa E: pitch, reprodutibilidade e entrega

Sua parte vale muitos pontos: **Pitch** (8), **Demonstração** (10), **Reprodutibilidade** (8), **Tempo e coordenação** (5) e **Transparência** (5). Você é quem garante que a entrega está completa e que o professor consegue rodar o projeto.

## O que fazer

### 1. Pitch (rascunho até 20/11, final até 21/11)

- Siga [`docs/roteiro-pitch.md`](../roteiro-pitch.md): 5 minutos, **no máximo 6 slides**, demonstração ao vivo no centro.
- Faça no Google Slides, PowerPoint ou Canva e **exporte como PDF** para `docs/pitch.pdf`.
- O slide de evidências usa os achados reais da Pessoa D.
- Defina quem fala em cada parte e preencha a coluna "Quem fala" do roteiro. **Todos os 5 devem falar.**

### 2. Teste de reprodutibilidade (20/11)

- Em **outro computador** (ou num usuário limpo), siga o README do zero: instalar JDK 17 e Maven, clonar, `mvn test`, executar.
- Anote **cada** coisa que falhou ou ficou confusa e corrija o README (ou avise o dev responsável).
- O edital pede isso no checklist: "O README foi seguido do início ao fim em um ambiente limpo".

### 3. Vídeo de contingência (21/11)

- Grave de 1 a 2 minutos do fluxo principal (opção "demonstração automática" ou o roteiro em `demo/roteiro-demo.md`). Pode ser com OBS, Xbox Game Bar (Win+G) ou gravador do celular.
- Suba no Google Drive com **"Qualquer pessoa com o link pode ver"** e coloque o link no README.

### 4. Documentos da entrega (até 22/11)

- README: preencher integrantes, turma, link do repositório e do vídeo, e tirar o aviso de "Estado atual: base".
- [`docs/declaracao-ia-terceiros.md`](../declaracao-ia-terceiros.md) (Anexo E): pedir para cada integrante dizer se usou IA e para quê, preencher a tabela, nomes e o registro de ciência.
- [`docs/definicao-mvp.md`](../definicao-mvp.md) (Anexo C): conferir os critérios de pronto e preencher o "maior risco".

### 5. Ensaio e checklist final (22/11)

- Ensaio cronometrado com os 5, pelo menos 2 vezes.
- Conferir o checklist "Antes de enviar no Classroom" do edital:
  - [ ] Todos os integrantes e a turma identificados
  - [ ] Links abrem sem pedir permissão (repositório **público** ou professor com acesso; vídeo público)
  - [ ] Repositório é a versão final, sem segredos ou dados pessoais
  - [ ] README seguido do início ao fim em ambiente limpo
  - [ ] `docs/pitch.pdf` abre corretamente
  - [ ] MVP, evidências e contingência acessíveis
  - [ ] Cópia dos arquivos finais guardada

### 6. Entrega (23/11)

Uma pessoa envia no Google Classroom e confere nomes, links e permissões.

## Como subir seus arquivos (commits)

Igual à Pessoa D: pelo site do GitHub (lápis → "Create a new branch" → Pull Request) ou pelo terminal com a branch `pessoa-e/pitch`. Para o PDF, use "Add file" → "Upload files" dentro da pasta `docs/`.

## O que saber explicar na mostra

- Por que o recorte do MVP e quais as limitações.
- Como rodar o projeto (você é quem mais testou a instalação).
- Ler e entender o [guia de arquitetura](../arquitetura-e-conceitos.md): o professor pode perguntar do código para você também.
