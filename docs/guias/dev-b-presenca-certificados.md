# Dev B: presença, elegibilidade e certificados

Você implementa duas partes:

- **Parte 3:** `presenca/` (Strategy). Faça **primeiro**, porque o Dev A (relatórios) e a sua parte de certificados dependem dela.
- **Parte 4:** `certificado/` (Factory Method + Observer + verificação).

Para ver tudo que é seu: `grep -rn "TODO Dev B" src/`

---

## Parte 3: presença e elegibilidade (Strategy)

Branch: `dev-b/presenca`

### Ideia

- **Palestra:** basta ter feito check-in.
- **Minicurso:** pelo menos **75% da carga horária** (8h exige 6h).

Em vez de `if (tipo == PALESTRA) ... else ...`, cada regra é uma classe que implementa `RegraElegibilidade` (uma "estratégia"). O `SeletorRegraElegibilidade` guarda um mapa tipo -> regra e entrega a certa.

### O que implementar

1. `RegraPalestra.ehElegivel`: `return presenca != null;`
2. `RegraMinicurso.ehElegivel`: `horasPresentes >= cargaHoraria * PERCENTUAL_MINIMO`.
3. `SeletorRegraElegibilidade`: no construtor, registrar as duas regras; `registrar` coloca no `EnumMap`; `regraPara` busca e lança `IllegalStateException` se não houver.
4. `ServicoPresenca.registrarCheckIn(participante, evento, horas)`: recusa evento encerrado e pessoa não inscrita (`RegraNegocioException`), depois cria e salva a `Presenca`. Registrar de novo substitui as horas (o id é o mesmo).
5. `ServicoElegibilidade.listarElegiveis(evento)`:

```java
RegraElegibilidade regra = seletor.regraPara(evento.getTipo());
return presencas.buscar(p -> p.getEvento().equals(evento)).stream()
        .filter(regra::ehElegivel)
        .map(Presenca::getParticipante)
        .toList();
```

**Testes:** apague o `@Disabled` de `ElegibilidadeTest`. Crie `ServicoPresencaTest` (check-in sem inscrição é recusado; check-in em evento encerrado é recusado) e um teste de `listarElegiveis` com 8h e 4h num minicurso de 8h (só o primeiro é elegível).

Assim que fizer merge, **avise o Dev A** (relatórios) e o **Dev C** (menu).

---

## Parte 4: certificados (Factory Method + Observer)

Branch: `dev-b/certificados`

### Ideia

1. **Factory Method:** três tipos de certificado. `FabricaCertificado.emitir(...)` é igual para todos (gera o código e cria). Só a criação muda, e isso fica em `criar(...)`, que cada fábrica concreta implementa.
2. **Observer:** `EmissorCertificados` implementa `ObservadorEncerramento`. Ao encerrar o evento, o `ServicoEvento` avisa o emissor, que gera os certificados sozinho.
3. **Verificação:** `ServicoVerificacao.verificar(codigo)`.

```
ServicoEvento.encerrarEvento()
    └─> avisa EmissorCertificados.eventoEncerrado(evento)
            ├─ para cada elegível ─> fabricaParticipante.emitir() ─> CertificadoParticipante
            ├─ se houver palestrante ─> fabricaPalestrante.emitir() ─> CertificadoPalestrante
            └─ organizador ─> fabricaOrganizador.emitir() ─> CertificadoOrganizador
```

### O que implementar

1. `GeradorCodigoAleatorio.gerar()`: código `CERT-XXXX-XXXX` (sugestão no TODO usando `UUID`).
2. `FabricaCertificado.emitir(...)`: `return criar(geradorCodigo.gerar(), participante, evento, dataEmissao);`
3. **Criar 3 subclasses de `Certificado`:**
   - `CertificadoParticipante`: tipo `PARTICIPANTE`, papel `"participou como ouvinte do(a)"`.
   - `CertificadoPalestrante`: tipo `PALESTRANTE`, papel `"ministrou o(a)"`.
   - `CertificadoOrganizador`: tipo `ORGANIZADOR`, papel `"organizou o(a)"`.
4. **Criar 3 fábricas** que estendem `FabricaCertificado`:
   ```java
   public class FabricaCertificadoParticipante extends FabricaCertificado {
       public FabricaCertificadoParticipante(GeradorCodigoVerificacao gerador) { super(gerador); }
       @Override
       protected Certificado criar(String codigo, Participante p, Evento e, LocalDate data) {
           return new CertificadoParticipante(codigo, p, e, data);
       }
   }
   ```
   (e o mesmo para `FabricaCertificadoPalestrante` e `FabricaCertificadoOrganizador`)
5. `EmissorCertificados.eventoEncerrado(evento)`: os 3 passos do TODO.
6. `ServicoVerificacao.verificar(codigo)`: ignorar espaços e maiúsculas/minúsculas.

**Testes:** apague o `@Disabled` de `ServicoVerificacaoTest`. Crie `FabricaCertificadoTest` (cada fábrica devolve o tipo certo; o texto tem o nome e a carga) usando um gerador fixo: `GeradorCodigoVerificacao fixo = () -> "CERT-TEST-0001";`. Crie `EmissorCertificadosTest`: 2 elegíveis + palestrante + organizador = 4 certificados.

---

## O que saber explicar na mostra

- Strategy: a diferença para um `if/else`; como incluir "workshop com 90%" (nova classe + `registrar`).
- Por que `PERCENTUAL_MINIMO` é constante e a comparação usa `>=`; o que é `regra::ehElegivel`.
- Factory Method: o que é fixo (`emitir`, `final`) e o que muda (`criar`).
- Por que o gerador de código é uma interface (testes previsíveis).
- Observer: quem avisa quem e por que o `ServicoEvento` não conhece o emissor.
- Limitação: o código é aleatório, mas não há assinatura digital.
