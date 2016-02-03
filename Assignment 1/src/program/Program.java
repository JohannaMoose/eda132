package program;

import game.Game;
import game.GameBoard;
import gui.Gui;

import javax.swing.*;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Program {
    public static void main(String[] args) {
        Game game = new Game();
        Gui gui = new Gui(game);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true){

        }
    }
}
