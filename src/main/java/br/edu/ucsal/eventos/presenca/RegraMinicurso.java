package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Presenca;

/**
 * Minicurso: precisa de pelo menos 75% da carga horária.
 * Exemplo: minicurso de 8h exige 6h de presença (8 * 0,75 = 6).
 * <b>Responsável: Dev B.</b>
 */
public class RegraMinicurso implements RegraElegibilidade {

    /** Percentual mínimo de presença (75%). Constante para não ter "número mágico" no código. */
    public static final double PERCENTUAL_MINIMO = 0.75;

    @Override
    public boolean ehElegivel(Presenca presenca) {
        // TODO Dev B: comparar presenca.getHorasPresentes() com
        //               presenca.getEvento().getCargaHoraria() * PERCENTUAL_MINIMO (usar >=).
        throw new UnsupportedOperationException("TODO Dev B: RegraMinicurso");
    }
}
