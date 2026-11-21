package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.TipoCertificado;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import br.edu.ucsal.eventos.repositorio.Repositorio;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ServicoVerificacaoTest {
    @Test
    void encontraCertificadoIgnorandoEspacosEMaiusculas() {
        Participante org = new Participante("ORG-01", "Profa. Ana Exemplo", "ana@exemplo.edu.br");
        Evento ev = new Evento("EV1", "Palestra X", TipoEvento.PALESTRA, 10, 2, LocalDate.of(2026, 11, 20), org, null);
        Certificado cert = new Certificado("CERT-AB12-CD34", org, ev, LocalDate.of(2026, 11, 21)) {
            @Override public TipoCertificado getTipo() { return TipoCertificado.ORGANIZADOR; }
            @Override protected String descreverPapel() { return "organizou a"; }
        };
        Repositorio<Certificado, String> repo = new RepositorioEmMemoria<>(Certificado::getCodigoVerificacao);
        repo.salvar(cert);

        ServicoVerificacao servico = new ServicoVerificacao(repo);

        assertTrue(servico.verificar(" cert-ab12-cd34 ").isPresent());
        assertTrue(servico.verificar("CERT-0000-0000").isEmpty());
        assertTrue(servico.verificar("").isEmpty());
    }
}
