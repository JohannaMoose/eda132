package game;

import logging.Log;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johanna on 2016-02-03.
 */
public class OthelloGameMove {

    private final int x;
    private final int y;
    private final Disk.Color color;
    private final GameBoard board;

    public OthelloGameMove(int x, int y, Disk.Color color, GameBoard board){

        this.x = x;
        this.y = y;
        this.color = color;
        this.board = board;
    }

    public boolean isValidMove(){
        if(!board.isEmpty(x, y))
            return false;
        if(!piecesToFlipRight().isEmpty()|| !piecesToFlipDownRight().isEmpty() || !piecesToFlipDownwards().isEmpty() || !piecesToFlipDownLeft().isEmpty() ||
                !piecesToFlipLeft().isEmpty() || !piecesToFlipUpLeft().isEmpty()|| !piecesToFlipUpwards().isEmpty() || !piecesToFlipUpRight().isEmpty())
            return true;
        return false;
    }

    private List<Pair<Integer, Integer>> piecesToFlipRight(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        boolean foundOwnPiece = false;

        for (int i = x + 1; i < board.getGameSize(); i++){
            if(board.isEmpty(i, y))
                break;
            else if(board.isColor(i, y, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(i, y, color))
                break;
            else if(!board.isColor(i, y, color))
                list.add(new Pair<>(i, y));
        }

        if(!foundOwnPiece)
            list.clear();

        Log.Information("Could flip %d to the right", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipDownRight(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        int i = x +1;
        int j = y + 1;
        while (i < board.getGameSize() && j < board.getGameSize()){
            if(board.isEmpty(i, j))
                break;
            else if(board.isColor(i, j, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(i, j, color))
                break;
            else if(!board.isColor(i, j, color))
                list.add(new Pair<>(i, j));

            i++;
            j++;
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d to the down right", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipDownwards(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        for (int i = y + 1; i < board.getGameSize(); i++){
            if(board.isEmpty(x, i))
                break;
            else if(board.isColor(x, i, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(x, i, color))
                break;
            else if(!board.isColor(x, i, color))
                list.add(new Pair<>(x, i));
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d downwards", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipDownLeft(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        int i = x - 1;
        int j = y + 1;
        while (i >= 0 && j < board.getGameSize()){
            if(board.isEmpty(i, j))
                break;
            else if(board.isColor(i, j, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(i, j, color))
                break;
            else if(!board.isColor(i, j, color))
                list.add(new Pair<>(i, j));

            i--;
            j++;
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d to the down left", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipLeft(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        for (int i = x - 1; i >= 0; i--){
            if(board.isEmpty(i, y))
                break;
            else if(board.isColor(i, y, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(i, y, color))
                break;
            else if(!board.isColor(i, y, color))
                list.add(new Pair<>(i, y));
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d to the left", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipUpLeft(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        int i = x - 1;
        int j = y - 1;
        while (i >= 0 && j >= 0){
            if(board.isEmpty(i, j))
                break;
            else if(board.isColor(i, j, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(i, j, color))
                break;
            else if(!board.isColor(i, j, color))
                list.add(new Pair<>(i, j));

            i--;
            j--;
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d to the up left", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipUpwards(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        for (int i = y-1; i >= 0; i--){
            if(board.isEmpty(x, i))
                break;
            else if(board.isColor(x, i, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(x, i, color))
                break;
            else if(!board.isColor(x, i, color))
                list.add(new Pair<>(x, i));
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d upwards", list.size());
        return list;
    }

    private List<Pair<Integer, Integer>> piecesToFlipUpRight(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        boolean foundOwnPiece = false;

        int i = x + 1;
        int j = y - 1;
        while (i < board.getGameSize() && j >= 0){
            if(board.isEmpty(i, j))
                break;
            else if(board.isColor(i, j, color) && !list.isEmpty())
            {
                foundOwnPiece = true;
                break;
            }
            else if(board.isColor(i, j, color))
                break;
            else if(!board.isColor(i, j, color))
                list.add(new Pair<>(i, j));

            i++;
            j--;
        }

        if(!foundOwnPiece)
             list.clear();

        Log.Information("Could flip %d to the up right", list.size());
        return list;
    }

    public List<Pair<Integer, Integer>> getCoordinatesToFlip(){
        List<Pair<Integer, Integer>> list = new ArrayList<>();

       list.addAll(piecesToFlipUpwards());
        list.addAll(piecesToFlipUpRight());
        list.addAll(piecesToFlipRight());
        list.addAll(piecesToFlipDownRight());
        list.addAll(piecesToFlipDownwards());
        list.addAll(piecesToFlipDownLeft());
        list.addAll(piecesToFlipLeft());
        list.addAll(piecesToFlipUpLeft());

        return list;

    }
}
