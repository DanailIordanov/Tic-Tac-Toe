package game;

import players.ArtificialPlayer;
import players.BasePlayer;
import players.RegularPlayer;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {

        var board = new Board();
        var display = new Display(board);

        var scanner = new Scanner(System.in);

        var firstPlayer = new RegularPlayer('X', board, scanner);
        var secondPlayer = chooseSecondPlayer('O', board, scanner);

        var engine = new Engine(board, display, firstPlayer, secondPlayer);

        engine.run();

        scanner.close();
    }

    private static BasePlayer chooseSecondPlayer(char symbol, Board board, Scanner scanner) {

        System.out.println("Please enter 1 if you want to play with another person,\nor 2 if you want to play with an AI player.");
        while (true) {
            var gameType = scanner.nextInt();

            if (gameType == 1) {
                return new RegularPlayer(symbol, board, scanner);
            } else if (gameType == 2) {
                return new ArtificialPlayer(symbol, board, scanner);
            }

            System.out.println("This is an invalid game type, please enter 1 to play\nwith another person or 2 to play with an AI player!");
        }

    }

}
