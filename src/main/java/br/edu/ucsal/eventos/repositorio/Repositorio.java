package br.edu.ucsal.eventos.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Contrato genérico de armazenamento.
 *
 * <p>Os serviços dependem desta <b>interface</b>, nunca da classe concreta
 * (Princípio da Inversão de Dependência, o "D" do SOLID). Hoje a única
 * implementação guarda tudo em memória; se um dia houver banco de dados, basta
 * criar outra implementação sem mexer nos serviços (Aberto/Fechado, o "O").</p>
 *
 * @param <T>  tipo da entidade guardada (Evento, Inscricao, ...)
 * @param <ID> tipo do identificador (aqui sempre String)
 */
public interface Repositorio<T, ID> {

    /** Guarda a entidade. Se já existir uma com o mesmo id, ela é substituída. */
    T salvar(T entidade);

    /** Busca pelo id. Optional vazio quando não existe (evita null). */
    Optional<T> buscarPorId(ID id);

    /** Todas as entidades, na ordem em que foram salvas. A lista não pode ser alterada. */
    List<T> listarTodos();

    boolean existe(ID id);

    int contar();

    /**
     * Filtra as entidades com um critério qualquer, usando Streams.
     * Exemplo: {@code repo.buscar(i -> i.getEvento().equals(evento))}.
     * Método default: toda implementação já ganha este comportamento de graça.
     */
    default List<T> buscar(Predicate<T> criterio) {
        return listarTodos().stream().filter(criterio).toList();
    }
}
