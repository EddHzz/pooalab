package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.TipoCertificado;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import br.edu.ucsal.eventos.presenca.SeletorRegraElegibilidade;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.repositorio.Repositorio;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EmissorCertificadosTest {
    private final Participante org = new Participante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
    private final Participante palestrante = new Participante("PAL-01", "Prof. Caio Exemplo", "caio@exemplo.edu.br");
    private final Participante a1 = new Participante("ALU-01", "João Fictício", "joao@exemplo.edu.br");
    private final Participante a2 = new Participante("ALU-02", "Maria Fictícia", "maria@exemplo.edu.br");
    private final Participante a3 = new Participante("ALU-03", "Pedro Fictício", "pedro@exemplo.edu.br");

    private Repositorio<Certificado, String> emitirEm(Evento evento, Repositorio<Presenca, String> presencas) {
        Repositorio<Certificado, String> certificados = new RepositorioEmMemoria<>(Certificado::getCodigoVerificacao);
        AtomicInteger contador = new AtomicInteger();
        GeradorCodigoVerificacao sequencial = () -> "CERT-TEST-" + contador.incrementAndGet();
        Clock relogio = Clock.fixed(Instant.parse("2026-11-21T12:00:00Z"), ZoneId.of("UTC"));

        EmissorCertificados emissor = new EmissorCertificados(
                new ServicoElegibilidade(presencas, new SeletorRegraElegibilidade()),
                certificados,
                new FabricaCertificadoParticipante(sequencial),
                new FabricaCertificadoPalestrante(sequencial),
                new FabricaCertificadoOrganizador(sequencial),
                relogio);

        emissor.eventoEncerrado(evento);
        return certificados;
    }

    @Test
    void emiteParaElegiveisPalestranteEOrganizador() {
        Evento palestra = new Evento("PL1", "Palestra X", TipoEvento.PALESTRA, 10, 2,
                LocalDate.of(2026, 11, 20), org, palestrante);
        Repositorio<Presenca, String> presencas = new RepositorioEmMemoria<>(Presenca::getId);
        presencas.salvar(new Presenca(a1, palestra, 2));
        presencas.salvar(new Presenca(a2, palestra, 2));

        Repositorio<Certificado, String> certificados = emitirEm(palestra, presencas);

        assertEquals(4, certificados.contar());
        assertEquals(2, certificados.buscar(c -> c.getTipo() == TipoCertificado.PARTICIPANTE).size());
        assertEquals(1, certificados.buscar(c -> c.getTipo() == TipoCertificado.PALESTRANTE).size());
        assertEquals(1, certificados.buscar(c -> c.getTipo() == TipoCertificado.ORGANIZADOR).size());
    }

    @Test
    void naoEmiteParaQuemNaoCumpriuARegraDosSetentaECincoPorCento() {
        Evento minicurso = new Evento("MC1", "Minicurso X", TipoEvento.MINICURSO, 10, 8,
                LocalDate.of(2026, 11, 20), org, null);
        Repositorio<Presenca, String> presencas = new RepositorioEmMemoria<>(Presenca::getId);
        presencas.salvar(new Presenca(a1, minicurso, 8));
        presencas.salvar(new Presenca(a2, minicurso, 6));
        presencas.salvar(new Presenca(a3, minicurso, 4));

        Repositorio<Certificado, String> certificados = emitirEm(minicurso, presencas);

        assertEquals(3, certificados.contar());
        assertTrue(certificados.buscar(c -> c.getParticipante().equals(a3)).isEmpty());
        assertTrue(certificados.buscar(c -> c.getTipo() == TipoCertificado.PALESTRANTE).isEmpty());
    }
}
