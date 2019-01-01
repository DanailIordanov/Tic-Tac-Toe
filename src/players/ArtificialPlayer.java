package players;

import exceptions.InvalidMoveException;
import game.Board;

import java.util.Random;
import java.util.Scanner;

public class ArtificialPlayer extends BasePlayer {

    public ArtificialPlayer(char symbol, Board board, Scanner scanner) {
        super(symbol, board, scanner);
    }

    @Override
    public void makeMove() {
        this.choosePlaceOnBoard();
        System.out.println(super.getMove());
        super.board.setMember(super.getMove(), super.symbol);
    }

    private void choosePlaceOnBoard() {
        var random = new Random();

        var isRandomValid = false;
        while(!isRandomValid) {
            try {
                super.setMove(random.nextInt(9));
                isRandomValid = true;
            } catch (InvalidMoveException e) {
                System.out.print(e.getMessage());
            }
        }
    }

}
