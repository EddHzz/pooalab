package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;

import java.io.PrintStream;
import java.time.Clock;
import java.util.List;
import java.util.Objects;

/**
 * Roda o roteiro da demonstração sozinho e imprime cada passo.
 * Usa dados próprios, então não mexe no que está no menu.
 */
public class DemonstracaoAutomatica {

    private static final String CODIGO_INEXISTENTE = "CERT-0000-0000";

    private final Clock relogio;
    private final PrintStream saida;
    private int passo;

    public DemonstracaoAutomatica(Clock relogio, PrintStream saida) {
        this.relogio = Objects.requireNonNull(relogio);
        this.saida = Objects.requireNonNull(saida);
    }

    public void executar() {
        passo = 0;
        Aplicacao app = new Aplicacao(relogio);
        new DadosDemonstracao().carregar(app);
        Evento minicurso = app.buscarEvento(DadosDemonstracao.MINICURSO);

        saida.println();
        saida.println("--- Demonstração automática (dados fictícios, separados dos dados do menu) ---");

        titulo("Eventos cadastrados");
        app.getServicoEvento().listarEventos().forEach(e -> saida.println("  " + e));

        inscrever(app, "ALU-03", DadosDemonstracao.MINICURSO);
        inscrever(app, "ALU-04", DadosDemonstracao.MINICURSO);
        inscrever(app, "ALU-01", DadosDemonstracao.PALESTRA); // já está inscrito
        inscrever(app, "ALU-01", DadosDemonstracao.PALESTRA_PRAZO_ENCERRADO);

        checkIn(app, "ALU-01", minicurso, 8);
        checkIn(app, "ALU-02", minicurso, 6);
        checkIn(app, "ALU-03", minicurso, 4);

        titulo("Relatório de " + minicurso.getCodigo() + " (mínimo de 75% da carga para o certificado)");
        saida.println("  " + app.getServicoRelatorios().resumo(minicurso));
        saida.println("  Elegíveis: " + app.getServicoRelatorios().elegiveis(minicurso));

        titulo("Encerrar " + minicurso.getCodigo() + ": certificados emitidos automaticamente");
        app.getServicoEvento().encerrarEvento(minicurso.getCodigo());
        List<Certificado> emitidos = app.getServicoVerificacao().emitidosPara(minicurso);
        emitidos.forEach(c -> saida.println("  " + c));

        if (!emitidos.isEmpty()) {
            verificar(app, emitidos.get(0).getCodigoVerificacao());
        }
        verificar(app, CODIGO_INEXISTENTE);

        saida.println();
        saida.println("--- Fim da demonstração automática ---");
    }

    private void inscrever(Aplicacao app, String matricula, String codigoEvento) {
        titulo("Inscrever " + matricula + " em " + codigoEvento);
        try {
            app.getServicoInscricao().inscrever(app.buscarParticipante(matricula), app.buscarEvento(codigoEvento));
            saida.println("  Inscrição aceita.");
        } catch (RegraNegocioException e) {
            saida.println("  Recusada: " + e.getMessage());
        }
    }

    private void checkIn(Aplicacao app, String matricula, Evento evento, int horas) {
        titulo("Check-in de " + matricula + " em " + evento.getCodigo() + " com " + horas + "h");
        saida.println("  " + app.getServicoPresenca().registrarCheckIn(app.buscarParticipante(matricula), evento, horas));
    }

    private void verificar(Aplicacao app, String codigo) {
        titulo("Verificar o código " + codigo);
        saida.println("  " + app.getServicoVerificacao().verificar(codigo)
                .map(Certificado::gerarTexto)
                .orElse("Certificado não encontrado."));
    }

    private void titulo(String texto) {
        passo++;
        saida.println();
        saida.println("Passo " + passo + ": " + texto);
    }
}
