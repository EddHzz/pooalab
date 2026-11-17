package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;

import java.time.LocalDate;

/**
 * Recusa a inscrição se o evento já estiver encerrado ou se o prazo tiver passado.
 * <b>Responsável: Dev A.</b>
 */
public class ValidadorPrazo extends ValidadorInscricao {

    @Override
    protected void verificar(Participante participante, Evento evento, LocalDate dataInscricao) {
        // TODO Dev A: se evento.isEncerrado() ou !evento.prazoAbertoEm(dataInscricao),
        //               lançar RegraNegocioException("Inscrições encerradas para ...").
        throw new UnsupportedOperationException("TODO Dev A: ValidadorPrazo");
    }
}
