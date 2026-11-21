package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Presenca;

public class RegraMinicurso implements RegraElegibilidade {
    public static final double PERCENTUAL_MINIMO = 0.75;

    @Override
    public boolean ehElegivel(Presenca presenca) {
        double horasExigidas = presenca.getEvento().getCargaHoraria() * PERCENTUAL_MINIMO;
        return presenca.getHorasPresentes() >= horasExigidas;
    }
}
