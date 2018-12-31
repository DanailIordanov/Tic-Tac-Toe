import java.util.Arrays;
import java.util.Scanner;

public class Board {

    private static final String OUT_OF_BOARD_MESSAGE = "You have to choose an index, which is part of the game board! Please try again:";
    private static final String TAKEN_PLACE_MESSAGE = "You can not make your move there, because the other player has chosen this place already!";
    private static final String INVALID_INPUT_MESSAGE = "You can not enter anything other than a number. Please do so now:";

    private Scanner scanner;
    private char[] board;
    private boolean displayed;

    public Board(Scanner scanner) {
        this.scanner = scanner;
        this.board = initializeBoard();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean isDisplayed) {
        this.displayed = isDisplayed;
    }

    public boolean isFull() {
        return String.valueOf(this.board).indexOf(' ') < 0;
    }

    public int getLength() {
        return this.board.length;
    }

    public int getMember(int index) {
        return this.board[index];
    }

    public int validateMove() throws UnsupportedOperationException {
        int move = 0;

        Boolean isMoveValid = false;
        while(isMoveValid == false) {
            try {
                move = Integer.parseInt(this.scanner.nextLine());

                if (move < 0 || move > this.board.length - 1) {
                    throw new UnsupportedOperationException(OUT_OF_BOARD_MESSAGE);
                } else if (this.board[move] != ' ') {
                    throw new UnsupportedOperationException(TAKEN_PLACE_MESSAGE);
                }

                isMoveValid = true;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        return move;
    }

    public void makeMove(char player, int move) {
        this.board[move] = player;
    }

    public Boolean check(char symbol) {
        return this.board[0] == this.board[1] && this.board[1] == this.board[2]	&& this.board[2] == symbol// checking the rows
                || this.board[3] == this.board[4] && this.board[4] == this.board[5] && this.board[5] == symbol
                || this.board[6] == this.board[7] && this.board[7] == this.board[8] && this.board[8] == symbol
                || this.board[0] == this.board[3] && this.board[3] == this.board[6] && this.board[6] == symbol // checking the columns
                || this.board[1] == this.board[4] && this.board[4] == this.board[7] && this.board[7] == symbol
                || this.board[2] == this.board[5] && this.board[5] == this.board[8] && this.board[8] == symbol
                || this.board[0] == this.board[4] && this.board[4] == this.board[8] && this.board[8] == symbol// checking both diagonals
                || this.board[2] == this.board[4] && this.board[4] == this.board[6] && this.board[6] == symbol;
    }

    public void clearBoard() {
        Arrays.fill(this.board, ' ');
    }

    private char[] initializeBoard() {
        var board = new char[9];
        Arrays.fill(board, ' ');

        return board;
    }

}
