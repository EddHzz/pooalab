package br.edu.ucsal.eventos.presenca;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import br.edu.ucsal.eventos.repositorio.Repositorio;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ServicoPresencaTest {
    private final Participante org = new Participante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
    private final Participante aluno = new Participante("ALU-01", "João Fictício", "joao@exemplo.edu.br");
    private final Participante outro = new Participante("ALU-02", "Maria Fictícia", "maria@exemplo.edu.br");

    private Evento minicurso;
    private Repositorio<Inscricao, String> inscricoes;
    private Repositorio<Presenca, String> presencas;
    private ServicoPresenca servico;

    @BeforeEach
    void preparar() {
        minicurso = new Evento("MC1", "Minicurso X", TipoEvento.MINICURSO, 10, 8,
                LocalDate.of(2026, 11, 20), org, null);
        inscricoes = new RepositorioEmMemoria<>(Inscricao::getId);
        presencas = new RepositorioEmMemoria<>(Presenca::getId);
        servico = new ServicoPresenca(inscricoes, presencas);
        inscricoes.salvar(new Inscricao(aluno, minicurso, LocalDate.of(2026, 11, 10)));
    }

    @Test
    void registraCheckInDeQuemEstaInscrito() {
        Presenca presenca = servico.registrarCheckIn(aluno, minicurso, 6);

        assertEquals(6, presenca.getHorasPresentes());
        assertEquals(1, presencas.contar());
    }

    @Test
    void recusaCheckInSemInscricao() {
        assertThrows(RegraNegocioException.class, () -> servico.registrarCheckIn(outro, minicurso, 6));
        assertEquals(0, presencas.contar());
    }

    @Test
    void recusaCheckInEmEventoEncerrado() {
        minicurso.encerrar();

        assertThrows(RegraNegocioException.class, () -> servico.registrarCheckIn(aluno, minicurso, 6));
    }

    @Test
    void registrarDeNovoSubstituiAsHoras() {
        servico.registrarCheckIn(aluno, minicurso, 4);
        servico.registrarCheckIn(aluno, minicurso, 8);

        assertEquals(1, presencas.contar());
        assertEquals(8, presencas.listarTodos().get(0).getHorasPresentes());
    }

    @Test
    void listaApenasOsElegiveisDoMinicurso() {
        inscricoes.salvar(new Inscricao(outro, minicurso, LocalDate.of(2026, 11, 10)));
        servico.registrarCheckIn(aluno, minicurso, 8);
        servico.registrarCheckIn(outro, minicurso, 4);

        ServicoElegibilidade elegibilidade = new ServicoElegibilidade(presencas, new SeletorRegraElegibilidade());

        assertEquals(java.util.List.of(aluno), elegibilidade.listarElegiveis(minicurso));
    }
}
