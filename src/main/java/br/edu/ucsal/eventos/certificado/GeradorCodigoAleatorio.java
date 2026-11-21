package br.edu.ucsal.eventos.certificado;

import java.util.UUID;

public class GeradorCodigoAleatorio implements GeradorCodigoVerificacao {
    @Override
    public String gerar() {
        String base = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return "CERT-" + base.substring(0, 4) + "-" + base.substring(4, 8);
    }
}
