package br.edu.ucsal.eventos.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipanteTest {

    @Test
    void doisParticipantesComMesmaMatriculaSaoIguais() {
        Participante a = new Participante("ALU-01", "Carla Exemplo", "carla@exemplo.edu.br");
        Participante b = new Participante("ALU-01", "Outro nome", "outro@exemplo.edu.br");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void recusaCamposVazios() {
        assertThrows(IllegalArgumentException.class, () -> new Participante(" ", "Nome", "x@exemplo.edu.br"));
        assertThrows(IllegalArgumentException.class, () -> new Participante("ALU-02", null, "x@exemplo.edu.br"));
    }
}
