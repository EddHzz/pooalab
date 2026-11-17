# Dev A: eventos, relatórios e inscrição

Você é o **líder técnico**: entende o domínio melhor que todos, cuida do `pom.xml` e revisa os Pull Requests. Implementa duas partes:

- **Parte 1:** `evento/ServicoEvento` (sujeito do Observer) e `relatorio/ServicoRelatorios` (Streams).
- **Parte 2:** `inscricao/` (Chain of Responsibility).

Para ver tudo que é seu: `grep -rn "TODO Dev A" src/`

## Passo 0: entender a base (antes de programar)

Já está pronto e testado:

- `dominio/`: `Evento`, `Participante`, `Inscricao`, `Presenca`, `Certificado` (abstrata), `TipoEvento`, `TipoCertificado`, `RegraNegocioException`.
- `repositorio/`: interface `Repositorio<T, ID>` e `RepositorioEmMemoria<T, ID>`.
- Testes: `EventoTest`, `ParticipanteTest`, `RepositorioEmMemoriaTest`.

Leia essas classes. Se quiser mudar o domínio (um campo, uma validação), avise a equipe antes, porque todos dependem dele.

---

## Parte 1a: `ServicoEvento` (faça primeiro, é rápido)

Branch: `dev-a/servico-evento`

| Método | O que faz |
|---|---|
| `adicionarObservador(obs)` | Guarda o observador na lista (`Objects.requireNonNull`). |
| `criarEvento(...)` | Se o código já existe, `RegraNegocioException`. Senão cria o `Evento`, salva e devolve. |
| `encerrarEvento(codigo)` | Busca (não achou = `RegraNegocioException`), chama `evento.encerrar()`, avisa cada observador e devolve o evento. |

```java
Evento evento = eventos.buscarPorId(codigo)
        .orElseThrow(() -> new RegraNegocioException("Evento não encontrado: " + codigo));
evento.encerrar();
for (ObservadorEncerramento observador : observadores) {
    observador.eventoEncerrado(evento);
}
return evento;
```

**Testes:** apague o `@Disabled` de `src/test/java/.../evento/ServicoEventoTest.java` e acrescente um seu (ex.: encerrar código inexistente lança `RegraNegocioException`).

---

## Parte 2: inscrição (Chain of Responsibility)

Branch: `dev-a/inscricao`

### Ideia

Uma inscrição pode ser recusada por **prazo encerrado**, **vagas esgotadas** ou **duplicidade**. Cada motivo é uma classe (um "elo"). Os elos formam uma fila; cada um verifica a sua regra e passa para o próximo. O primeiro que encontrar problema lança `RegraNegocioException` e a cadeia para.

```
inscrever() ──> [ValidadorPrazo] ──> [ValidadorVagas] ──> [ValidadorDuplicidade] ──> salva a inscrição
                     │ recusa             │ recusa              │ recusa
                     └──────────── RegraNegocioException ───────┘
```

### O que implementar

1. `ValidadorInscricao.encadear(proximo)`: guarda o próximo e devolve **o próximo** (é isso que permite `a.encadear(b).encadear(c)`).
2. `ValidadorInscricao.validar(...)`: chama `verificar(...)` e, se existir próximo, `proximo.validar(...)`.
3. `ValidadorPrazo.verificar`: recusa se `evento.isEncerrado()` ou `!evento.prazoAbertoEm(data)`.
4. `ValidadorVagas.verificar`: conta inscrições do evento e recusa se `>= evento.getVagas()`.
5. `ValidadorDuplicidade.verificar`: recusa se `inscricoes.existe(Inscricao.gerarId(participante, evento))`.
6. `ServicoInscricao.inscrever`: pega `LocalDate.now(relogio)`, chama a cadeia, cria e salva a `Inscricao`.

**Mensagens claras**, porque aparecem na demonstração. Ex.: `"Vagas esgotadas para o evento MC01 (3 de 3)."`

Montagem da cadeia (o Dev C faz no `Main`; o seu teste já faz):

```java
ValidadorInscricao cadeia = new ValidadorPrazo();
cadeia.encadear(new ValidadorVagas(inscricoes))
      .encadear(new ValidadorDuplicidade(inscricoes));
```

**Testes:** apague o `@Disabled` de `ServicoInscricaoTest` (sucesso, duplicada, vaga esgotada, prazo encerrado). Acrescente um seu: evento já encerrado; último dia do prazo ainda aceita; ou inscrição recusada **não** é salva. O `Clock.fixed(...)` congela o "hoje" em 18/11/2026 para o teste não depender do dia real.

---

## Parte 1b: `ServicoRelatorios` (depois que o Dev B entregar `ServicoElegibilidade`)

Branch: `dev-a/relatorios`

`inscritos`, `presentes` e `elegiveis` devolvem listas de `Participante` ordenadas por nome:

```java
return inscricoes.buscar(i -> i.getEvento().equals(evento)).stream()
        .map(Inscricao::getParticipante)
        .sorted(Comparator.comparing(Participante::getNome))
        .toList();
```

**Testes:** crie `src/test/java/.../relatorio/ServicoRelatoriosTest.java`.

---

## O que saber explicar na mostra

- Por que `Repositorio` é interface (DIP) e por que `RepositorioEmMemoria` recebe uma `Function<T, ID>`.
- Por que `Evento` valida no construtor (objeto inválido nunca existe).
- Observer: o `ServicoEvento` conhece só `ObservadorEncerramento`, não o emissor de certificados.
- Chain of Responsibility: como acrescentar uma regra nova sem mexer nas existentes (OCP); por que `validar` é `final` e `verificar` é `abstract`.
- Por que o `Clock` vem pelo construtor.
- O que fazem `filter`, `map`, `sorted` e `toList`.
