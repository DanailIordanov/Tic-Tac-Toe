package game;

import players.ArtificialPlayer;
import players.BasePlayer;
import players.RegularPlayer;

import java.util.Scanner;

public class Engine {

    private static final String CURRENT_STATUS = "Current status: %d : %d\n";
    private static final String NEXT_PLAYER = "Now it is Player %s's turn\n";
    private static final String CONGRATULATIONS = "Congratulations to Player %s!\n";
    private static final String GAME_OVER = "Game over!\n";

    private Board board;
    private Display display;
    private BasePlayer playerX;
    private BasePlayer playerO;
    private char lastPlayerToWin;
    private int round;
    private boolean somePlayerHasWon;

    public Engine(Scanner scanner) {
        this.board = new Board();
        this.display = new Display(this.board);
        this.playerX = new RegularPlayer('X', this.board, scanner);
        this.playerO = new ArtificialPlayer('O', this.board, scanner);
    }


    public void run() {
        var counter = 0;

        this.beginRound();

        while (this.round <= 3 && !this.somePlayerHasWon) {
            if (!this.board.isDisplayed()) {
                this.display.showBoard();
            } else {
                this.board.setDisplayed(false);
            }

            var currentPlayer = this.choosePlayer(counter++, lastPlayerToWin);
            System.out.printf("Player %s: ", currentPlayer.getSymbol());
            currentPlayer.makeMove();

            this.endTurn(currentPlayer);
        }

    }

    private BasePlayer choosePlayer(int counter, char lastPlayerToWin) {
        if (lastPlayerToWin == 'O' && this.round < 2) {
            return counter % 2 == 0 ? this.playerO : this.playerX;
        } else {
            return counter % 2 == 0 ? this.playerX : this.playerO;
        }
    }

    private void endTurn(BasePlayer currentBasePlayer) {
        if (this.board.check(currentBasePlayer.getSymbol())) {
            lastPlayerToWin = currentBasePlayer.getSymbol();

            if(currentBasePlayer.getSymbol() == 'X') {
                this.playerX.incrementWinsCount();
            } else if (currentBasePlayer.getSymbol() == 'O') {
                this.playerO.incrementWinsCount();
            }

            this.endRound(currentBasePlayer);

            if(this.round < 3 && !this.somePlayerHasWon) {
                this.beginRound();
            }
        } else if (this.board.isFull()) {
            this.endRound(currentBasePlayer);

            if(this.round < 3 && !this.somePlayerHasWon) {
                this.beginRound();
            }
        }
    }

    private void beginRound() {
        this.round++;

        System.out.printf(CURRENT_STATUS, this.playerX.getWinsCount(), this.playerO.getWinsCount());

        if (this.round < 3) {
            System.out.printf("Begin round %d!\n", this.round);
        } else {
            System.out.println("Final round!");
        }

        this.display.showBoard();
        this.board.setDisplayed(true);
    }

    private void endRound(BasePlayer currentBasePlayer) {
        this.display.showBoard();

        if (this.round < 3 && Math.abs(this.playerX.getWinsCount() - this.playerO.getWinsCount()) < 2) {
            if (this.board.isFull()) {
                System.out.printf("Sorry, but the game has been a cat game - there are no winners... :(\n" + NEXT_PLAYER,
                        currentBasePlayer.getSymbol() == 'X' ? 'O' : 'X');
            } else {
                System.out.printf(
                        CONGRATULATIONS + "They have won this round!\n" + NEXT_PLAYER,
                        currentBasePlayer.getSymbol(),
                        currentBasePlayer.getSymbol() == 'X' ? 'O' : 'X');
            }
            this.board.clear();
        } else {
            this.somePlayerHasWon = true;

            if (this.playerX.getWinsCount() == this.playerO.getWinsCount()) {
                System.out.printf(GAME_OVER + "Sorry, but there are no winners in this match... :(\n" + CURRENT_STATUS,
                        this.playerX.getWinsCount(),
                        this.playerO.getWinsCount());
            } else {
                System.out.printf(GAME_OVER + CONGRATULATIONS + "They have won the match!\n" + CURRENT_STATUS,
                        currentBasePlayer.getSymbol(),
                        this.playerX.getWinsCount(),
                        this.playerO.getWinsCount());
            }
        }
    }

}
