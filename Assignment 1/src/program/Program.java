package program;

import game.GameBoard;
import gui.Gui;

import javax.swing.*;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Program {
    public static void main(String[] args) {
        GameBoard board = new GameBoard();
        board.setupNewGame();
        Gui gui = new Gui(board);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true){

        }
    }
}
