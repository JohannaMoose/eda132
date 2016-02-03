package game;

import util.Pair;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Game {

    int timeLimit;
    boolean isPlayersTurn;
    GameBoard board;
    private GameAgent agent;

    public Game(GameAgent agent){
        this.agent = agent;
        board = new GameBoard();
    }

    public void startGame(Disk.Color playerColor, int timeOut){

        timeLimit = timeOut;
        if(playerColor == Disk.Color.Black)
            agent.setColor(Disk.Color.White);
        else
            agent.setColor(Disk.Color.Black);

        isPlayersTurn = playerColor == Disk.Color.Black; // Black starts the game

        board.setupNewGame();
    }

    public GameBoard getBoard(){
        return board;
    }

    public boolean tryPlaceDisk(int x, int y, Disk.Color color){
        OthelloGameMove move = new OthelloGameMove(x, y, color, board);
        if(!move.isValidMove())
            return false;


        board.placeDisk(x, y, color);
        for (Pair<Integer, Integer> coordinates: move.getCoordinatesToFlip()) {
            board.flipDisk(coordinates.getLeft(), coordinates.getRight());
        }

        isPlayersTurn = !isPlayersTurn;
        return true;
    }

    public boolean isPlayersTurn(){
        return isPlayersTurn;
    }
}
