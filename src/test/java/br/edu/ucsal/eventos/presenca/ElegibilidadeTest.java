package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ElegibilidadeTest {
    private final Participante org = new Participante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
    private final Participante aluno = new Participante("ALU-01", "João Fictício", "joao@exemplo.edu.br");
    private final Evento minicurso8h = new Evento("MC1", "Minicurso X", TipoEvento.MINICURSO, 10, 8,
            LocalDate.of(2026, 11, 20), org, null);

    @Test
    void minicursoExige75PorCento() {
        RegraElegibilidade regra = new RegraMinicurso();
        assertTrue(regra.ehElegivel(new Presenca(aluno, minicurso8h, 6)));
        assertFalse(regra.ehElegivel(new Presenca(aluno, minicurso8h, 5)));
    }

    @Test
    void seletorEscolheARegraPeloTipo() {
        SeletorRegraElegibilidade seletor = new SeletorRegraElegibilidade();
        assertInstanceOf(RegraPalestra.class, seletor.regraPara(TipoEvento.PALESTRA));
        assertInstanceOf(RegraMinicurso.class, seletor.regraPara(TipoEvento.MINICURSO));
    }
}
