import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {

    private static final String OUT_OF_BOARD_MESSAGE = "You have to choose an index, which is part of the game board! Please try again:";
    private static final String TAKEN_PLACE_MESSAGE = "You can not make your move there, because the other player has chosen this place already!";
    private static final String INVALID_INPUT_MESSAGE = "You can not enter anything other than a number. Please do so now:";
    private static final String CURRENT_STATUS = "Current status: %d : %d\n";
    private static final String NEXT_PLAYER = "Now it is Player %s's turn\n";
    private static final String CONGRATULATIONS = "Congratulations to Player %s!\n";
    private static final String GAME_OVER = "Game over!\n";

    private Scanner scanner;
    private char[] board;
    private boolean boardIsDisplayed;
    private int round;
    private int xWinsCount;
    private int oWinsCount;
    private char lastPlayerToWin;
    private boolean somePlayerHasWon;

    public GameEngine(Scanner scanner) {
        this.scanner = scanner;

        this.board = initializeBoard();
    }


    public void run() {
        var counter = 0;

        this.beginRound();

        while (this.round <= 3 && !this.somePlayerHasWon) {
            if (!this.boardIsDisplayed) {
                this.displayBoard();
            } else {
                this.boardIsDisplayed = false;
            }

            var currentPlayer = this.choosePlayer(counter++, lastPlayerToWin);

            int move = this.validateMove();

            this.makeAMove(currentPlayer, move);

            this.endTurn(currentPlayer);
        }

    }

    private char[] initializeBoard() {
        var board = new char[9];
        for (var i = 0; i < board.length; i++) {
            board[i] = ' ';
        }

        return board;
    }

    private void displayBoard() {
        System.out.println("-------------");
        for	(var i = 1; i <= this.board.length; i++) {

            System.out.printf("| %s ", this.board[i - 1]);

            if(i % 3 == 0) {
                System.out.println("|");
                System.out.println("-------------");
            }
        }
    }

    private char choosePlayer(int counter, char lastPlayerToWin) {
        if (lastPlayerToWin == 'O' && this.round < 2) {
            return counter % 2 == 0 ? 'O' : 'X';
        } else {
            return counter % 2 == 0 ? 'X' : 'O';
        }
    }

    private int validateMove() throws UnsupportedOperationException {
        int move = 0;

        boolean isMoveValid = false;
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

    private void makeAMove(char player, int move) {
        this.board[move] = player;
    }

    private void endTurn(char currentPlayer) {
        if (this.determineGameState(currentPlayer)) {
            lastPlayerToWin = currentPlayer;

            this.xWinsCount = currentPlayer == 'X' ? this.xWinsCount + 1 : this.xWinsCount;
            this.oWinsCount = currentPlayer == 'O' ? this.oWinsCount + 1 : this.oWinsCount;

            this.endRound(currentPlayer);

            if(this.round < 3 && !this.somePlayerHasWon) {
                this.beginRound();
            }
        } else if (String.valueOf(this.board).indexOf(' ') < 0) {
            this.endRound(currentPlayer);

            if(this.round < 3 && !this.somePlayerHasWon) {
                this.beginRound();
            }
        }
    }

    private Boolean determineGameState(char symbol) {
        return this.board[0] == this.board[1] && this.board[1] == this.board[2]	&& this.board[2] == symbol// checking the rows
                || this.board[3] == this.board[4] && this.board[4] == this.board[5] && this.board[5] == symbol
                || this.board[6] == this.board[7] && this.board[7] == this.board[8] && this.board[8] == symbol
                || this.board[0] == this.board[3] && this.board[3] == this.board[6] && this.board[6] == symbol // checking the columns
                || this.board[1] == this.board[4] && this.board[4] == this.board[7] && this.board[7] == symbol
                || this.board[2] == this.board[5] && this.board[5] == this.board[8] && this.board[8] == symbol
                || this.board[0] == this.board[4] && this.board[4] == this.board[8] && this.board[8] == symbol// checking both diagonals
                || this.board[2] == this.board[4] && this.board[4] == this.board[6] && this.board[6] == symbol;
    }

    private void beginRound() {
        this.round++;

        System.out.printf(CURRENT_STATUS, this.xWinsCount, this.oWinsCount);

        if (this.round < 3) {
            System.out.printf("Begin round %d!\n", this.round);
        } else {
            System.out.println("Final round!");
        }

        this.displayBoard();
        this.boardIsDisplayed = true;
    }

    private void endRound(char currentPlayer) {
        this.displayBoard();

        if (this.round < 3 && Math.abs(this.xWinsCount - this.oWinsCount) < 2) {
            if (String.valueOf(this.board).indexOf(' ') < 0) {
                System.out.printf("Sorry, but the game has been a cat game - there are no winners... :(\n" + NEXT_PLAYER,
                        currentPlayer == 'X' ? 'O' : 'X');
            } else {
                System.out.printf(
                        CONGRATULATIONS + "They have won this round!\n" + NEXT_PLAYER,
                        currentPlayer,
                        currentPlayer == 'X' ? 'O' : 'X');
            }
            Arrays.fill(this.board, ' ');
        } else {
            this.somePlayerHasWon = true;

            if (this.xWinsCount == this.oWinsCount) {
                System.out.printf(GAME_OVER + "Sorry, but there are no winners in this match... :(\n" + CURRENT_STATUS,
                        this.xWinsCount,
                        this.oWinsCount);
            } else {
                System.out.printf(GAME_OVER + CONGRATULATIONS + "They have won the match!\n" + CURRENT_STATUS,
                        currentPlayer,
                        this.xWinsCount,
                        this.oWinsCount);
            }
        }
    }

}
