# Divisão da equipe: quem faz o quê e como

> Comece por aqui. Este documento diz o papel de cada integrante, a ordem do trabalho, o cronograma até a entrega (23/11/2026) e as regras que valem para todos. Cada papel tem um guia detalhado em [`docs/guias/`](guias/README.md).

## 1. Os 5 papéis

| Papel | Nome | Responsabilidade | Guia | Pontos da rubrica que mais dependem dele |
|---|---|---|---|---|
| **Dev A** (líder técnico) | [PREENCHER] | Eventos (criar/encerrar + Observer), inscrição (Chain of Responsibility), relatórios (Streams), `pom.xml`, revisão dos PRs | [dev-a-eventos-inscricao.md](guias/dev-a-eventos-inscricao.md) | MVP funcional (25), Qualidade (10), Bônus POOA (+5) |
| **Dev B** | [PREENCHER] | Presença e elegibilidade (Strategy), certificados (Factory Method), emissão automática (Observer), verificação | [dev-b-presenca-certificados.md](guias/dev-b-presenca-certificados.md) | MVP funcional (25), Qualidade (10), Bônus POOA (+5) |
| **Dev C** (integrador) | [PREENCHER] | Menu de console, dados de demonstração, montagem no `Main`, teste do fluxo completo | [dev-c-console-integracao.md](guias/dev-c-console-integracao.md) | MVP funcional (25), Utilidade (10), Demonstração (10) |
| **Pessoa D** | [PREENCHER] | Entrevistas reais, Anexos A e B, teste com usuário | [pessoa-d-validacao.md](guias/pessoa-d-validacao.md) | Problema e validação (12), Decisões e respostas (7) |
| **Pessoa E** | [PREENCHER] | Pitch em PDF, teste em outro computador, vídeo de contingência, README final, Anexo E, entrega | [pessoa-e-pitch-entrega.md](guias/pessoa-e-pitch-entrega.md) | Pitch (8), Reprodutibilidade (8), Transparência (5), Tempo (5) |

Dica para escolher: quem programa melhor fica como **Dev C** (integra tudo e é o menu que vai ao vivo). O Dev A precisa conhecer bem o domínio, porque responde dúvidas dos outros.

## 2. Como as partes dependem umas das outras

```
           base pronta (domínio + repositório + interfaces)
                         │
     ┌───────────────────┼────────────────────┐
     ▼                   ▼                    ▼
  Dev A               Dev B                Dev C
  ServicoEvento ──┐   presenca/ ──┐        menu + dados (começa já)
  inscricao/      │      │        │              │
  relatorios <────┼──────┘        ▼              │
                  │           certificado/       │
                  └──────────────┬───────────────┘
                                 ▼
                    Dev C integra no Main + FluxoPrincipalTest
                                 ▼
          Pessoa D: teste com usuário │ Pessoa E: teste em outro PC + vídeo

  Pessoa D (entrevistas) e Pessoa E (slides) começam já, em paralelo.
```

- **Dev A** faz o `ServicoEvento` primeiro (é rápido e destrava o Observer).
- **Dev B** faz `presenca/` primeiro (os relatórios do Dev A e os certificados dependem dela).
- **Dev C** começa o menu no primeiro dia e vai ligando as partes conforme chegam.

## 3. Cronograma até a entrega

| Data | Dev A | Dev B | Dev C | Pessoa D | Pessoa E |
|---|---|---|---|---|---|
| **16–17/11** (seg–ter) | Sobe a base, lê o domínio, faz `ServicoEvento` | Faz `presenca/` | Esqueleto do menu e dados fictícios | Marca e começa entrevistas | Lê edital e roteiro; rascunho dos slides |
| **18–19/11** (qua–qui) | Inscrição (Chain) + relatórios | Certificados (Factory + Observer) | Liga o menu às partes já mergeadas | Termina entrevistas, preenche Anexos A e B | Slides com espaço para evidências e demo |
| **20/11** (sex) | Revisa PRs, corrige bugs | Corrige bugs | **Fluxo completo rodando** + `FluxoPrincipalTest` | **Teste com usuário** | **Teste em outro computador** |
| **21/11** (sáb) | **Congelar funcionalidades**: só correções | idem | Ajustes do teste com usuário | Síntese dos aprendizados | **Grava o vídeo**; pitch final em PDF |
| **22/11** (dom) | **Ensaio cronometrado com os 5 (2 vezes)** e revisão final dos documentos | | | | Checklist final do edital |
| **23/11** (seg) | **Entrega no Classroom** | | | | |

## 4. Regras que valem para todos

### Git

1. **Uma branch por tarefa**, com o seu papel no nome: `dev-a/inscricao`, `dev-b/presenca`, `pessoa-d/validacao`.
2. **Commits pequenos e frequentes**, com mensagem que diz o que mudou ("Implementa ValidadorVagas e teste de vaga esgotada").
3. Antes de abrir o Pull Request: `git pull origin main`, depois `mvn test` passando.
4. **Merge na `main` só com outra pessoa aprovando** o Pull Request.
5. Nunca subir senhas, tokens ou dados pessoais. A pasta `target/` já está no `.gitignore`.

```bash
git checkout main && git pull
git checkout -b dev-b/presenca
# ... programa e testa ...
mvn test
git add .
git commit -m "Implementa RegraMinicurso com 75% e teste"
git push -u origin dev-b/presenca
# abre o Pull Request no GitHub
```

D e E podem fazer tudo pelo site do GitHub (lápis → "Create a new branch" → Pull Request). **Os 5 precisam aparecer no histórico.**

### Código

- Os trechos a fazer estão marcados `TODO Dev A`, `TODO Dev B` e `TODO Dev C`. Os métodos ainda não feitos lançam `UnsupportedOperationException`.
- Cada dev tira o `@Disabled` do seu teste-contrato quando terminar e escreve **pelo menos mais um teste próprio**.
- Código e comentários em português. Comente quando a lógica não for óbvia.

### Uso de IA

- Pode usar (Claude, ChatGPT, Copilot...), mas **você precisa entender e saber explicar cada linha**.
- Registre na tabela do Anexo E ([declaracao-ia-terceiros.md](declaracao-ia-terceiros.md)): ferramenta, o que pediu, arquivos afetados e o que revisou. Passe essa informação para a Pessoa E.
- **Nunca** usar IA para inventar entrevistas, números ou depoimentos.

### Estudo (os 5)

O professor pode perguntar **qualquer parte a qualquer integrante**. Até o ensaio de 22/11, todos devem:

1. Ler o [guia de arquitetura e conceitos](arquitetura-e-conceitos.md).
2. Saber responder as 7 "perguntas para treinar" do fim do guia.
3. Saber explicar, em uma frase, cada padrão (Chain, Strategy, Factory Method, Observer) e onde está no código.
4. Saber rodar o projeto e a demonstração.

## 5. Definição de pronto

| Item | Pronto quando |
|---|---|
| Código de uma parte | Sem `TODO` do seu papel, `@Disabled` removido, ao menos 1 teste novo seu, `mvn test` verde, PR aprovado e mergeado |
| MVP | Roteiro de `demo/roteiro-demo.md` roda inteiro no menu sem erro; `mvn test` mostra 0 Skipped |
| Validação | Anexo B com entrevistas reais + 1 teste com usuário + síntese |
| Pitch | `docs/pitch.pdf` no repositório, ensaiado em até 5 minutos com os 5 falando |
| Entrega | Checklist do edital completo (guia da Pessoa E) |
