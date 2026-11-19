package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.*;
import br.edu.ucsal.eventos.repositorio.Repositorio;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes-contrato do Dev A: a cadeia de validação da inscrição.
 * O "hoje" é fixado em 18/11/2026 com Clock.fixed, então o teste não depende do dia real.
 */
class ServicoInscricaoTest {

    private static final ZoneId FUSO = ZoneId.of("America/Bahia");
    private final Clock hoje = Clock.fixed(LocalDate.of(2026, 11, 18).atStartOfDay(FUSO).toInstant(), FUSO);

    private final Participante org = new Participante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
    private final Participante joao = new Participante("ALU-01", "João Fictício", "joao@exemplo.edu.br");
    private final Participante maria = new Participante("ALU-02", "Maria Fictícia", "maria@exemplo.edu.br");

    private Repositorio<Inscricao, String> inscricoes;
    private ServicoInscricao servico;

    @BeforeEach
    void preparar() {
        inscricoes = new RepositorioEmMemoria<>(Inscricao::getId);
        ValidadorInscricao cadeia = new ValidadorPrazo();
        cadeia.encadear(new ValidadorVagas(inscricoes)).encadear(new ValidadorDuplicidade(inscricoes));
        servico = new ServicoInscricao(inscricoes, cadeia, hoje);
    }

    private Evento evento(int vagas, LocalDate prazo) {
        return new Evento("EV1", "Palestra X", TipoEvento.PALESTRA, vagas, 2, prazo, org, null);
    }

    @Test
    void inscreveComSucesso() {
        Inscricao i = servico.inscrever(joao, evento(10, LocalDate.of(2026, 11, 20)));
        assertEquals("ALU-01:EV1", i.getId());
        assertEquals(1, inscricoes.contar());
    }

    @Test
    void bloqueiaDuplicada() {
        Evento ev = evento(10, LocalDate.of(2026, 11, 20));
        servico.inscrever(joao, ev);
        assertThrows(RegraNegocioException.class, () -> servico.inscrever(joao, ev));
    }

    @Test
    void bloqueiaVagaEsgotada() {
        Evento ev = evento(1, LocalDate.of(2026, 11, 20));
        servico.inscrever(joao, ev);
        assertThrows(RegraNegocioException.class, () -> servico.inscrever(maria, ev));
    }

    @Test
    void bloqueiaPrazoEncerrado() {
        Evento ev = evento(10, LocalDate.of(2026, 11, 17)); // prazo foi ontem
        assertThrows(RegraNegocioException.class, () -> servico.inscrever(joao, ev));
        assertEquals(0, inscricoes.contar());
    }

    @Test
    void ultimoDiaDoPrazoAindaAceita() {
        Evento ev = evento(10, LocalDate.of(2026, 11, 18)); // o "hoje" do teste é 18/11
        assertNotNull(servico.inscrever(joao, ev));
    }
}