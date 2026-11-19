package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
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
        // o id é "matrícula:evento": a mesma pessoa no mesmo evento gera sempre o mesmo id
        if (inscricoes.existe(Inscricao.gerarId(participante, evento))) {
            throw new RegraNegocioException(participante.getNome() + " já está inscrito(a) no evento "
                    + evento.getCodigo() + ".");
        }
    }
}