package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;

import java.time.LocalDate;

public class FabricaCertificadoPalestrante extends FabricaCertificado {
    public FabricaCertificadoPalestrante(GeradorCodigoVerificacao gerador) {
        super(gerador);
    }

    @Override
    protected Certificado criar(String codigo, Participante participante, Evento evento, LocalDate dataEmissao) {
        return new CertificadoPalestrante(codigo, participante, evento, dataEmissao);
    }
}
