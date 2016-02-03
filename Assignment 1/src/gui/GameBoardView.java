package gui;

import game.Disk;
import game.GameBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Johanna on 2016-02-03.
 */
public class GameBoardView extends JPanel implements ActionListener {

    JButton[][] pieceButtons;

    private Image white, black;
    GameBoard board;

    public GameBoardView(GameBoard board){
        this.board = board;

        try {
            white = ImageIO.read(getClass().getResource("../resources/white.jpg"));
            black = ImageIO.read(getClass().getResource("../resources/black.jpg"));
        } catch (IOException ex) {
        }

        createButtonGrid();
        drawBoard();
    }

    private void createButtonGrid() {
        pieceButtons = new JButton[8][8];

        for (int i = 0; i < this.board.getGameSize(); i++) {
            for (int j = 0; j < this.board.getGameSize(); j++) {
                JButton btn = new JButton();
                btn.setBackground(Color.BLACK);
                pieceButtons[i][j] = btn;
                this.add(btn);
                pieceButtons[i][j].addActionListener(this);
            }

        }
    }

    public void drawBoard(){
        for (int i = 0; i < this.board.getGameSize(); i++) {
            for (int j = 0; j < this.board.getGameSize(); j++) {
                if (board.isColor(i, j, Disk.Color.White))
                    pieceButtons[i][j].setIcon(new ImageIcon(white));
                else if(board.isColor(i, j, Disk.Color.Black))
                    pieceButtons[i][j].setIcon(new ImageIcon(black));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < pieceButtons.length; i++) {
            for (int j = 0; j < pieceButtons[i].length; j++) {
                if (pieceButtons[i][j] == e.getSource()) {
                    board.placeDisk(i, j, Disk.Color.Black);
                }
            }
        }
        drawBoard();
    }
}
