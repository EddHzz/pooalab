# Roteiro de demonstração (para a estação e o pitch)

> Este roteiro assume os dados de `DadosDemonstracao` sugeridos no guia do Dev C. **Ajustem os códigos e as opções do menu** depois que o menu estiver pronto, e ensaiem com cronômetro.

## Preparação (antes de chamar a banca)

```bash
mvn -q clean package
java -jar target/pooalab-eventos-0.1.0.jar
```

Os dados fictícios já vêm carregados: palestra **PL01**, minicurso **MC01** (3 vagas, 2 já inscritos, 8h), palestra **PL02** (prazo encerrado), participantes **ALU-01** a **ALU-05**.

## Fluxo (cerca de 1 min 40 s)

| # | Ação no menu | O que mostrar |
|---|---|---|
| 1 | Listar eventos | Os três eventos, tipos, vagas e carga horária. |
| 2 | Inscrever `ALU-03` em `MC01` | Inscrição aceita (3/3 vagas). |
| 3 | Inscrever `ALU-04` em `MC01` | **Recusa: vagas esgotadas.** |
| 4 | Inscrever `ALU-03` em `MC01` de novo | **Recusa: inscrição duplicada.** |
| 5 | Inscrever `ALU-01` em `PL02` | **Recusa: prazo encerrado.** |
| 6 | Check-in em `MC01`: `ALU-01` com 8h, `ALU-02` com 6h, `ALU-03` com 4h | Presenças registradas. |
| 7 | Relatório de `MC01` | Inscritos 3, presentes 3, **elegíveis 2** (4h < 75% de 8h). |
| 8 | Encerrar `MC01` | Certificados emitidos automaticamente: 2 participantes + palestrante + organizadora, com códigos. |
| 9 | Verificar o código de um certificado | Certificado encontrado, com o texto completo. |
| 10 | Verificar `CERT-0000-0000` | "Certificado não encontrado." |

## Se algo der errado

1. Opção "Executar demonstração automática" do menu (roda o fluxo inteiro sozinha).
2. Vídeo de contingência (link no README).
