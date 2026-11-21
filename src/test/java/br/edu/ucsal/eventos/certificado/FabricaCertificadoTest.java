package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.TipoCertificado;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FabricaCertificadoTest {
    private final GeradorCodigoVerificacao fixo = () -> "CERT-TEST-0001";
    private final Participante pessoa = new Participante("ALU-01", "João Fictício", "joao@exemplo.edu.br");
    private final Evento evento = new Evento("PL1", "Palestra X", TipoEvento.PALESTRA, 10, 2,
            LocalDate.of(2026, 11, 20), pessoa, null);
    private final LocalDate hoje = LocalDate.of(2026, 11, 21);

    @Test
    void cadaFabricaDevolveOTipoCerto() {
        assertEquals(TipoCertificado.PARTICIPANTE,
                new FabricaCertificadoParticipante(fixo).emitir(pessoa, evento, hoje).getTipo());
        assertEquals(TipoCertificado.PALESTRANTE,
                new FabricaCertificadoPalestrante(fixo).emitir(pessoa, evento, hoje).getTipo());
        assertEquals(TipoCertificado.ORGANIZADOR,
                new FabricaCertificadoOrganizador(fixo).emitir(pessoa, evento, hoje).getTipo());
    }

    @Test
    void usaOCodigoDoGeradorEMontaOTexto() {
        Certificado cert = new FabricaCertificadoParticipante(fixo).emitir(pessoa, evento, hoje);

        assertEquals("CERT-TEST-0001", cert.getCodigoVerificacao());
        String texto = cert.gerarTexto();
        assertTrue(texto.contains("João Fictício"));
        assertTrue(texto.contains("2h"));
        assertTrue(texto.contains("participou como ouvinte"));
    }

    @Test
    void geradorAleatorioSegueOFormatoEvitandoRepeticao() {
        GeradorCodigoAleatorio gerador = new GeradorCodigoAleatorio();
        String codigo = gerador.gerar();

        assertTrue(codigo.matches("CERT-[A-Z0-9]{4}-[A-Z0-9]{4}"), codigo);
        assertNotEquals(codigo, gerador.gerar());
    }
}
