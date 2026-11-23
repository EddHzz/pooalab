package br.edu.ucsal.eventos.app;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Scanner;

/**
 * Ponto de entrada: monta o sistema, carrega os dados fictícios e abre o menu.
 */
public class Main {

    public static void main(String[] args) {
        // UTF-8 para os acentos (no Windows, rodar "chcp 65001" antes)
        PrintStream saida = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8);

        Aplicacao app = new Aplicacao(Clock.systemDefaultZone());
        new DadosDemonstracao().carregar(app);
        new MenuConsole(app, entrada, saida).executar();
    }
}
