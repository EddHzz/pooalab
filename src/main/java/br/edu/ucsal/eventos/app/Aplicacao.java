package br.edu.ucsal.eventos.app;

import br.edu.ucsal.eventos.certificado.EmissorCertificados;
import br.edu.ucsal.eventos.certificado.FabricaCertificadoOrganizador;
import br.edu.ucsal.eventos.certificado.FabricaCertificadoPalestrante;
import br.edu.ucsal.eventos.certificado.FabricaCertificadoParticipante;
import br.edu.ucsal.eventos.certificado.GeradorCodigoAleatorio;
import br.edu.ucsal.eventos.certificado.GeradorCodigoVerificacao;
import br.edu.ucsal.eventos.certificado.ServicoVerificacao;
import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.dominio.Presenca;
import br.edu.ucsal.eventos.dominio.RegraNegocioException;
import br.edu.ucsal.eventos.evento.ServicoEvento;
import br.edu.ucsal.eventos.inscricao.ServicoInscricao;
import br.edu.ucsal.eventos.inscricao.ValidadorDuplicidade;
import br.edu.ucsal.eventos.inscricao.ValidadorInscricao;
import br.edu.ucsal.eventos.inscricao.ValidadorPrazo;
import br.edu.ucsal.eventos.inscricao.ValidadorVagas;
import br.edu.ucsal.eventos.presenca.SeletorRegraElegibilidade;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.presenca.ServicoPresenca;
import br.edu.ucsal.eventos.relatorio.ServicoRelatorios;
import br.edu.ucsal.eventos.repositorio.Repositorio;
import br.edu.ucsal.eventos.repositorio.RepositorioEmMemoria;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Monta o sistema: cria os repositórios e os serviços e liga uns aos outros.
 * O Main e os testes usam esta classe para não repetir a montagem.
 * Também guarda as pessoas cadastradas.
 */
public class Aplicacao {

    private final Clock relogio;

    private final Repositorio<Participante, String> participantes =
            new RepositorioEmMemoria<>(Participante::getMatricula);
    private final Repositorio<Evento, String> eventos = new RepositorioEmMemoria<>(Evento::getCodigo);
    private final Repositorio<Inscricao, String> inscricoes = new RepositorioEmMemoria<>(Inscricao::getId);
    private final Repositorio<Presenca, String> presencas = new RepositorioEmMemoria<>(Presenca::getId);
    private final Repositorio<Certificado, String> certificados =
            new RepositorioEmMemoria<>(Certificado::getCodigoVerificacao);

    private final ServicoEvento servicoEvento;
    private final ServicoInscricao servicoInscricao;
    private final ServicoPresenca servicoPresenca;
    private final ServicoRelatorios servicoRelatorios;
    private final ServicoVerificacao servicoVerificacao;

    public Aplicacao(Clock relogio) {
        this(relogio, new GeradorCodigoAleatorio());
    }

    // os testes passam um gerador de códigos fixo
    public Aplicacao(Clock relogio, GeradorCodigoVerificacao gerador) {
        this.relogio = Objects.requireNonNull(relogio);
        Objects.requireNonNull(gerador);

        // ordem das validações: prazo, vagas, duplicidade
        ValidadorInscricao cadeia = new ValidadorPrazo();
        cadeia.encadear(new ValidadorVagas(inscricoes)).encadear(new ValidadorDuplicidade(inscricoes));

        ServicoElegibilidade elegibilidade = new ServicoElegibilidade(presencas, new SeletorRegraElegibilidade());

        this.servicoEvento = new ServicoEvento(eventos);
        this.servicoInscricao = new ServicoInscricao(inscricoes, cadeia, relogio);
        this.servicoPresenca = new ServicoPresenca(inscricoes, presencas);
        this.servicoRelatorios = new ServicoRelatorios(inscricoes, presencas, elegibilidade);
        this.servicoVerificacao = new ServicoVerificacao(certificados);

        // quando um evento encerra, o emissor gera os certificados
        servicoEvento.adicionarObservador(new EmissorCertificados(elegibilidade, certificados,
                new FabricaCertificadoParticipante(gerador),
                new FabricaCertificadoPalestrante(gerador),
                new FabricaCertificadoOrganizador(gerador),
                relogio));
    }

    // não aceita matrícula repetida
    public Participante cadastrarParticipante(String matricula, String nome, String email) {
        Participante participante = new Participante(matricula, nome, email);
        if (participantes.existe(participante.getMatricula())) {
            throw new RegraNegocioException("Já existe uma pessoa com a matrícula "
                    + participante.getMatricula() + ".");
        }
        return participantes.salvar(participante);
    }

    // aceita maiúsculas ou minúsculas
    public Participante buscarParticipante(String matricula) {
        String procurada = normalizar(matricula);
        return participantes.buscar(p -> p.getMatricula().equalsIgnoreCase(procurada)).stream()
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException("Pessoa não encontrada: " + procurada));
    }

    public Evento buscarEvento(String codigo) {
        String procurado = normalizar(codigo);
        return eventos.buscar(e -> e.getCodigo().equalsIgnoreCase(procurado)).stream()
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException("Evento não encontrado: " + procurado));
    }

    public List<Participante> listarParticipantes() {
        return participantes.listarTodos();
    }

    public LocalDate hoje() {
        return LocalDate.now(relogio);
    }

    public Clock getRelogio() {
        return relogio;
    }

    public ServicoEvento getServicoEvento() {
        return servicoEvento;
    }

    public ServicoInscricao getServicoInscricao() {
        return servicoInscricao;
    }

    public ServicoPresenca getServicoPresenca() {
        return servicoPresenca;
    }

    public ServicoRelatorios getServicoRelatorios() {
        return servicoRelatorios;
    }

    public ServicoVerificacao getServicoVerificacao() {
        return servicoVerificacao;
    }

    private static String normalizar(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
