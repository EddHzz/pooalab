package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.List;
import java.util.Objects;

public class ServicoElegibilidade {
    private final Repositorio<Presenca, String> presencas;
    private final SeletorRegraElegibilidade seletor;

    public ServicoElegibilidade(Repositorio<Presenca, String> presencas, SeletorRegraElegibilidade seletor) {
        this.presencas = Objects.requireNonNull(presencas);
        this.seletor = Objects.requireNonNull(seletor);
    }

    public List<Participante> listarElegiveis(Evento evento) {
        RegraElegibilidade regra = seletor.regraPara(evento.getTipo());
        return presencas.buscar(p -> p.getEvento().equals(evento)).stream()
                .filter(regra::ehElegivel)
                .map(Presenca::getParticipante)
                .toList();
    }
}
