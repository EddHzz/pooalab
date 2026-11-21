package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.TipoEvento;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class SeletorRegraElegibilidade {
    private final Map<TipoEvento, RegraElegibilidade> regras = new EnumMap<>(TipoEvento.class);

    public SeletorRegraElegibilidade() {
        registrar(TipoEvento.PALESTRA, new RegraPalestra());
        registrar(TipoEvento.MINICURSO, new RegraMinicurso());
    }

    public void registrar(TipoEvento tipo, RegraElegibilidade regra) {
        regras.put(Objects.requireNonNull(tipo), Objects.requireNonNull(regra));
    }

    public RegraElegibilidade regraPara(TipoEvento tipo) {
        RegraElegibilidade regra = regras.get(tipo);
        if (regra == null) {
            throw new IllegalStateException("Não há regra de elegibilidade cadastrada para o tipo " + tipo);
        }
        return regra;
    }
}
