/**
 * Created by Johanna on 2016-02-08.
 */
public class TestSetup {

    public static OthelloGame NoMove(){
        OthelloGame board = new OthelloGame();
        board.setPlaceForTest(3, 3, OthelloGame.BLACK);
        board.setPlaceForTest(4, 4, OthelloGame.BLACK);
        return board;
    }

    public static OthelloGame NoBlackMove(){
        OthelloGame board = new OthelloGame();
        board.setPlaceForTest(3, 3, OthelloGame.WHITE);
        board.setPlaceForTest(3, 2, OthelloGame.BLACK);
        board.setPlaceForTest(4, 2, OthelloGame.BLACK);
        board.setPlaceForTest(4, 3, OthelloGame.BLACK);
        board.setPlaceForTest(4, 4, OthelloGame.BLACK);
        board.setPlaceForTest(3, 4, OthelloGame.BLACK);
        board.setPlaceForTest(2, 4, OthelloGame.BLACK);
        board.setPlaceForTest(2, 3, OthelloGame.BLACK);
        board.setPlaceForTest(2,2, OthelloGame.BLACK);

        return board;
    }
}
