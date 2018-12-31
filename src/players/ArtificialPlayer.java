package players;

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
        System.out.println(super.move);
        super.board.setMember(super.move, super.symbol);
    }

    private void choosePlaceOnBoard() {
        var random = new Random();

        var isRandomValid = false;
        while(!isRandomValid) {
            var place = random.nextInt(9);
            if (super.board.getMember(place) == ' ') {
                isRandomValid = true;
                super.move = place;
            }
        }
    }

}
