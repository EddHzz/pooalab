package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Recusa a inscrição quando o número de inscritos já chegou ao número de vagas.
 * <b>Responsável: Dev A.</b>
 */
public class ValidadorVagas extends ValidadorInscricao {

    private final Repositorio<Inscricao, String> inscricoes;

    public ValidadorVagas(Repositorio<Inscricao, String> inscricoes) {
        this.inscricoes = Objects.requireNonNull(inscricoes);
    }

    @Override
    protected void verificar(Participante participante, Evento evento, LocalDate dataInscricao) {
        int inscritos = inscricoes.buscar(i -> i.getEvento().equals(evento)).size(); // inscritos deste evento
        if (inscritos >= evento.getVagas()) {
            throw new RegraNegocioException("Vagas esgotadas para o evento " + evento.getCodigo()
                    + " (" + inscritos + " de " + evento.getVagas() + ").");
        }
    }
}