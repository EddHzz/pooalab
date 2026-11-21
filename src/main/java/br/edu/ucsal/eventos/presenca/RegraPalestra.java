package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Presenca;

public class RegraPalestra implements RegraElegibilidade {
    @Override
    public boolean ehElegivel(Presenca presenca) {
        return presenca != null;
    }
}
