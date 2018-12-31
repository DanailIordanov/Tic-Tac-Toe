import java.util.Scanner;

public class GameEngine {

    private static final String CURRENT_STATUS = "Current status: %d : %d\n";
    private static final String NEXT_PLAYER = "Now it is Player %s's turn\n";
    private static final String CONGRATULATIONS = "Congratulations to Player %s!\n";
    private static final String GAME_OVER = "Game over!\n";

    private Board board;
    private Display display;
    private Player playerX;
    private Player playerO;
    private char lastPlayerToWin;
    private int round;
    private boolean somePlayerHasWon;

    public GameEngine(Scanner scanner) {
        this.board = new Board(scanner);
        this.display = new Display(this.board);
        this.playerX = new Player('X');
        this.playerO = new Player('O');
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

            int move = this.board.validateMove();

            this.board.makeMove(currentPlayer.getSymbol(), move);

            this.endTurn(currentPlayer);
        }

    }

    private Player choosePlayer(int counter, char lastPlayerToWin) {
        if (lastPlayerToWin == 'O' && this.round < 2) {
            return counter % 2 == 0 ? this.playerO : this.playerX;
        } else {
            return counter % 2 == 0 ? this.playerX : this.playerO;
        }
    }

    private void endTurn(Player currentPlayer) {
        if (this.board.check(currentPlayer.getSymbol())) {
            lastPlayerToWin = currentPlayer.getSymbol();

            if(currentPlayer.getSymbol() == 'X') {
                this.playerX.incrementWinsCount();
            } else if (currentPlayer.getSymbol() == 'O') {
                this.playerO.incrementWinsCount();
            }

            this.endRound(currentPlayer);

            if(this.round < 3 && !this.somePlayerHasWon) {
                this.beginRound();
            }
        } else if (this.board.isFull()) {
            this.endRound(currentPlayer);

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

    private void endRound(Player currentPlayer) {
        this.display.showBoard();

        if (this.round < 3 && Math.abs(this.playerX.getWinsCount() - this.playerO.getWinsCount()) < 2) {
            if (this.board.isFull()) {
                System.out.printf("Sorry, but the game has been a cat game - there are no winners... :(\n" + NEXT_PLAYER,
                        currentPlayer.getSymbol() == 'X' ? 'O' : 'X');
            } else {
                System.out.printf(
                        CONGRATULATIONS + "They have won this round!\n" + NEXT_PLAYER,
                        currentPlayer.getSymbol(),
                        currentPlayer.getSymbol() == 'X' ? 'O' : 'X');
            }
            this.board.clearBoard();
        } else {
            this.somePlayerHasWon = true;

            if (this.playerX.getWinsCount() == this.playerO.getWinsCount()) {
                System.out.printf(GAME_OVER + "Sorry, but there are no winners in this match... :(\n" + CURRENT_STATUS,
                        this.playerX.getWinsCount(),
                        this.playerO.getWinsCount());
            } else {
                System.out.printf(GAME_OVER + CONGRATULATIONS + "They have won the match!\n" + CURRENT_STATUS,
                        currentPlayer.getSymbol(),
                        this.playerX.getWinsCount(),
                        this.playerO.getWinsCount());
            }
        }
    }

}
