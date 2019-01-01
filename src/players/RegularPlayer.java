package players;

import exceptions.InvalidMoveException;
import game.Board;

import java.util.Scanner;

public class RegularPlayer extends BasePlayer {

    private static final String INVALID_INPUT_MESSAGE = "You can not enter anything other than a number.\r\nPlease do so now: ";

    public RegularPlayer(char symbol, Board board, Scanner scanner) {
        super(symbol, board, scanner);
    }

    @Override
    public void makeMove() {
        this.validateMove();
        super.board.setMember(super.getMove(), super.symbol);
    }

    private void validateMove() {
        boolean isMoveValid = false;
        while(!isMoveValid) {
            try {
                super.setMove(Integer.parseInt(super.scanner.next()));
                isMoveValid = true;
            } catch (NumberFormatException e) {
                System.out.print(INVALID_INPUT_MESSAGE);
            } catch (InvalidMoveException e) {
                System.out.print(e.getMessage());
            }
        }
    }

}
