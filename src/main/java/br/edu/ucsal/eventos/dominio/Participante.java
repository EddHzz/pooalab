package br.edu.ucsal.eventos.dominio;

import java.util.Objects;

/**
 * Pessoa que participa de um evento (como ouvinte, palestrante ou organizador).
 *
 * <p>Os dados usados no projeto são sempre fictícios. A matrícula é o
 * identificador: dois objetos com a mesma matrícula representam a mesma pessoa
 * (por isso {@code equals} e {@code hashCode} usam só a matrícula).</p>
 */
public class Participante {

    private final String matricula;
    private final String nome;
    private final String email;

    public Participante(String matricula, String nome, String email) {
        this.matricula = exigirTexto(matricula, "matrícula");
        this.nome = exigirTexto(nome, "nome");
        this.email = exigirTexto(email, "e-mail");
    }

    /** Validação simples de entrada: nenhum campo pode ser nulo ou vazio. */
    private static String exigirTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Participante p)) {
            return false;
        }
        return matricula.equals(p.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return nome + " (" + matricula + ")";
    }
}
