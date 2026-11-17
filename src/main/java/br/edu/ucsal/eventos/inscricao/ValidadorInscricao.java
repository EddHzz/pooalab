package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;

import java.time.LocalDate;

/**
 * Elo do padrão <b>Chain of Responsibility</b>.
 *
 * <p>Cada validação (prazo, vaga, duplicidade) é uma subclasse. Os elos são
 * ligados em fila: {@code prazo.encadear(vagas).encadear(duplicidade)}.
 * Quando um elo encontra um problema, lança RegraNegocioException e a cadeia
 * para ali. Se não encontrar, repassa para o próximo.</p>
 *
 * <p>Para criar uma regra nova basta criar uma nova subclasse e colocá-la na
 * cadeia, sem editar as outras (princípio Aberto/Fechado).</p>
 *
 * <p><b>Responsável: Dev A.</b> Veja docs/guias/dev-a-eventos-inscricao.md</p>
 */
public abstract class ValidadorInscricao {

    private ValidadorInscricao proximo;

    /**
     * Liga o próximo elo e devolve esse próximo, para permitir escrever em sequência:
     * {@code a.encadear(b).encadear(c);}
     */
    public ValidadorInscricao encadear(ValidadorInscricao proximo) {
        // TODO Dev A: guardar o próximo elo e devolvê-lo.
        throw new UnsupportedOperationException("TODO Dev A: encadear");
    }

    /**
     * Executa este elo e, se passar, o resto da cadeia.
     * É final para que nenhuma subclasse quebre a lógica de repasse.
     */
    public final void validar(Participante participante, Evento evento, LocalDate dataInscricao) {
        // TODO Dev A:
        //  1. chamar verificar(participante, evento, dataInscricao)  -> pode lançar exceção
        //  2. se existir próximo, chamar proximo.validar(...)
        throw new UnsupportedOperationException("TODO Dev A: validar");
    }

    /** A regra específica deste elo. Lança RegraNegocioException quando a inscrição deve ser recusada. */
    protected abstract void verificar(Participante participante, Evento evento, LocalDate dataInscricao);
}
