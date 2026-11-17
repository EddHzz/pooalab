package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.List;
import java.util.Objects;

/**
 * Descobre quem tem direito ao certificado de participante em um evento.
 * <p><b>Responsável: Dev B.</b></p>
 */
public class ServicoElegibilidade {

    private final Repositorio<Presenca, String> presencas;
    private final SeletorRegraElegibilidade seletor;

    public ServicoElegibilidade(Repositorio<Presenca, String> presencas, SeletorRegraElegibilidade seletor) {
        this.presencas = Objects.requireNonNull(presencas);
        this.seletor = Objects.requireNonNull(seletor);
    }

    /** Participantes do evento cuja presença cumpre a regra do tipo do evento. */
    public List<Participante> listarElegiveis(Evento evento) {
        // TODO Dev B: com Streams:
        //   RegraElegibilidade regra = seletor.regraPara(evento.getTipo());
        //   presencas.buscar(p -> p.getEvento().equals(evento)).stream()
        //       .filter(regra::ehElegivel).map(Presenca::getParticipante).toList();
        throw new UnsupportedOperationException("TODO Dev B: listarElegiveis");
    }
}
