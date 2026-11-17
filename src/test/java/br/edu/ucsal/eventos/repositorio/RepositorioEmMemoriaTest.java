package br.edu.ucsal.eventos.repositorio;

import br.edu.ucsal.eventos.dominio.Participante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioEmMemoriaTest {

    private final Repositorio<Participante, String> repo = new RepositorioEmMemoria<>(Participante::getMatricula);

    @Test
    void salvaEBuscaPorId() {
        Participante p = new Participante("ALU-01", "Davi Exemplo", "davi@exemplo.edu.br");
        repo.salvar(p);
        assertTrue(repo.existe("ALU-01"));
        assertEquals(p, repo.buscarPorId("ALU-01").orElseThrow());
        assertTrue(repo.buscarPorId("NAO-EXISTE").isEmpty());
    }

    @Test
    void buscaComFiltroEListaImutavel() {
        repo.salvar(new Participante("ALU-01", "Davi Exemplo", "davi@exemplo.edu.br"));
        repo.salvar(new Participante("ALU-02", "Elisa Exemplo", "elisa@exemplo.edu.br"));
        assertEquals(2, repo.contar());
        assertEquals(1, repo.buscar(p -> p.getNome().startsWith("E")).size());
        assertThrows(UnsupportedOperationException.class, () -> repo.listarTodos().clear());
    }
}
