package br.edu.ucsal.eventos.dominio;

import java.util.Objects;

/**
 * Registro de presença (check-in) de um participante em um evento.
 *
 * <p>Guarda quantas horas a pessoa esteve presente. Em uma palestra basta ter
 * presença; em um minicurso as horas são comparadas com a carga horária
 * (regra dos 75%, pacote {@code presenca}).</p>
 */
public class Presenca {

    private final Participante participante;
    private final Evento evento;
    private final int horasPresentes;

    public Presenca(Participante participante, Evento evento, int horasPresentes) {
        this.participante = Objects.requireNonNull(participante, "O participante é obrigatório.");
        this.evento = Objects.requireNonNull(evento, "O evento é obrigatório.");
        if (horasPresentes < 0 || horasPresentes > evento.getCargaHoraria()) {
            throw new IllegalArgumentException(
                    "As horas de presença devem estar entre 0 e " + evento.getCargaHoraria() + ".");
        }
        this.horasPresentes = horasPresentes;
    }

    /** Mesmo formato de id da inscrição: "matrícula:códigoDoEvento". */
    public String getId() {
        return Inscricao.gerarId(participante, evento);
    }

    public Participante getParticipante() {
        return participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public int getHorasPresentes() {
        return horasPresentes;
    }

    @Override
    public String toString() {
        return participante.getNome() + " presente em " + evento.getCodigo() + " (" + horasPresentes + "h)";
    }
}
