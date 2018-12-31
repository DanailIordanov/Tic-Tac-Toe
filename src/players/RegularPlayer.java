package players;

import game.Board;

import java.util.Scanner;

public class RegularPlayer extends BasePlayer {

    private static final String INVALID_INPUT_MESSAGE = "You can not enter anything other than a number. Please do so now:";
    private static final String OUT_OF_BOARD_MESSAGE = "You have to choose an index, which is part of the game board! Please try again:";
    private static final String TAKEN_PLACE_MESSAGE = "You can not make your move there, because this place has been chosen already!";

    public RegularPlayer(char symbol, Board board, Scanner scanner) {
        super(symbol, board, scanner);
    }

    @Override
    public void makeMove() {
        this.validateMove();
        super.board.setMember(super.move, super.symbol);
    }

    private void validateMove() throws UnsupportedOperationException, NumberFormatException {
        boolean isMoveValid = false;
        while(!isMoveValid) {
            try {
                super.move = Integer.parseInt(super.scanner.next());

                if (super.move < 0 || super.move > super.board.getLength() - 1) {
                    throw new UnsupportedOperationException(OUT_OF_BOARD_MESSAGE);
                } else if (super.board.getMember(move) != ' ') {
                    throw new UnsupportedOperationException(TAKEN_PLACE_MESSAGE);
                }

                isMoveValid = true;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
