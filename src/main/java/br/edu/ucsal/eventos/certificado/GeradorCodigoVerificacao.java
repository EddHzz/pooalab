package br.edu.ucsal.eventos.certificado;

/**
 * Gera o código que qualquer pessoa usa para conferir um certificado.
 *
 * <p>É uma interface para que os testes possam usar um gerador previsível
 * (por exemplo, sempre "TESTE-0001") em vez do aleatório.</p>
 *
 * <p><b>Responsável: Dev B.</b> Veja docs/guias/dev-b-presenca-certificados.md</p>
 */
public interface GeradorCodigoVerificacao {

    String gerar();
}
