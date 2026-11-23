package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.dominio.TipoCertificado;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testa o fluxo completo: criar evento, inscrever, registrar presença,
 * encerrar e conferir os certificados. Data e códigos são fixos.
 */
class FluxoPrincipalTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 20);

    private Aplicacao app;
    private Participante organizadora;
    private Participante palestrante;
    private Participante alu1;
    private Participante alu2;
    private Participante alu3;

    @BeforeEach
    void montar() {
        Clock relogio = Clock.fixed(Instant.parse("2026-11-20T12:00:00Z"), ZoneId.of("UTC"));
        AtomicInteger contador = new AtomicInteger();
        app = new Aplicacao(relogio, () -> "CERT-TEST-" + contador.incrementAndGet());

        organizadora = app.cadastrarParticipante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
        palestrante = app.cadastrarParticipante("PAL-01", "Prof. Caio Modelo", "caio@exemplo.edu.br");
        alu1 = app.cadastrarParticipante("ALU-01", "João Fictício", "joao@exemplo.edu.br");
        alu2 = app.cadastrarParticipante("ALU-02", "Maria Inventada", "maria@exemplo.edu.br");
        alu3 = app.cadastrarParticipante("ALU-03", "Pedro Imaginário", "pedro@exemplo.edu.br");
    }

    private Evento criarMinicurso(int vagas) {
        return app.getServicoEvento().criarEvento("MC01", "Minicurso de Git na Prática",
                TipoEvento.MINICURSO, vagas, 8, HOJE.plusDays(7), organizadora, palestrante);
    }

    @Test
    void fluxoCompletoDoMinicurso() {
        // cria o evento
        Evento minicurso = criarMinicurso(3);
        assertEquals(List.of(minicurso), app.getServicoEvento().listarEventos());

        // inscreve duas pessoas
        app.getServicoInscricao().inscrever(alu1, minicurso);
        app.getServicoInscricao().inscrever(alu2, minicurso);
        assertEquals(List.of(alu1, alu2), app.getServicoRelatorios().inscritos(minicurso));

        // check-in: 8h e 4h (4h fica abaixo dos 75%)
        app.getServicoPresenca().registrarCheckIn(alu1, minicurso, 8);
        app.getServicoPresenca().registrarCheckIn(alu2, minicurso, 4);
        assertEquals(List.of(alu1), app.getServicoRelatorios().elegiveis(minicurso));
        assertEquals("Inscritos: 2 | Presentes: 2 | Elegíveis: 1", app.getServicoRelatorios().resumo(minicurso));

        // ainda não tem certificado
        assertTrue(app.getServicoVerificacao().emitidosPara(minicurso).isEmpty());

        // encerra o evento e os certificados são emitidos
        app.getServicoEvento().encerrarEvento("MC01");
        assertTrue(minicurso.isEncerrado());

        List<Certificado> emitidos = app.getServicoVerificacao().emitidosPara(minicurso);
        assertEquals(3, emitidos.size(), "1 participante elegível + palestrante + organizadora");

        List<Certificado> deParticipante = emitidos.stream()
                .filter(c -> c.getTipo() == TipoCertificado.PARTICIPANTE).toList();
        assertEquals(1, deParticipante.size());
        assertEquals(alu1, deParticipante.get(0).getParticipante());
        assertTrue(emitidos.stream().anyMatch(c -> c.getTipo() == TipoCertificado.PALESTRANTE
                && c.getParticipante().equals(palestrante)));
        assertTrue(emitidos.stream().anyMatch(c -> c.getTipo() == TipoCertificado.ORGANIZADOR
                && c.getParticipante().equals(organizadora)));
        assertTrue(emitidos.stream().noneMatch(c -> c.getParticipante().equals(alu2)));

        // verifica pelo código
        Certificado certificado = deParticipante.get(0);
        Optional<Certificado> verificado = app.getServicoVerificacao()
                .verificar(certificado.getCodigoVerificacao().toLowerCase());
        assertTrue(verificado.isPresent());
        assertEquals(alu1, verificado.get().getParticipante());
        assertEquals(HOJE, verificado.get().getDataEmissao());
        assertTrue(verificado.get().gerarTexto().contains("João Fictício"));
        assertTrue(verificado.get().gerarTexto().contains("Minicurso de Git na Prática"));

        assertTrue(app.getServicoVerificacao().verificar("CERT-0000-0000").isEmpty());
    }

    @Test
    void inscricaoRecusaDuplicidadeVagaEsgotadaEPrazoEncerrado() {
        Evento minicurso = criarMinicurso(2);
        Evento prazoEncerrado = app.getServicoEvento().criarEvento("PL02", "Carreira em Tecnologia",
                TipoEvento.PALESTRA, 20, 2, HOJE.minusDays(1), organizadora, null);

        app.getServicoInscricao().inscrever(alu1, minicurso);
        assertThrows(RegraNegocioException.class, () -> app.getServicoInscricao().inscrever(alu1, minicurso));

        app.getServicoInscricao().inscrever(alu2, minicurso);
        assertThrows(RegraNegocioException.class, () -> app.getServicoInscricao().inscrever(alu3, minicurso));

        assertThrows(RegraNegocioException.class, () -> app.getServicoInscricao().inscrever(alu1, prazoEncerrado));
        assertEquals(2, app.getServicoRelatorios().inscritos(minicurso).size());
        assertTrue(app.getServicoRelatorios().inscritos(prazoEncerrado).isEmpty());
    }

    @Test
    void presencaExigeInscricaoEEventoAberto() {
        Evento minicurso = criarMinicurso(3);
        app.getServicoInscricao().inscrever(alu1, minicurso);

        assertThrows(RegraNegocioException.class,
                () -> app.getServicoPresenca().registrarCheckIn(alu2, minicurso, 8));

        app.getServicoEvento().encerrarEvento("MC01");
        assertThrows(RegraNegocioException.class,
                () -> app.getServicoPresenca().registrarCheckIn(alu1, minicurso, 8));
        assertThrows(RegraNegocioException.class, () -> app.getServicoInscricao().inscrever(alu2, minicurso));
        assertThrows(RegraNegocioException.class, () -> app.getServicoEvento().encerrarEvento("MC01"));
    }

    @Test
    void palestraCertificaQuemTevePresencaSimples() {
        Evento palestra = app.getServicoEvento().criarEvento("PL01", "Introdução a Testes Automatizados",
                TipoEvento.PALESTRA, 30, 2, HOJE.plusDays(7), organizadora, null);
        app.getServicoInscricao().inscrever(alu1, palestra);
        app.getServicoInscricao().inscrever(alu2, palestra);
        app.getServicoPresenca().registrarCheckIn(alu1, palestra);

        app.getServicoEvento().encerrarEvento("PL01");

        List<Certificado> emitidos = app.getServicoVerificacao().emitidosPara(palestra);
        assertEquals(2, emitidos.size(), "1 participante presente + organizadora (sem palestrante)");
        assertTrue(emitidos.stream().anyMatch(c -> c.getParticipante().equals(alu1)));
        assertFalse(emitidos.stream().anyMatch(c -> c.getParticipante().equals(alu2)));
    }

    @Test
    void cadastroRecusaCodigoEMatriculaRepetidosEBuscaIgnoraMaiusculas() {
        Evento minicurso = criarMinicurso(3);

        assertThrows(RegraNegocioException.class, () -> criarMinicurso(3));
        assertThrows(RegraNegocioException.class,
                () -> app.cadastrarParticipante("ALU-01", "Outra Pessoa", "outra@exemplo.edu.br"));

        assertEquals(minicurso, app.buscarEvento(" mc01 "));
        assertEquals(alu1, app.buscarParticipante("alu-01"));
        assertThrows(RegraNegocioException.class, () -> app.buscarEvento("XX99"));
        assertThrows(RegraNegocioException.class, () -> app.buscarParticipante("ALU-99"));
    }
}
