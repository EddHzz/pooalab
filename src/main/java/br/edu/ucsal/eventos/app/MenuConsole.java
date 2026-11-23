package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.dominio.TipoEvento;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menu do terminal. Só lê o que a pessoa digita, chama os serviços e mostra o resultado.
 * Erro de digitação ou regra não cumprida vira mensagem e o menu continua.
 */
public class MenuConsole {

    private static final String OPCAO_SAIR = "0";
    private static final int DIAS_PRAZO_PADRAO = 7;

    private record Opcao(String descricao, Runnable acao) {
    }

    // lançada quando não há mais nada para ler (Ctrl+D)
    private static class EntradaEncerradaException extends RuntimeException {
    }

    private final Aplicacao app;
    private final Scanner entrada;
    private final PrintStream saida;
    private final Map<String, Opcao> opcoes = new LinkedHashMap<>();

    public MenuConsole(Aplicacao app, Scanner entrada, PrintStream saida) {
        this.app = Objects.requireNonNull(app);
        this.entrada = Objects.requireNonNull(entrada);
        this.saida = Objects.requireNonNull(saida);

        opcoes.put("1", new Opcao("Listar eventos", this::listarEventos));
        opcoes.put("2", new Opcao("Criar evento", this::criarEvento));
        opcoes.put("3", new Opcao("Inscrever participante", this::inscreverParticipante));
        opcoes.put("4", new Opcao("Registrar presença (check-in)", this::registrarPresenca));
        opcoes.put("5", new Opcao("Consultar evento e relatório (inscritos, presentes, elegíveis)",
                this::consultarEvento));
        opcoes.put("6", new Opcao("Encerrar evento e emitir certificados", this::encerrarEvento));
        opcoes.put("7", new Opcao("Verificar certificado por código", this::verificarCertificado));
        opcoes.put("8", new Opcao("Executar demonstração automática", this::executarDemonstracao));
        opcoes.put("9", new Opcao("Listar certificados de um evento", this::listarCertificados));
        opcoes.put("10", new Opcao("Listar pessoas cadastradas", this::listarParticipantes));
        opcoes.put("11", new Opcao("Cadastrar pessoa", this::cadastrarParticipante));
    }

    public void executar() {
        try {
            while (true) {
                mostrarMenu();
                String escolha = lerTexto("Opção: ");
                if (escolha.equals(OPCAO_SAIR)) {
                    break;
                }
                executarOpcao(escolha);
            }
        } catch (EntradaEncerradaException e) {
            saida.println();
        }
        saida.println("Até logo!");
    }

    private void mostrarMenu() {
        saida.println();
        saida.println("=== POOALAB - Eventos Acadêmicos ===");
        opcoes.forEach((numero, opcao) -> saida.println(numero + ". " + opcao.descricao()));
        saida.println(OPCAO_SAIR + ". Sair");
    }

    private void executarOpcao(String escolha) {
        Opcao opcao = opcoes.get(escolha);
        if (opcao == null) {
            saida.println("Opção inválida. Digite um dos números do menu.");
            return;
        }
        try {
            opcao.acao().run();
        } catch (RegraNegocioException | IllegalArgumentException e) {
            saida.println("Não foi possível: " + e.getMessage());
        } catch (EntradaEncerradaException e) {
            throw e; // quem encerra o menu é o executar()
        } catch (RuntimeException e) {
            // qualquer outro erro também não pode fechar o menu
            saida.println("Erro inesperado: " + e);
        }
    }

    // opções do menu

    private void listarEventos() {
        List<Evento> eventos = app.getServicoEvento().listarEventos();
        if (eventos.isEmpty()) {
            saida.println("Nenhum evento cadastrado.");
            return;
        }
        saida.println("Eventos:");
        eventos.forEach(e -> saida.println("  " + resumir(e)));
    }

    private void criarEvento() {
        String codigo = lerTexto("Código do evento (ex.: PL03): ").toUpperCase();
        String titulo = lerTexto("Título: ");
        TipoEvento tipo = lerTipoEvento();
        int vagas = lerInteiro("Vagas: ");
        int carga = lerInteiro("Carga horária (em horas): ");
        LocalDate prazo = lerPrazo();
        listarParticipantes();
        Participante organizador = app.buscarParticipante(lerTexto("Matrícula de quem organiza: "));
        String matriculaPalestrante = lerTexto("Matrícula de quem palestra (Enter se não houver): ");
        Participante palestrante = matriculaPalestrante.isEmpty() ? null
                : app.buscarParticipante(matriculaPalestrante);

        Evento evento = app.getServicoEvento().criarEvento(codigo, titulo, tipo, vagas, carga, prazo,
                organizador, palestrante);
        saida.println("Evento criado: " + resumir(evento));
    }

    private void inscreverParticipante() {
        Evento evento = escolherEvento();
        Participante participante = escolherParticipante();
        Inscricao inscricao = app.getServicoInscricao().inscrever(participante, evento);
        saida.println("Inscrição aceita: " + inscricao + " (" + vagasOcupadas(evento) + ").");
    }

    private void registrarPresenca() {
        Evento evento = escolherEvento();
        List<Participante> inscritos = app.getServicoRelatorios().inscritos(evento);
        saida.println("Inscritos em " + evento.getCodigo() + ": " + (inscritos.isEmpty() ? "ninguém" : inscritos));
        Participante participante = app.buscarParticipante(lerTexto("Matrícula: "));
        int horas = lerInteiro("Horas de presença (0 a " + evento.getCargaHoraria() + "): ");
        Presenca presenca = app.getServicoPresenca().registrarCheckIn(participante, evento, horas);
        saida.println("Presença registrada: " + presenca + ".");
    }

    private void consultarEvento() {
        Evento evento = escolherEvento();
        saida.println(resumir(evento));
        saida.println("  Organização: " + evento.getOrganizador());
        saida.println("  Palestrante: " + evento.getPalestrante().map(Participante::toString).orElse("não informado"));
        saida.println("  " + app.getServicoRelatorios().resumo(evento));
        saida.println("  Inscritos: " + app.getServicoRelatorios().inscritos(evento));
        saida.println("  Presentes: " + app.getServicoRelatorios().presentes(evento));
        saida.println("  Elegíveis ao certificado: " + app.getServicoRelatorios().elegiveis(evento));
    }

    private void encerrarEvento() {
        Evento evento = escolherEvento();
        app.getServicoEvento().encerrarEvento(evento.getCodigo());
        saida.println("Evento " + evento.getCodigo() + " encerrado.");
        mostrarCertificados(evento);
    }

    private void verificarCertificado() {
        String codigo = lerTexto("Código de verificação (ex.: CERT-1A2B-3C4D): ");
        Optional<Certificado> certificado = app.getServicoVerificacao().verificar(codigo);
        if (certificado.isPresent()) {
            saida.println("Certificado válido (" + certificado.get().getTipo() + "):");
            saida.println("  " + certificado.get().gerarTexto());
        } else {
            saida.println("Certificado não encontrado.");
        }
    }

    private void executarDemonstracao() {
        new DemonstracaoAutomatica(app.getRelogio(), saida).executar();
    }

    private void listarCertificados() {
        mostrarCertificados(escolherEvento());
    }

    private void listarParticipantes() {
        saida.println("Pessoas cadastradas:");
        app.listarParticipantes().forEach(p -> saida.println("  " + p.getMatricula() + " - " + p.getNome()));
    }

    private void cadastrarParticipante() {
        String matricula = lerTexto("Matrícula (ex.: ALU-06): ").toUpperCase();
        String nome = lerTexto("Nome (use um nome fictício): ");
        String email = lerTexto("E-mail (ex.: nome@exemplo.edu.br): ");
        saida.println("Pessoa cadastrada: " + app.cadastrarParticipante(matricula, nome, email) + ".");
    }

    // métodos de apoio

    private Evento escolherEvento() {
        listarEventos();
        return app.buscarEvento(lerTexto("Código do evento: "));
    }

    private Participante escolherParticipante() {
        listarParticipantes();
        return app.buscarParticipante(lerTexto("Matrícula: "));
    }

    private void mostrarCertificados(Evento evento) {
        List<Certificado> emitidos = app.getServicoVerificacao().emitidosPara(evento);
        if (emitidos.isEmpty()) {
            saida.println("Nenhum certificado emitido para " + evento.getCodigo()
                    + (evento.isEncerrado() ? "." : " (o evento ainda não foi encerrado)."));
            return;
        }
        saida.println("Certificados emitidos para " + evento.getCodigo() + " (" + emitidos.size() + "):");
        emitidos.forEach(c -> saida.println("  " + c));
    }

    private String resumir(Evento evento) {
        return evento + " | vagas: " + vagasOcupadas(evento)
                + " | inscrições até " + evento.getPrazoInscricao()
                + " | " + (evento.isEncerrado() ? "ENCERRADO" : "aberto");
    }

    private String vagasOcupadas(Evento evento) {
        return app.getServicoRelatorios().inscritos(evento).size() + "/" + evento.getVagas();
    }

    // leitura do teclado

    private String lerTexto(String pergunta) {
        saida.print(pergunta);
        saida.flush();
        if (!entrada.hasNextLine()) {
            throw new EntradaEncerradaException();
        }
        return entrada.nextLine().trim();
    }

    private int lerInteiro(String pergunta) {
        String texto = lerTexto(pergunta);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\"" + texto + "\" não é um número inteiro.");
        }
    }

    private TipoEvento lerTipoEvento() {
        String texto = lerTexto("Tipo (1 = Palestra, 2 = Minicurso): ");
        return switch (texto) {
            case "1" -> TipoEvento.PALESTRA;
            case "2" -> TipoEvento.MINICURSO;
            default -> throw new IllegalArgumentException("tipo inválido, digite 1 ou 2.");
        };
    }

    private LocalDate lerPrazo() {
        LocalDate padrao = app.hoje().plusDays(DIAS_PRAZO_PADRAO);
        String texto = lerTexto("Prazo de inscrição (AAAA-MM-DD, Enter para " + padrao + "): ");
        if (texto.isEmpty()) {
            return padrao;
        }
        try {
            return LocalDate.parse(texto);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("data inválida, use o formato AAAA-MM-DD.");
        }
    }
}
