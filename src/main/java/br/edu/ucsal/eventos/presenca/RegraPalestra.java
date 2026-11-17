package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Presenca;

/**
 * Palestra: basta ter feito check-in (presença simples).
 * <b>Responsável: Dev B.</b>
 */
public class RegraPalestra implements RegraElegibilidade {

    @Override
    public boolean ehElegivel(Presenca presenca) {
        // TODO Dev B: se existe presença registrada, é elegível (presenca != null).
        throw new UnsupportedOperationException("TODO Dev B: RegraPalestra");
    }
}
