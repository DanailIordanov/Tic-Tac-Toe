public class Player {

    private int winsCount;
    private char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public void incrementWinsCount() {
        this.winsCount++;
    }

    public char getSymbol() {
        return this.symbol;
    }

}
