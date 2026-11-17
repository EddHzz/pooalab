package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Recusa a inscrição se a mesma pessoa já estiver inscrita no mesmo evento.
 * <b>Responsável: Dev A.</b>
 */
public class ValidadorDuplicidade extends ValidadorInscricao {

    private final Repositorio<Inscricao, String> inscricoes;

    public ValidadorDuplicidade(Repositorio<Inscricao, String> inscricoes) {
        this.inscricoes = Objects.requireNonNull(inscricoes);
    }

    @Override
    protected void verificar(Participante participante, Evento evento, LocalDate dataInscricao) {
        // TODO Dev A: se inscricoes.existe(Inscricao.gerarId(participante, evento)),
        //               lançar RegraNegocioException("... já está inscrito(a) em ...").
        throw new UnsupportedOperationException("TODO Dev A: ValidadorDuplicidade");
    }
}
