package br.edu.ucsal.eventos.relatorio;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import br.edu.ucsal.eventos.presenca.SeletorRegraElegibilidade;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.repositorio.Repositorio;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes dos relatórios (Streams): inscritos, presentes e elegíveis.
 *
 * <p>Cenário: minicurso de 8h. Bruno ficou 8h (elegível), Ana ficou 4h
 * (abaixo dos 75%, não elegível) e Carla se inscreveu mas não fez check-in.
 * Os nomes foram escolhidos fora de ordem para testar a ordenação alfabética.</p>
 */
class ServicoRelatoriosTest {

    private final Participante org = new Participante("ORG-01", "Profa. Exemplo", "org@exemplo.edu.br");
    private final Participante bruno = new Participante("ALU-02", "Bruno Fictício", "bruno@exemplo.edu.br");
    private final Participante ana = new Participante("ALU-01", "Ana Fictícia", "ana@exemplo.edu.br");
    private final Participante carla = new Participante("ALU-03", "Carla Fictícia", "carla@exemplo.edu.br");
    private final Evento minicurso = new Evento("MC1", "Minicurso X", TipoEvento.MINICURSO, 10, 8,
            LocalDate.of(2026, 11, 30), org, null);

    private ServicoRelatorios relatorios;

    /** Roda antes de cada teste: monta os repositórios com 3 inscritos e 2 presenças. */
    @BeforeEach
    void preparar() {
        Repositorio<Inscricao, String> inscricoes = new RepositorioEmMemoria<>(Inscricao::getId);
        Repositorio<Presenca, String> presencas = new RepositorioEmMemoria<>(Presenca::getId);
        LocalDate dia = LocalDate.of(2026, 11, 18);
        inscricoes.salvar(new Inscricao(bruno, minicurso, dia));
        inscricoes.salvar(new Inscricao(ana, minicurso, dia));
        inscricoes.salvar(new Inscricao(carla, minicurso, dia));
        presencas.salvar(new Presenca(bruno, minicurso, 8)); // 8h de 8h = 100%
        presencas.salvar(new Presenca(ana, minicurso, 4));   // 4h de 8h = 50%
        relatorios = new ServicoRelatorios(inscricoes, presencas,
                new ServicoElegibilidade(presencas, new SeletorRegraElegibilidade()));
    }

    /** Os 3 inscritos aparecem, em ordem alfabética (Ana, Bruno, Carla). */
    @Test
    void listaInscritosEmOrdemAlfabetica() {
        assertEquals(List.of(ana, bruno, carla), relatorios.inscritos(minicurso));
    }

    /** Carla não fez check-in, então não aparece entre os presentes. */
    @Test
    void listaSoQuemFezCheckIn() {
        assertEquals(List.of(ana, bruno), relatorios.presentes(minicurso));
    }

    /** Só o Bruno cumpre os 75% do minicurso; o resumo junta os 3 números. */
    @Test
    void elegiveisSeguemARegraDos75PorCento() {
        assertEquals(List.of(bruno), relatorios.elegiveis(minicurso));
        assertEquals("Inscritos: 3 | Presentes: 2 | Elegíveis: 1", relatorios.resumo(minicurso));
    }
}