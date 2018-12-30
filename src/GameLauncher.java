import java.util.Scanner;

public class GameLauncher {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var game = new GameEngine(scanner);

        game.run();

        scanner.close();
    }

}
