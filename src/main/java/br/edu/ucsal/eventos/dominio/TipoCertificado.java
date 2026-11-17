package br.edu.ucsal.eventos.dominio;

/**
 * Papéis que uma pessoa pode ter em um evento e que geram certificado.
 * Cada papel tem a sua fábrica no pacote {@code certificado} (Factory Method).
 */
public enum TipoCertificado {
    PARTICIPANTE,
    PALESTRANTE,
    ORGANIZADOR
}
