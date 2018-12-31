package game;

import java.util.Arrays;

public class Board {

    private char[] board;
    private boolean displayed;

    public Board() {
        this.board = initializeBoard();
    }

    public int getMember(int index) {
        return this.board[index];
    }

    public void setMember(int index, char symbol) {
        this.board[index] = symbol;
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

    public void clear() {
        Arrays.fill(this.board, ' ');
    }

    private char[] initializeBoard() {
        var board = new char[9];
        Arrays.fill(board, ' ');

        return board;
    }

}
