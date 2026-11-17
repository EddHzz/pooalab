package br.edu.ucsal.eventos.dominio;

/**
 * Tipos de evento aceitos pelo sistema.
 *
 * <p>O tipo decide a regra de elegibilidade ao certificado (ver pacote
 * {@code presenca}, padrão Strategy): palestra exige presença simples e
 * minicurso exige pelo menos 75% da carga horária.</p>
 */
public enum TipoEvento {
    PALESTRA("Palestra"),
    MINICURSO("Minicurso");

    private final String descricao;

    TipoEvento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
