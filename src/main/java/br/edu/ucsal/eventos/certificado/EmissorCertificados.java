package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.evento.ObservadorEncerramento;
import br.edu.ucsal.eventos.presenca.ServicoElegibilidade;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

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
        LocalDate hoje = LocalDate.now(relogio);

        for (Participante participante : elegibilidade.listarElegiveis(evento)) {
            certificados.salvar(fabricaParticipante.emitir(participante, evento, hoje));
        }

        evento.getPalestrante()
                .ifPresent(p -> certificados.salvar(fabricaPalestrante.emitir(p, evento, hoje)));

        certificados.salvar(fabricaOrganizador.emitir(evento.getOrganizador(), evento, hoje));
    }
}
