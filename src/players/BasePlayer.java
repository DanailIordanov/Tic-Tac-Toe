package players;

import game.Board;

import java.util.Scanner;

public abstract class BasePlayer {

    protected char symbol;
    protected Board board;
    protected Scanner scanner;
    protected int move;
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

    public abstract void makeMove();
}
