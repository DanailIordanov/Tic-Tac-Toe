package game;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var game = new Engine(scanner);

        game.run();

        scanner.close();
    }

}
