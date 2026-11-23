package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.TipoEvento;

import java.time.LocalDate;

/**
 * Dados fictícios para a demonstração.
 * As datas são contadas a partir de hoje, para funcionar em qualquer dia.
 */
public class DadosDemonstracao {

    public static final String PALESTRA = "PL01";
    public static final String MINICURSO = "MC01";
    public static final String PALESTRA_PRAZO_ENCERRADO = "PL02";

    private static final String MATRICULA_ORGANIZADORA = "ORG-01";

    // se já carregou, não faz nada
    public void carregar(Aplicacao app) {
        if (jaCarregado(app)) {
            return;
        }
        Participante organizadora = app.cadastrarParticipante(
                MATRICULA_ORGANIZADORA, "Profa. Ana Exemplo", "ana.exemplo@exemplo.edu.br");
        Participante palestrante = app.cadastrarParticipante(
                "PAL-01", "Prof. Caio Modelo", "caio.modelo@exemplo.edu.br");

        Participante alu1 = app.cadastrarParticipante("ALU-01", "João Fictício", "joao.ficticio@exemplo.edu.br");
        Participante alu2 = app.cadastrarParticipante("ALU-02", "Maria Inventada", "maria.inventada@exemplo.edu.br");
        app.cadastrarParticipante("ALU-03", "Pedro Imaginário", "pedro.imaginario@exemplo.edu.br");
        app.cadastrarParticipante("ALU-04", "Lúcia Simulada", "lucia.simulada@exemplo.edu.br");
        Participante alu5 = app.cadastrarParticipante("ALU-05", "Rafael Amostra", "rafael.amostra@exemplo.edu.br");

        LocalDate hoje = app.hoje();
        Evento palestra = app.getServicoEvento().criarEvento(PALESTRA, "Introdução a Testes Automatizados",
                TipoEvento.PALESTRA, 30, 2, hoje.plusDays(7), organizadora, palestrante);
        // só 3 vagas, para mostrar "vagas esgotadas"
        Evento minicurso = app.getServicoEvento().criarEvento(MINICURSO, "Minicurso de Git na Prática",
                TipoEvento.MINICURSO, 3, 8, hoje.plusDays(7), organizadora, palestrante);
        // prazo vencido, para mostrar "prazo encerrado"
        app.getServicoEvento().criarEvento(PALESTRA_PRAZO_ENCERRADO, "Carreira em Tecnologia",
                TipoEvento.PALESTRA, 20, 2, hoje.minusDays(1), organizadora, palestrante);

        app.getServicoInscricao().inscrever(alu1, minicurso);
        app.getServicoInscricao().inscrever(alu2, minicurso);
        app.getServicoInscricao().inscrever(alu1, palestra);
        app.getServicoInscricao().inscrever(alu5, palestra);
    }

    private boolean jaCarregado(Aplicacao app) {
        return app.listarParticipantes().stream()
                .anyMatch(p -> p.getMatricula().equals(MATRICULA_ORGANIZADORA));
    }
}
