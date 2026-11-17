package br.edu.ucsal.eventos.evento;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.TipoEvento;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Casos de uso do organizador: criar e encerrar eventos.
 * É o "sujeito" (Subject) do padrão Observer.
 *
 * <p><b>Responsável: Dev A.</b> Veja docs/guias/dev-a-eventos-inscricao.md</p>
 */
public class ServicoEvento {

    private final Repositorio<Evento, String> eventos;
    private final List<ObservadorEncerramento> observadores = new ArrayList<>();

    /** As dependências chegam pelo construtor (injeção de dependência). */
    public ServicoEvento(Repositorio<Evento, String> eventos) {
        this.eventos = Objects.requireNonNull(eventos);
    }

    /** Registra alguém para ser avisado quando um evento for encerrado. */
    public void adicionarObservador(ObservadorEncerramento observador) {
        // TODO Dev A: guardar o observador na lista (não aceitar nulo).
        throw new UnsupportedOperationException("TODO Dev A: adicionarObservador");
    }

    /**
     * Cria o evento e salva no repositório.
     * Deve recusar (RegraNegocioException) um código que já exista.
     */
    public Evento criarEvento(String codigo, String titulo, TipoEvento tipo, int vagas, int cargaHoraria,
                              LocalDate prazoInscricao, Participante organizador, Participante palestrante) {
        // TODO Dev A:
        //  1. se eventos.existe(codigo), lançar RegraNegocioException("Já existe um evento com o código ...")
        //  2. criar o Evento (o construtor já valida vagas e carga horária)
        //  3. salvar no repositório e devolver
        throw new UnsupportedOperationException("TODO Dev A: criarEvento");
    }

    /**
     * Encerra o evento e avisa todos os observadores (que vão emitir os certificados).
     * Deve lançar RegraNegocioException se o código não existir.
     */
    public Evento encerrarEvento(String codigo) {
        // TODO Dev A:
        //  1. buscar o evento pelo código (não achou -> RegraNegocioException)
        //  2. chamar evento.encerrar()
        //  3. percorrer a lista de observadores chamando eventoEncerrado(evento)
        //  4. devolver o evento
        throw new UnsupportedOperationException("TODO Dev A: encerrarEvento");
    }

    public List<Evento> listarEventos() {
        return eventos.listarTodos();
    }
}
