public class Display {

    private Board board;

    public Display(Board board) {
        this.board = board;
    }

    public void showBoard() {
        System.out.println("-------------");
        for	(var i = 1; i <= this.board.getLength(); i++) {

            System.out.printf("| %s ", (char)this.board.getMember(i - 1));

            if(i % 3 == 0) {
                System.out.println("|");
                System.out.println("-------------");
            }
        }
    }

}
