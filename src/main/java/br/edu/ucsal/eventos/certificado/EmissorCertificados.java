package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.evento.ObservadorEncerramento;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.time.Clock;
import java.util.Objects;

/**
 * Observador que emite os certificados quando um evento é encerrado (padrão Observer).
 *
 * <p>Ao ser avisado, emite: um certificado de participante para cada elegível,
 * um de palestrante (se o evento tiver palestrante) e um de organizador.
 * Recebe as três fábricas prontas pelo construtor, sem conhecer as classes concretas.</p>
 *
 * <p><b>Responsável: Dev B.</b> Veja docs/guias/dev-b-presenca-certificados.md</p>
 */
public class EmissorCertificados implements ObservadorEncerramento {

    private final ServicoElegibilidade elegibilidade;
    private final Repositorio<Certificado, String> certificados;
    private final FabricaCertificado fabricaParticipante;
    private final FabricaCertificado fabricaPalestrante;
    private final FabricaCertificado fabricaOrganizador;
    private final Clock relogio;

    public EmissorCertificados(ServicoElegibilidade elegibilidade,
                               Repositorio<Certificado, String> certificados,
                               FabricaCertificado fabricaParticipante,
                               FabricaCertificado fabricaPalestrante,
                               FabricaCertificado fabricaOrganizador,
                               Clock relogio) {
        this.elegibilidade = Objects.requireNonNull(elegibilidade);
        this.certificados = Objects.requireNonNull(certificados);
        this.fabricaParticipante = Objects.requireNonNull(fabricaParticipante);
        this.fabricaPalestrante = Objects.requireNonNull(fabricaPalestrante);
        this.fabricaOrganizador = Objects.requireNonNull(fabricaOrganizador);
        this.relogio = Objects.requireNonNull(relogio);
    }

    @Override
    public void eventoEncerrado(Evento evento) {
        // TODO Dev B:
        //  LocalDate hoje = LocalDate.now(relogio);
        //  1. para cada participante em elegibilidade.listarElegiveis(evento):
        //       certificados.salvar(fabricaParticipante.emitir(participante, evento, hoje));
        //  2. evento.getPalestrante().ifPresent(p -> certificados.salvar(fabricaPalestrante.emitir(p, evento, hoje)));
        //  3. certificados.salvar(fabricaOrganizador.emitir(evento.getOrganizador(), evento, hoje));
        throw new UnsupportedOperationException("TODO Dev B: eventoEncerrado");
    }
}
