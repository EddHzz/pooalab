package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.Objects;

/**
 * Registro de presença (check-in).
 * <p><b>Responsável: Dev B.</b> Veja docs/guias/dev-b-presenca-certificados.md</p>
 */
public class ServicoPresenca {

    private final Repositorio<Inscricao, String> inscricoes;
    private final Repositorio<Presenca, String> presencas;

    public ServicoPresenca(Repositorio<Inscricao, String> inscricoes, Repositorio<Presenca, String> presencas) {
        this.inscricoes = Objects.requireNonNull(inscricoes);
        this.presencas = Objects.requireNonNull(presencas);
    }

    /**
     * Registra a presença com as horas informadas.
     * Regras: o evento não pode estar encerrado e a pessoa precisa estar inscrita
     * (senão RegraNegocioException). Registrar de novo substitui as horas anteriores.
     */
    public Presenca registrarCheckIn(Participante participante, Evento evento, int horasPresentes) {
        // TODO Dev B:
        //  1. evento encerrado -> RegraNegocioException
        //  2. !inscricoes.existe(Inscricao.gerarId(participante, evento)) -> RegraNegocioException
        //  3. criar Presenca (o construtor valida as horas), salvar e devolver
        throw new UnsupportedOperationException("TODO Dev B: registrarCheckIn");
    }

    /** Atalho para palestra: presença com a carga horária completa. */
    public Presenca registrarCheckIn(Participante participante, Evento evento) {
        return registrarCheckIn(participante, evento, evento.getCargaHoraria());
    }
}
