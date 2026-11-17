# Guias por papel

Comece pela visão geral em [`../divisao-da-equipe.md`](../divisao-da-equipe.md). Depois abra o guia do seu papel:

| Papel | Parte | Guia |
|---|---|---|
| Dev A | Eventos, inscrição (Chain of Responsibility) e relatórios (Streams) | [dev-a-eventos-inscricao.md](dev-a-eventos-inscricao.md) |
| Dev B | Presença (Strategy) e certificados (Factory Method + Observer) | [dev-b-presenca-certificados.md](dev-b-presenca-certificados.md) |
| Dev C | Console, dados de demonstração e integração | [dev-c-console-integracao.md](dev-c-console-integracao.md) |
| Pessoa D | Problema e validação com usuários reais | [pessoa-d-validacao.md](pessoa-d-validacao.md) |
| Pessoa E | Pitch, reprodutibilidade e entrega | [pessoa-e-pitch-entrega.md](pessoa-e-pitch-entrega.md) |

## Como achar o que é seu no código

```bash
grep -rn "TODO Dev A" src/                  # Linux/macOS/Git Bash
findstr /s /n "TODO Dev A" src\*.java       # Windows (cmd)
```

Os métodos ainda não feitos lançam `UnsupportedOperationException`. É proposital: o projeto compila e os testes da base passam, e quem chamar um método não feito vê na hora qual parte falta.

## Testes-contrato

Para os Devs A e B já existe um teste com `@Disabled` em cada parte. Ele é o **contrato**: descreve o comportamento que os outros esperam. Quando terminar a parte:

1. apague a linha `@Disabled(...)`;
2. rode `mvn test` e deixe tudo verde;
3. escreva **pelo menos mais um teste seu** (um caso de erro ou de limite);
4. abra o Pull Request para a `main`.

| Teste-contrato | Dono |
|---|---|
| `evento/ServicoEventoTest` | Dev A |
| `inscricao/ServicoInscricaoTest` | Dev A |
| `presenca/ElegibilidadeTest` | Dev B |
| `certificado/ServicoVerificacaoTest` | Dev B |
| `FluxoPrincipalTest` (a criar) | Dev C |
