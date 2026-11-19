package br.edu.ucsal.eventos.inscricao;

import br.edu.ucsal.eventos.dominio.Evento;
import br.edu.ucsal.eventos.dominio.Inscricao;
import br.edu.ucsal.eventos.dominio.Participante;
import br.edu.ucsal.eventos.repositorio.Repositorio;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Caso de uso do participante: se inscrever em um evento.
 *
 * <p>O serviço não sabe quais validações existem: ele recebe o primeiro elo da
 * cadeia pronto pelo construtor e só chama {@code validar}. O {@link Clock}
 * também vem de fora, para os testes poderem fixar o "dia de hoje".</p>
 *
 * <p><b>Responsável: Dev A.</b> Veja docs/guias/dev-a-eventos-inscricao.md</p>
 */
public class ServicoInscricao {

    private final Repositorio<Inscricao, String> inscricoes;
    private final ValidadorInscricao cadeiaDeValidacao;
    private final Clock relogio;

    public ServicoInscricao(Repositorio<Inscricao, String> inscricoes,
                            ValidadorInscricao cadeiaDeValidacao,
                            Clock relogio) {
        this.inscricoes = Objects.requireNonNull(inscricoes);
        this.cadeiaDeValidacao = Objects.requireNonNull(cadeiaDeValidacao);
        this.relogio = Objects.requireNonNull(relogio);
    }

    /**
     * Inscreve o participante no evento, ou lança RegraNegocioException com o motivo da recusa.
     */
    public Inscricao inscrever(Participante participante, Evento evento) {
        LocalDate hoje = LocalDate.now(relogio);               // "hoje" vem do Clock (nos testes é fixo)
        cadeiaDeValidacao.validar(participante, evento, hoje); // passa pelos 3 elos; se algum recusar, para aqui
        Inscricao inscricao = new Inscricao(participante, evento, hoje);
        return inscricoes.salvar(inscricao);                   // só chega aqui se ninguém recusou
    }
}