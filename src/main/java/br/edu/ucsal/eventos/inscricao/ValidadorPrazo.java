package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;

import java.time.LocalDate;

/**
 * Recusa a inscrição se o evento já estiver encerrado ou se o prazo tiver passado.
 * <b>Responsável: Dev A.</b>
 */
public class ValidadorPrazo extends ValidadorInscricao {

    @Override
    protected void verificar(Participante participante, Evento evento, LocalDate dataInscricao) {
        if (evento.isEncerrado()) {
            throw new RegraNegocioException("O evento " + evento.getCodigo() + " já foi encerrado.");
        }
        if (!evento.prazoAbertoEm(dataInscricao)) {
            throw new RegraNegocioException("Inscrições encerradas para o evento " + evento.getCodigo()
                    + " (prazo: " + evento.getPrazoInscricao() + ").");
        }
    }
}