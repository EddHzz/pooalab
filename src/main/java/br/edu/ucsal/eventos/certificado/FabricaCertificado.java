package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Padrão <b>Factory Method</b>.
 *
 * <p>{@link #emitir} é igual para todos os tipos: gera o código e cria o
 * certificado. A única parte que muda é QUAL subclasse de Certificado é criada,
 * e isso fica no método abstrato {@link #criar} (o "factory method"). Cada
 * fábrica concreta (participante, palestrante, organizador) implementa só ele.</p>
 *
 * <p>Fábricas que a Dev B deve criar neste pacote:
 * FabricaCertificadoParticipante, FabricaCertificadoPalestrante e
 * FabricaCertificadoOrganizador, cada uma criando a sua subclasse
 * (CertificadoParticipante, CertificadoPalestrante, CertificadoOrganizador).</p>
 */
public abstract class FabricaCertificado {

    private final GeradorCodigoVerificacao geradorCodigo;

    protected FabricaCertificado(GeradorCodigoVerificacao geradorCodigo) {
        this.geradorCodigo = Objects.requireNonNull(geradorCodigo);
    }

    /** Passo fixo: gerar código e delegar a criação para a subclasse. */
    public final Certificado emitir(Participante participante, Evento evento, LocalDate dataEmissao) {
        // TODO Dev B: return criar(geradorCodigo.gerar(), participante, evento, dataEmissao);
        throw new UnsupportedOperationException("TODO Dev B: emitir");
    }

    /** O factory method: cada fábrica concreta devolve o seu tipo de certificado. */
    protected abstract Certificado criar(String codigo, Participante participante, Evento evento,
                                         LocalDate dataEmissao);
}
