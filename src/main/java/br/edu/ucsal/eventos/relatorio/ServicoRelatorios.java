package br.edu.ucsal.eventos.relatorio;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.List;
import java.util.Objects;

/**
 * Relatórios do evento usando <b>Streams e Collections</b>:
 * quem se inscreveu, quem esteve presente e quem tem direito ao certificado.
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
        // TODO Dev A: inscricoes.buscar(...).stream().map(Inscricao::getParticipante)
        //               .sorted(Comparator.comparing(Participante::getNome)).toList()
        throw new UnsupportedOperationException("TODO Dev A: inscritos");
    }

    /** Participantes com check-in no evento, em ordem alfabética de nome. */
    public List<Participante> presentes(Evento evento) {
        // TODO Dev A: mesma ideia de inscritos(), mas usando o repositório de presenças.
        throw new UnsupportedOperationException("TODO Dev A: presentes");
    }

    /** Participantes que cumprem a regra de elegibilidade do evento. */
    public List<Participante> elegiveis(Evento evento) {
        // TODO Dev A: delegar para elegibilidade.listarElegiveis(evento), ordenando por nome.
        throw new UnsupportedOperationException("TODO Dev A: elegiveis");
    }

    /** Resumo em uma linha, por exemplo: "Inscritos: 5 | Presentes: 4 | Elegíveis: 3". */
    public String resumo(Evento evento) {
        return "Inscritos: " + inscritos(evento).size()
                + " | Presentes: " + presentes(evento).size()
                + " | Elegíveis: " + elegiveis(evento).size();
    }
}
