package br.edu.ucsal.eventos.certificado;

import br.edu.ucsal.eventos.dominio.Certificado;
import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ServicoVerificacao {
    private final Repositorio<Certificado, String> certificados;

    public ServicoVerificacao(Repositorio<Certificado, String> certificados) {
        this.certificados = Objects.requireNonNull(certificados);
    }

    public Optional<Certificado> verificar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return Optional.empty();
        }

        return certificados.buscarPorId(codigo.trim().toUpperCase());
    }

    public List<Certificado> emitidosPara(Evento evento) {
        return certificados.buscar(c -> c.getEvento().equals(evento));
    }
}
