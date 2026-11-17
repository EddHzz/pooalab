package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.TipoEvento;

import java.util.EnumMap;
import java.util.Map;

/**
 * Escolhe a estratégia de elegibilidade certa para cada tipo de evento.
 *
 * <p>Usa um {@link EnumMap} (tipo -> regra) em vez de if/else. Para um novo
 * tipo de evento, basta registrar uma nova regra.</p>
 *
 * <p><b>Responsável: Dev B.</b></p>
 */
public class SeletorRegraElegibilidade {

    private final Map<TipoEvento, RegraElegibilidade> regras = new EnumMap<>(TipoEvento.class);

    /** Já vem com as duas regras do MVP registradas. */
    public SeletorRegraElegibilidade() {
        // TODO Dev B: registrar(TipoEvento.PALESTRA, new RegraPalestra());
        //               registrar(TipoEvento.MINICURSO, new RegraMinicurso());
    }

    public void registrar(TipoEvento tipo, RegraElegibilidade regra) {
        // TODO Dev B: guardar no mapa.
        throw new UnsupportedOperationException("TODO Dev B: registrar");
    }

    /** Devolve a regra do tipo, ou lança IllegalStateException se não houver regra cadastrada. */
    public RegraElegibilidade regraPara(TipoEvento tipo) {
        // TODO Dev B: buscar no mapa; se não achar, lançar IllegalStateException.
        throw new UnsupportedOperationException("TODO Dev B: regraPara");
    }
}
