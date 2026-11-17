package br.edu.ucsal.eventos.dominio;

/**
 * Exceção lançada quando uma regra de negócio é violada, por exemplo:
 * inscrição duplicada, vaga esgotada, prazo encerrado ou check-in sem inscrição.
 *
 * <p>É uma {@link RuntimeException} (não verificada) para não poluir as
 * assinaturas dos métodos. O menu de console captura essa exceção e mostra a
 * mensagem para o usuário, sem derrubar o programa.</p>
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
