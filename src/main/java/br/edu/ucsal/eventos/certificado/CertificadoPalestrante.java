package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.TipoCertificado;

import java.time.LocalDate;

public class CertificadoPalestrante extends Certificado {
    public CertificadoPalestrante(String codigoVerificacao, Participante participante, Evento evento,
                                  LocalDate dataEmissao) {
        super(codigoVerificacao, participante, evento, dataEmissao);
    }

    @Override
    public TipoCertificado getTipo() {
        return TipoCertificado.PALESTRANTE;
    }

    @Override
    protected String descreverPapel() {
        return "ministrou o(a)";
    }
}
