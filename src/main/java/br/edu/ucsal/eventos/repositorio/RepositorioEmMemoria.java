package br.edu.ucsal.eventos.repositorio;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Implementação de {@link Repositorio} que guarda os dados em um Map, na memória.
 * Ao fechar o programa os dados somem; isso é esperado no MVP (sem banco de dados).
 *
 * <p>Como cada entidade tem o id em um lugar diferente, quem cria o repositório
 * informa uma função que extrai o id. Exemplo:
 * {@code new RepositorioEmMemoria<Evento, String>(Evento::getCodigo)}.</p>
 */
public class RepositorioEmMemoria<T, ID> implements Repositorio<T, ID> {

    // LinkedHashMap mantém a ordem de inserção, o que deixa as listagens previsíveis.
    private final Map<ID, T> dados = new LinkedHashMap<>();
    private final Function<T, ID> extratorDeId;

    public RepositorioEmMemoria(Function<T, ID> extratorDeId) {
        this.extratorDeId = Objects.requireNonNull(extratorDeId);
    }

    @Override
    public T salvar(T entidade) {
        Objects.requireNonNull(entidade, "Não é possível salvar uma entidade nula.");
        dados.put(extratorDeId.apply(entidade), entidade);
        return entidade;
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<T> listarTodos() {
        return List.copyOf(dados.values()); // cópia imutável: ninguém altera o repositório "por fora"
    }

    @Override
    public boolean existe(ID id) {
        return dados.containsKey(id);
    }

    @Override
    public int contar() {
        return dados.size();
    }
}
