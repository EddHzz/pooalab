package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testa o menu simulando o que seria digitado.
 */
class MenuConsoleTest {

    private Aplicacao app;

    @BeforeEach
    void montar() {
        app = new Aplicacao(Clock.fixed(Instant.parse("2026-11-20T12:00:00Z"), ZoneId.of("UTC")));
        new DadosDemonstracao().carregar(app);
    }

    // roda o menu com essas linhas e devolve o que foi impresso
    private String executarMenu(String... linhas) {
        ByteArrayOutputStream memoria = new ByteArrayOutputStream();
        new MenuConsole(app, new Scanner(String.join("\n", linhas)),
                new PrintStream(memoria, true, StandardCharsets.UTF_8)).executar();
        return memoria.toString(StandardCharsets.UTF_8);
    }

    @Test
    void fluxoDoRoteiroPeloMenu() {
        String saida = executarMenu(
                "1",
                "3", "MC01", "ALU-03",
                "3", "MC01", "ALU-04",
                "3", "PL01", "ALU-01",
                "3", "PL02", "ALU-01",
                "4", "MC01", "ALU-01", "8",
                "4", "MC01", "ALU-02", "6",
                "4", "MC01", "ALU-03", "4",
                "5", "MC01",
                "6", "MC01",
                "7", "CERT-0000-0000",
                "0");

        assertTrue(saida.contains("Inscrição aceita"));
        assertTrue(saida.contains("Não foi possível: Vagas esgotadas"));
        assertTrue(saida.contains("já está inscrito(a)"));
        assertTrue(saida.contains("Não foi possível: Inscrições encerradas"));
        assertTrue(saida.contains("Inscritos: 3 | Presentes: 3 | Elegíveis: 2"));
        assertTrue(saida.contains("Certificados emitidos para MC01 (4)"));
        assertTrue(saida.contains("Certificado não encontrado."));
        assertTrue(saida.contains("Até logo!"));

        // verifica um código emitido
        Certificado emitido = app.getServicoVerificacao().emitidosPara(app.buscarEvento("MC01")).get(0);
        String verificacao = executarMenu("7", emitido.getCodigoVerificacao(), "0");
        assertTrue(verificacao.contains("Certificado válido"));
        assertTrue(verificacao.contains(emitido.gerarTexto()));
    }

    @Test
    void criaEventoECadastraPessoaPeloMenu() {
        String saida = executarMenu(
                "11", "alu-06", "Bia Hipotética", "bia.hipotetica@exemplo.edu.br",
                "2", "pl03", "Palestra de POO", "1", "10", "2", "2026-12-01", "ORG-01", "",
                "3", "PL03", "ALU-06",
                "0");

        Evento criado = app.buscarEvento("PL03");
        assertEquals(TipoEvento.PALESTRA, criado.getTipo());
        assertEquals(LocalDate.of(2026, 12, 1), criado.getPrazoInscricao());
        assertTrue(criado.getPalestrante().isEmpty());
        assertEquals(List.of(app.buscarParticipante("ALU-06")), app.getServicoRelatorios().inscritos(criado));
        assertTrue(saida.contains("Evento criado: [PL03]"));
    }

    @Test
    void entradasInvalidasNaoDerrubamOMenu() {
        String saida = executarMenu(
                "abc",                                          // opção inválida
                "",                                             // Enter vazio
                "4", "MC01", "ALU-01", "oito",                  // horas com letras
                "4", "MC01", "ALU-01", "99",                    // horas acima da carga
                "3", "XX99",                                    // evento que não existe
                "3", "MC01", "ALU-99",                          // pessoa que não existe
                "2", "PL01", "Repetido", "1", "5", "2", "", "ORG-01", "", // código repetido
                "2", "PL09", "Tipo errado", "7",                // tipo inválido
                "2", "PL09", "Data errada", "1", "5", "2", "amanhã", // data inválida
                "2", "PL09", "Sem vagas", "1", "0", "2", "", "ORG-01", "", // vagas inválidas
                "9", "MC01",                                    // certificados antes de encerrar
                "1",
                "0");

        assertEquals(2, saida.split("Opção inválida", -1).length - 1);
        assertTrue(saida.contains("\"oito\" não é um número inteiro"));
        assertTrue(saida.contains("As horas de presença devem estar entre 0 e 8"));
        assertTrue(saida.contains("Evento não encontrado: XX99"));
        assertTrue(saida.contains("Pessoa não encontrada: ALU-99"));
        assertTrue(saida.contains("Já existe um evento com o código PL01"));
        assertTrue(saida.contains("tipo inválido"));
        assertTrue(saida.contains("data inválida"));
        assertTrue(saida.contains("pelo menos 1 vaga"));
        assertTrue(saida.contains("o evento ainda não foi encerrado"));
        assertTrue(saida.contains("Até logo!"));
        assertEquals(3, app.getServicoEvento().listarEventos().size());
    }

    @Test
    void entradaQueAcabaNoMeioEncerraSemErro() {
        String saida = executarMenu("3", "MC01");

        assertTrue(saida.contains("Até logo!"));
    }

    @Test
    void demonstracaoAutomaticaRodaOFluxoSemAlterarOsDadosDoMenu() {
        String saida = executarMenu("8", "8", "0");

        assertEquals(2, saida.split("Fim da demonstração automática", -1).length - 1);
        assertTrue(saida.contains("Recusada: Vagas esgotadas"));
        assertTrue(saida.contains("já está inscrito(a) no evento PL01"));
        assertTrue(saida.contains("Recusada: Inscrições encerradas"));
        assertTrue(saida.contains("Inscritos: 3 | Presentes: 3 | Elegíveis: 2"));
        assertTrue(saida.contains("Certificamos que"));
        assertTrue(saida.contains("Certificado não encontrado."));
        assertEquals(2, app.getServicoRelatorios().inscritos(app.buscarEvento("MC01")).size());
        assertTrue(!app.buscarEvento("MC01").isEncerrado());
    }
}
