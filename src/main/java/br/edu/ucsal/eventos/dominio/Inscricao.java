package br.edu.ucsal.eventos.dominio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Inscrição de um participante em um evento.
 *
 * <p>O identificador é formado por "matrícula:códigoDoEvento". Assim, a mesma
 * pessoa no mesmo evento sempre gera o mesmo id, o que facilita detectar
 * inscrição duplicada.</p>
 */
public class Inscricao {

    private final Participante participante;
    private final Evento evento;
    private final LocalDate dataInscricao;

    public Inscricao(Participante participante, Evento evento, LocalDate dataInscricao) {
        this.participante = Objects.requireNonNull(participante, "O participante é obrigatório.");
        this.evento = Objects.requireNonNull(evento, "O evento é obrigatório.");
        this.dataInscricao = Objects.requireNonNull(dataInscricao, "A data da inscrição é obrigatória.");
    }

    /** Monta o id de uma inscrição (ou presença) a partir do participante e do evento. */
    public static String gerarId(Participante participante, Evento evento) {
        return participante.getMatricula() + ":" + evento.getCodigo();
    }

    public String getId() {
        return gerarId(participante, evento);
    }

    public Participante getParticipante() {
        return participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    @Override
    public String toString() {
        return participante.getNome() + " inscrito(a) em " + evento.getCodigo() + " em " + dataInscricao;
    }
}
