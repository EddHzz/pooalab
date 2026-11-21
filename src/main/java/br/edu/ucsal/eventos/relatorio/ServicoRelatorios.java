package br.edu.ucsal.eventos.relatorio;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Relatórios do evento usando <b>Streams e Collections</b>:
 * quem se inscreveu, quem esteve presente e quem tem direito ao certificado.
 *
 * <p>Todos os métodos seguem o mesmo "caminho" de Stream:
 * filtrar pelo evento -> transformar em Participante -> ordenar por nome -> virar lista.</p>
 *
 * <p><b>Responsável: Dev A.</b> Veja docs/guias/dev-a-eventos-inscricao.md</p>
 */
public class ServicoRelatorios {

    private final Repositorio<Inscricao, String> inscricoes;
    private final Repositorio<Presenca, String> presencas;
    private final ServicoElegibilidade elegibilidade;

    public ServicoRelatorios(Repositorio<Inscricao, String> inscricoes,
                             Repositorio<Presenca, String> presencas,
                             ServicoElegibilidade elegibilidade) {
        this.inscricoes = Objects.requireNonNull(inscricoes);
        this.presencas = Objects.requireNonNull(presencas);
        this.elegibilidade = Objects.requireNonNull(elegibilidade);
    }

    /** Participantes inscritos no evento, em ordem alfabética de nome. */
    public List<Participante> inscritos(Evento evento) {
        return inscricoes.buscar(i -> i.getEvento().equals(evento)).stream() // filter: só as inscrições deste evento
                .map(Inscricao::getParticipante)                             // map: de Inscricao para Participante
                .sorted(Comparator.comparing(Participante::getNome))         // sorted: ordem alfabética pelo nome
                .toList();                                                   // toList: junta o resultado numa lista
    }

    /** Participantes com check-in no evento, em ordem alfabética de nome. */
    public List<Participante> presentes(Evento evento) {
        // mesma ideia de inscritos(), mas olhando o repositório de presenças
        return presencas.buscar(p -> p.getEvento().equals(evento)).stream()
                .map(Presenca::getParticipante)
                .sorted(Comparator.comparing(Participante::getNome))
                .toList();
    }

    /** Participantes que cumprem a regra de elegibilidade do evento. */
    public List<Participante> elegiveis(Evento evento) {
        // quem decide a regra (palestra ou 75% do minicurso) é o ServicoElegibilidade (Strategy, do Dev B);
        // aqui só ordenamos o resultado por nome
        return elegibilidade.listarElegiveis(evento).stream()
                .sorted(Comparator.comparing(Participante::getNome))
                .toList();
    }

    /** Resumo em uma linha, por exemplo: "Inscritos: 5 | Presentes: 4 | Elegíveis: 3". */
    public String resumo(Evento evento) {
        return "Inscritos: " + inscritos(evento).size()
                + " | Presentes: " + presentes(evento).size()
                + " | Elegíveis: " + elegiveis(evento).size();
    }
}