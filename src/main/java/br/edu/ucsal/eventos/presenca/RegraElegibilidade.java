package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Presenca;

/**
 * Padrão <b>Strategy</b>: cada tipo de evento tem a sua regra para decidir
 * se uma presença dá direito ao certificado.
 *
 * <p>Quem usa a regra não precisa de if/else por tipo de evento: pede a regra
 * certa ao {@link SeletorRegraElegibilidade} e chama {@code ehElegivel}.</p>
 *
 * <p><b>Responsável: Dev B.</b> Veja docs/guias/dev-b-presenca-certificados.md</p>
 */
public interface RegraElegibilidade {

    /** true se a presença registrada dá direito ao certificado. */
    boolean ehElegivel(Presenca presenca);
}
