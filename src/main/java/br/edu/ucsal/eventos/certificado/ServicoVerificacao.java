package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Qualquer pessoa confere um certificado pelo código de verificação.
 * <p><b>Responsável: Dev B.</b></p>
 */
public class ServicoVerificacao {

    private final Repositorio<Certificado, String> certificados;

    public ServicoVerificacao(Repositorio<Certificado, String> certificados) {
        this.certificados = Objects.requireNonNull(certificados);
    }

    /**
     * Devolve o certificado se o código existir. Deve ignorar espaços e
     * maiúsculas/minúsculas ("cert-ab12-cd34 " encontra "CERT-AB12-CD34").
     */
    public Optional<Certificado> verificar(String codigo) {
        // TODO Dev B: se codigo for nulo/vazio -> Optional.empty();
        //               senão certificados.buscarPorId(codigo.trim().toUpperCase())
        throw new UnsupportedOperationException("TODO Dev B: verificar");
    }

    /** Certificados emitidos para um evento (útil no menu). */
    public List<Certificado> emitidosPara(Evento evento) {
        return certificados.buscar(c -> c.getEvento().equals(evento));
    }
}
