# Roteiro de demonstração (para a estação e o pitch)

> Este roteiro acompanha o `MenuConsole` e os dados de `DadosDemonstracao`. Se o menu mudar, atualizem aqui. Ensaiem com cronômetro.

## Preparação (antes de chamar a banca)

```bash
mvn -q clean package
java -jar target/pooalab-eventos-0.1.0.jar
```

Alternativa sem gerar o `.jar`: `mvn -q compile exec:java`. No Windows, rode `chcp 65001` antes para os acentos saírem certos.

Os dados fictícios já vêm carregados:

| Código | Evento | Tipo | Vagas | Carga | Prazo | Já inscritos |
|---|---|---|---|---|---|---|
| PL01 | Introdução a Testes Automatizados | Palestra | 30 | 2h | daqui a 7 dias | ALU-01, ALU-05 |
| MC01 | Minicurso de Git na Prática | Minicurso | 3 | 8h | daqui a 7 dias | ALU-01, ALU-02 |
| PL02 | Carreira em Tecnologia | Palestra | 20 | 2h | ontem (encerrado) | ninguém |

Pessoas: participantes **ALU-01** a **ALU-05**, organizadora **ORG-01** e palestrante **PAL-01** (dos três eventos).

## Menu

```
=== POOALAB - Eventos Acadêmicos ===
1. Listar eventos
2. Criar evento
3. Inscrever participante
4. Registrar presença (check-in)
5. Consultar evento e relatório (inscritos, presentes, elegíveis)
6. Encerrar evento e emitir certificados
7. Verificar certificado por código
8. Executar demonstração automática
9. Listar certificados de um evento
10. Listar pessoas cadastradas
11. Cadastrar pessoa
0. Sair
```

Como o menu se comporta:

- Digite o número da opção e Enter. Depois o menu pede os dados, um por linha, e sempre lista os eventos e as pessoas antes de pedir o código ou a matrícula.
- Códigos e matrículas aceitam maiúsculas ou minúsculas (`mc01` = `MC01`).
- Entrada inválida ou regra de negócio violada mostra `Não foi possível: <motivo>` e volta ao menu. O programa não fecha por erro.

## Fluxo principal (cerca de 1 min 40 s)

| # | O que digitar | O que mostrar |
|---|---|---|
| 1 | `1` | Os três eventos, com tipo, carga, vagas ocupadas, prazo e situação. |
| 2 | `3`, `MC01`, `ALU-03` | Inscrição aceita (3/3 vagas). |
| 3 | `3`, `MC01`, `ALU-04` | **Recusa: vagas esgotadas.** |
| 4 | `3`, `PL01`, `ALU-01` | **Recusa: inscrição duplicada** ("já está inscrito(a)"). |
| 5 | `3`, `PL02`, `ALU-01` | **Recusa: inscrições encerradas (prazo).** |
| 6 | `4`, `MC01`, `ALU-01`, `8` | Presença registrada (8h). |
| 7 | `4`, `MC01`, `ALU-02`, `6` | Presença registrada (6h). |
| 8 | `4`, `MC01`, `ALU-03`, `4` | Presença registrada (4h). |
| 9 | `5`, `MC01` | Inscritos 3, presentes 3, **elegíveis 2** (4h < 75% de 8h). |
| 10 | `6`, `MC01` | Evento encerrado e certificados emitidos automaticamente: 2 participantes + palestrante + organizadora, com os códigos. |
| 11 | `7`, um código impresso no passo 10 | Certificado válido, com o texto completo. |
| 12 | `7`, `CERT-0000-0000` | "Certificado não encontrado." |

**Nota sobre a duplicidade.** A cadeia de validação segue a ordem prazo, vagas, duplicidade, e a primeira regra que recusa interrompe as demais. Com o `MC01` lotado, repetir a inscrição de `ALU-03` nele seria recusada por "vagas esgotadas", não por duplicidade. Por isso o passo 4 usa o `PL01`, que tem vaga livre e onde `ALU-01` já está inscrito.

## Fluxo desde o cadastro do evento (opcional, cerca de 1 min)

| # | O que digitar | O que mostrar |
|---|---|---|
| 1 | `11`, `ALU-06`, `Bia Hipotética`, `bia.hipotetica@exemplo.edu.br` | Pessoa cadastrada. |
| 2 | `2`, `PL03`, `Palestra de POO`, `1`, `10`, `2`, Enter (prazo padrão: 7 dias), `ORG-01`, `PAL-01` | Evento criado. |
| 3 | `3`, `PL03`, `ALU-06` | Inscrição aceita (1/10). |
| 4 | `4`, `PL03`, `ALU-06`, `2` | Presença registrada. |
| 5 | `6`, `PL03` | Certificados emitidos: participante, palestrante e organizadora. |
| 6 | `7`, código do certificado de `ALU-06` | Certificado válido. |

Ao criar evento: tipo `1` é palestra e `2` é minicurso; o prazo usa o formato `AAAA-MM-DD`; a matrícula de quem palestra pode ficar em branco.

## Se algo der errado

1. Opção `8` (demonstração automática): roda o fluxo principal inteiro sozinha, imprimindo cada passo. Ela usa dados próprios, então pode ser repetida e não altera o que está no menu.
2. Fechar e abrir o programa de novo: os dados voltam ao estado inicial (ficam só em memória).
3. Vídeo de contingência (link no README).
