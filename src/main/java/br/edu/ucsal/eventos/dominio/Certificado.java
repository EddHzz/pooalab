package br.edu.ucsal.eventos.dominio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Certificado emitido ao fim de um evento.
 *
 * <p>É uma classe <b>abstrata</b>: cada papel (participante, palestrante,
 * organizador) vira uma subclasse concreta no pacote {@code certificado},
 * criada pela sua fábrica (padrão Factory Method). As subclasses só precisam
 * dizer qual é o tipo e como descrever o papel da pessoa; o resto já está aqui.</p>
 */
public abstract class Certificado {

    private final String codigoVerificacao;
    private final Participante participante;
    private final Evento evento;
    private final LocalDate dataEmissao;

    protected Certificado(String codigoVerificacao, Participante participante, Evento evento,
                          LocalDate dataEmissao) {
        if (codigoVerificacao == null || codigoVerificacao.isBlank()) {
            throw new IllegalArgumentException("O código de verificação é obrigatório.");
        }
        this.codigoVerificacao = codigoVerificacao;
        this.participante = Objects.requireNonNull(participante, "O participante é obrigatório.");
        this.evento = Objects.requireNonNull(evento, "O evento é obrigatório.");
        this.dataEmissao = Objects.requireNonNull(dataEmissao, "A data de emissão é obrigatória.");
    }

    /** Qual papel este certificado comprova. */
    public abstract TipoCertificado getTipo();

    /** Frase que descreve o papel, por exemplo "participou como ouvinte do". */
    protected abstract String descreverPapel();

    /** Horas comprovadas. Por padrão é a carga do evento; uma subclasse pode mudar. */
    public int getHorasComprovadas() {
        return evento.getCargaHoraria();
    }

    /**
     * Texto final do certificado. Usa os métodos abstratos acima, então cada
     * subclasse gera um texto diferente sem reescrever este método.
     */
    public String gerarTexto() {
        return "Certificamos que " + participante.getNome()
                + " " + descreverPapel()
                + " " + evento.getTipo().getDescricao().toLowerCase()
                + " \"" + evento.getTitulo() + "\""
                + ", com carga horária de " + getHorasComprovadas() + "h."
                + " Emitido em " + dataEmissao
                + ". Código de verificação: " + codigoVerificacao;
    }

    public String getCodigoVerificacao() {
        return codigoVerificacao;
    }

    public Participante getParticipante() {
        return participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    @Override
    public String toString() {
        return getTipo() + " | " + participante.getNome() + " | " + evento.getCodigo() + " | " + codigoVerificacao;
    }
}
