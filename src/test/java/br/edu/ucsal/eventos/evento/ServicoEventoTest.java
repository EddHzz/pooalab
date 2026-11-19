package br.edu.ucsal.eventos.evento;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes-contrato da Dev A. Remova o @Disabled quando implementar o ServicoEvento
 * e acrescente pelo menos mais um teste seu.
 */
class ServicoEventoTest {

    private ServicoEvento servico;
    private final Participante organizador = new Participante("ORG-01", "Prof. Caio Exemplo", "caio@exemplo.edu.br");

    @BeforeEach
    void preparar() {
        servico = new ServicoEvento(new RepositorioEmMemoria<>(Evento::getCodigo));
    }

    @Test
    void criaEventoEListar() {
        servico.criarEvento("EV1", "Palestra X", TipoEvento.PALESTRA, 10, 2,
                LocalDate.of(2026, 11, 20), organizador, null);
        assertEquals(1, servico.listarEventos().size());
    }

    @Test
    void recusaCodigoRepetido() {
        servico.criarEvento("EV1", "Palestra X", TipoEvento.PALESTRA, 10, 2,
                LocalDate.of(2026, 11, 20), organizador, null);
        assertThrows(RegraNegocioException.class, () -> servico.criarEvento("EV1", "Outra", TipoEvento.PALESTRA,
                10, 2, LocalDate.of(2026, 11, 20), organizador, null));
    }

    @Test
    void encerrarAvisaOsObservadores() {
        servico.criarEvento("EV1", "Palestra X", TipoEvento.PALESTRA, 10, 2,
                LocalDate.of(2026, 11, 20), organizador, null);
        List<String> avisados = new ArrayList<>();
        servico.adicionarObservador(evento -> avisados.add(evento.getCodigo())); // observador com lambda

        servico.encerrarEvento("EV1");

        assertEquals(List.of("EV1"), avisados);
    }


    @Test
    void encerrarCodigoInexistenteLancaExcecao() {
        assertThrows(RegraNegocioException.class, () -> servico.encerrarEvento("NAO-EXISTE"));
    }
}

