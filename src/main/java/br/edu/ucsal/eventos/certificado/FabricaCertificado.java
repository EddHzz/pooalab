package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Participante;

import java.time.LocalDate;
import java.util.Objects;

public abstract class FabricaCertificado {
    private final GeradorCodigoVerificacao geradorCodigo;

    protected FabricaCertificado(GeradorCodigoVerificacao geradorCodigo) {
        this.geradorCodigo = Objects.requireNonNull(geradorCodigo);
    }

    public final Certificado emitir(Participante participante, Evento evento, LocalDate dataEmissao) {
        return criar(geradorCodigo.gerar(), participante, evento, dataEmissao);
    }

    protected abstract Certificado criar(String codigo, Participante participante, Evento evento,
                                         LocalDate dataEmissao);
}
