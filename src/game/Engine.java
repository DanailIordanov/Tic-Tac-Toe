package game;

import players.ArtificialPlayer;
import players.BasePlayer;
import players.RegularPlayer;

import java.util.Scanner;

public class Engine {

    private static final String CURRENT_STATUS = "Current status: %d : %d\r\n";
    private static final String NEXT_PLAYER = "Now it is Player %s's turn\r\n";
    private static final String CONGRATULATIONS = "Congratulations to Player %s!\r\n";
    private static final String GAME_OVER = "Game over!\r\n";

    private Board board;
    private Display display;
    private Scanner scanner;
    private BasePlayer playerX;
    private BasePlayer playerO;
    private char lastPlayerToWin;
    private int round;
    private boolean somePlayerHasWon;

    public Engine(Scanner scanner) {
        this.board = new Board();
        this.display = new Display(this.board);
        this.scanner = scanner;
        this.playerX = new RegularPlayer('X', this.board, scanner);
    }


    public void run() {
        var counter = 0;

        this.chooseGameType();

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

    private void chooseGameType() {
        System.out.println("Please enter 1 if you want to play with another person,\r\nor 2 if you want to play with an AI player.");
        var inputIsValid = false;
        while (!inputIsValid) {
            var gameType = this.scanner.nextInt();
            if (gameType == 1) {
                this.playerO = new RegularPlayer('O', this.board, this.scanner);
                inputIsValid = true;
            } else if(gameType == 2) {
                this.playerO = new ArtificialPlayer('O', this.board, this.scanner);
                inputIsValid = true;
            } else {
                System.out.println("This is an invalid game type, please enter 1 to play\r\nwith another person or 2 to play with an AI player!");
            }
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
            System.out.printf("Begin round %d!\r\n", this.round);
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
                System.out.printf("Sorry, but the game has been a cat game - there are no winners... :(\r\n" + NEXT_PLAYER,
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
                System.out.printf(GAME_OVER + "Sorry, but there are no winners in this match... :(\r\n" + CURRENT_STATUS,
                        this.playerX.getWinsCount(),
                        this.playerO.getWinsCount());
            } else {
                System.out.printf(GAME_OVER + CONGRATULATIONS + "They have won the match!\r\n" + CURRENT_STATUS,
                        currentBasePlayer.getSymbol(),
                        this.playerX.getWinsCount(),
                        this.playerO.getWinsCount());
            }
        }
    }

}
