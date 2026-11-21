package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.Objects;

public class ServicoPresenca {
    private final Repositorio<Inscricao, String> inscricoes;
    private final Repositorio<Presenca, String> presencas;

    public ServicoPresenca(Repositorio<Inscricao, String> inscricoes, Repositorio<Presenca, String> presencas) {
        this.inscricoes = Objects.requireNonNull(inscricoes);
        this.presencas = Objects.requireNonNull(presencas);
    }

    public Presenca registrarCheckIn(Participante participante, Evento evento, int horasPresentes) {
        if (evento.isEncerrado()) {
            throw new RegraNegocioException("O evento " + evento.getCodigo() + " já foi encerrado.");
        }
        if (!inscricoes.existe(Inscricao.gerarId(participante, evento))) {
            throw new RegraNegocioException(participante.getNome() + " não está inscrito(a) no evento "
                    + evento.getCodigo() + ".");
        }

        Presenca presenca = new Presenca(participante, evento, horasPresentes);

        return presencas.salvar(presenca);
    }

    public Presenca registrarCheckIn(Participante participante, Evento evento) {
        return registrarCheckIn(participante, evento, evento.getCargaHoraria());
    }
}
