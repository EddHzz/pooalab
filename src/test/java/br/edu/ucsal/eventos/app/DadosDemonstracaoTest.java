package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DadosDemonstracaoTest {

    private Aplicacao app;

    @BeforeEach
    void carregar() {
        app = new Aplicacao(Clock.fixed(Instant.parse("2026-11-20T12:00:00Z"), ZoneId.of("UTC")));
        new DadosDemonstracao().carregar(app);
    }

    @Test
    void cadastraPessoasEEventosFicticiosDasDuasModalidades() {
        assertEquals(7, app.listarParticipantes().size());
        assertTrue(app.listarParticipantes().stream().allMatch(p -> p.getEmail().endsWith("@exemplo.edu.br")));

        assertEquals(3, app.getServicoEvento().listarEventos().size());
        assertEquals(TipoEvento.PALESTRA, app.buscarEvento("PL01").getTipo());
        assertEquals(TipoEvento.MINICURSO, app.buscarEvento("MC01").getTipo());
    }

    @Test
    void minicursoFicaComUmaVagaLivreEPalestraAntigaComPrazoEncerrado() {
        Evento minicurso = app.buscarEvento("MC01");
        assertEquals(3, minicurso.getVagas());
        assertEquals(2, app.getServicoRelatorios().inscritos(minicurso).size());

        app.getServicoInscricao().inscrever(app.buscarParticipante("ALU-03"), minicurso);
        assertThrows(RegraNegocioException.class,
                () -> app.getServicoInscricao().inscrever(app.buscarParticipante("ALU-04"), minicurso));
        assertThrows(RegraNegocioException.class,
                () -> app.getServicoInscricao().inscrever(app.buscarParticipante("ALU-01"), app.buscarEvento("PL02")));
    }

    @Test
    void carregarDuasVezesNaoDuplicaNemFalha() {
        new DadosDemonstracao().carregar(app);

        assertEquals(7, app.listarParticipantes().size());
        assertEquals(3, app.getServicoEvento().listarEventos().size());
        assertEquals(2, app.getServicoRelatorios().inscritos(app.buscarEvento("MC01")).size());
    }
}
