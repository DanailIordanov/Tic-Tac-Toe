package players;

import exceptions.InvalidMoveException;
import game.Board;

import java.util.Scanner;

public abstract class BasePlayer {

    private static final String OUT_OF_BOARD_MESSAGE = "You have to choose an index, which is part of the game board!\r\nPlease try again: ";
    private static final String TAKEN_PLACE_MESSAGE = "You can not make your move there, because this place has been\r\nchosen already! Please try again: ";

    protected char symbol;
    protected Board board;
    protected Scanner scanner;
    private int move;
    private int winsCount;

    protected BasePlayer(char symbol, Board board, Scanner scanner) {
        this.symbol = symbol;
        this.board = board;
        this.scanner = scanner;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public void incrementWinsCount() {
        this.winsCount++;
    }

    protected int getMove() {
        return move;
    }

    protected void setMove(int move) throws InvalidMoveException {
        if (move < 0 || move > this.board.getLength() - 1) {
            throw new InvalidMoveException(OUT_OF_BOARD_MESSAGE);
        } else if (this.board.getMember(move) != ' ') {
            throw new InvalidMoveException(TAKEN_PLACE_MESSAGE);
        }
        this.move = move;
    }

    public abstract void makeMove();

}
