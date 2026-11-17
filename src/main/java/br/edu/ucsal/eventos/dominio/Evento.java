package br.edu.ucsal.eventos.dominio;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * Evento acadêmico (palestra ou minicurso) criado por um organizador.
 *
 * <p>O próprio evento garante as regras que dependem só dele (vagas e carga
 * horária positivas, não encerrar duas vezes). Regras que dependem de outros
 * objetos (por exemplo, quantas inscrições já existem) ficam nos serviços.</p>
 */
public class Evento {

    private final String codigo;
    private final String titulo;
    private final TipoEvento tipo;
    private final int vagas;
    private final int cargaHoraria;          // em horas
    private final LocalDate prazoInscricao;  // último dia em que é possível se inscrever
    private final Participante organizador;
    private final Participante palestrante;  // pode ser nulo (evento sem palestrante cadastrado)
    private boolean encerrado;

    public Evento(String codigo, String titulo, TipoEvento tipo, int vagas, int cargaHoraria,
                  LocalDate prazoInscricao, Participante organizador, Participante palestrante) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código do evento é obrigatório.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título do evento é obrigatório.");
        }
        if (vagas <= 0) {
            throw new IllegalArgumentException("O evento precisa ter pelo menos 1 vaga.");
        }
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("A carga horária precisa ser maior que zero.");
        }
        this.codigo = codigo.trim();
        this.titulo = titulo.trim();
        this.tipo = Objects.requireNonNull(tipo, "O tipo do evento é obrigatório.");
        this.vagas = vagas;
        this.cargaHoraria = cargaHoraria;
        this.prazoInscricao = Objects.requireNonNull(prazoInscricao, "O prazo de inscrição é obrigatório.");
        this.organizador = Objects.requireNonNull(organizador, "O organizador é obrigatório.");
        this.palestrante = palestrante;
        this.encerrado = false;
    }

    /**
     * Diz se ainda é possível se inscrever na data informada.
     * Recebemos a data como parâmetro (em vez de usar LocalDate.now() aqui dentro)
     * para que os testes consigam simular qualquer dia.
     */
    public boolean prazoAbertoEm(LocalDate data) {
        return !data.isAfter(prazoInscricao);
    }

    /** Marca o evento como encerrado. Encerrar duas vezes é um erro de regra de negócio. */
    public void encerrar() {
        if (encerrado) {
            throw new RegraNegocioException("O evento " + codigo + " já foi encerrado.");
        }
        encerrado = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public int getVagas() {
        return vagas;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public LocalDate getPrazoInscricao() {
        return prazoInscricao;
    }

    public Participante getOrganizador() {
        return organizador;
    }

    /** Optional deixa explícito que o palestrante pode não existir. */
    public Optional<Participante> getPalestrante() {
        return Optional.ofNullable(palestrante);
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Evento e)) {
            return false;
        }
        return codigo.equals(e.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + titulo + " (" + tipo.getDescricao() + ", " + cargaHoraria + "h)";
    }
}
