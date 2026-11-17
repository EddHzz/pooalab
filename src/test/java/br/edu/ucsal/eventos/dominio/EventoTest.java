package br.edu.ucsal.eventos.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** Testes das regras que o próprio domínio garante (já passam na base). */
class EventoTest {

    private final Participante organizadora = new Participante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
    private final LocalDate prazo = LocalDate.of(2026, 11, 20);

    private Evento novoEvento(int vagas, int carga) {
        return new Evento("EV1", "Palestra de teste", TipoEvento.PALESTRA, vagas, carga, prazo, organizadora, null);
    }

    @Test
    void criaEventoValido() {
        Evento evento = novoEvento(30, 2);
        assertEquals("EV1", evento.getCodigo());
        assertFalse(evento.isEncerrado());
        assertTrue(evento.getPalestrante().isEmpty());
    }

    @Test
    void recusaVagasOuCargaInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> novoEvento(0, 2));
        assertThrows(IllegalArgumentException.class, () -> novoEvento(10, 0));
    }

    @Test
    void prazoValeAteOUltimoDiaInclusive() {
        Evento evento = novoEvento(30, 2);
        assertTrue(evento.prazoAbertoEm(prazo));
        assertFalse(evento.prazoAbertoEm(prazo.plusDays(1)));
    }

    @Test
    void naoEncerraDuasVezes() {
        Evento evento = novoEvento(30, 2);
        evento.encerrar();
        assertTrue(evento.isEncerrado());
        assertThrows(RegraNegocioException.class, evento::encerrar);
    }

    @Test
    void presencaNaoPodePassarDaCargaHoraria() {
        Evento evento = novoEvento(30, 2);
        Participante p = new Participante("ALU-01", "Bruno Fictício", "bruno@exemplo.edu.br");
        assertThrows(IllegalArgumentException.class, () -> new Presenca(p, evento, 3));
        assertEquals("ALU-01:EV1", new Presenca(p, evento, 2).getId());
    }
}
