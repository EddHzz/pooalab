package br.edu.ucsal.eventos.evento;

import br.edu.ucsal.eventos.dominio.Evento;

/**
 * Padrão <b>Observer</b>: quem quiser ser avisado quando um evento for
 * encerrado implementa esta interface e se registra no {@link ServicoEvento}.
 *
 * <p>O principal observador é o emissor de certificados (pacote
 * {@code certificado}). O ServicoEvento não conhece o emissor: ele só conhece
 * esta interface. Assim dá para acrescentar outros observadores (um log, um
 * relatório) sem alterar o ServicoEvento.</p>
 */
public interface ObservadorEncerramento {

    /** Chamado logo depois que o evento é marcado como encerrado. */
    void eventoEncerrado(Evento evento);
}
