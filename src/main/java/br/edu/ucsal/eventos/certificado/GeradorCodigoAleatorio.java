package br.edu.ucsal.eventos.certificado;

/**
 * Gera códigos no formato "CERT-XXXX-XXXX" (letras maiúsculas e números).
 * <b>Responsável: Dev B.</b>
 */
public class GeradorCodigoAleatorio implements GeradorCodigoVerificacao {

    @Override
    public String gerar() {
        // TODO Dev B: sugestão simples:
        //   String base = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        //   return "CERT-" + base.substring(0, 4) + "-" + base.substring(4, 8);
        throw new UnsupportedOperationException("TODO Dev B: gerar código");
    }
}
