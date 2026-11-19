package br.edu.ucsal.eventos.evento;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
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
        // guarda quem quer ser avisado quando um evento encerrar (não aceita nulo)
        observadores.add(Objects.requireNonNull(observador, "O observador não pode ser nulo."));
    }

    /**
     * Cria o evento e salva no repositório.
     * Deve recusar (RegraNegocioException) um código que já exista.
     */
    public Evento criarEvento(String codigo, String titulo, TipoEvento tipo, int vagas, int cargaHoraria,
                              LocalDate prazoInscricao, Participante organizador, Participante palestrante) {
        // regra: dois eventos não podem ter o mesmo código
        if (eventos.existe(codigo)) {
            throw new RegraNegocioException("Já existe um evento com o código " + codigo + ".");
        }
        // o construtor do Evento já valida vagas, carga horária e campos obrigatórios
        Evento evento = new Evento(codigo, titulo, tipo, vagas, cargaHoraria,
                prazoInscricao, organizador, palestrante);
        return eventos.salvar(evento);
    }

    /**
     * Encerra o evento e avisa todos os observadores (que vão emitir os certificados).
     * Deve lançar RegraNegocioException se o código não existir.
     */
    public Evento encerrarEvento(String codigo) {
        // busca o evento; se não existir, Optional vazio vira exceção
        Evento evento = eventos.buscarPorId(codigo)
                .orElseThrow(() -> new RegraNegocioException("Evento não encontrado: " + codigo));
        evento.encerrar(); // o próprio Evento impede encerrar duas vezes
        // Observer: avisa todos os interessados (o emissor de certificados será um deles)
        for (ObservadorEncerramento observador : observadores) {
            observador.eventoEncerrado(evento);
        }
        return evento;
    }

    public List<Evento> listarEventos() {
        return eventos.listarTodos();
    }
}
