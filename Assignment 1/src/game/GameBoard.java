package game;

/**
 * Created by Johanna on 2016-02-03.
 */
public class GameBoard {
    private Disk[][] board;
    int gameSize = 8;

    public GameBoard(){
        board = new Disk[gameSize][gameSize];
    }

    public void setupNewGame(){
        placeDisk(3,3, Disk.Color.White);
        placeDisk(4,4, Disk.Color.White);
        placeDisk(3,4, Disk.Color.Black);
        placeDisk(4,3, Disk.Color.Black);
    }

    public int getGameSize(){
        return gameSize;
    }

    public boolean isEmpty(int x, int y){
        return board[x][y] == null;
    }

    public Disk.Color getColor(int x, int y) {
        return board[x][y].getColor();
    }

    public void placeDisk(int x, int y, Disk.Color color){
        board[x][y] = new Disk(color);
    }

    public boolean isColor(int x, int y, Disk.Color color) {
        return board[x][y] != null && board[x][y].getColor() == color;
    }
}
