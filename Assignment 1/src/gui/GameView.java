package gui;

import game.Disk;
import game.Game;
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
public class GameView extends JPanel implements ActionListener {

    JPanel gameGrid;
    JButton[][] pieceButtons;
    Label statusLabel;
    private Image white, black;
    GameBoard board;
    private Game game;


    public GameView(Game game){
        this.game = game;
        this.board = game.getBoard();

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        gameGrid = new JPanel();
        gameGrid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gameGrid.setLayout(new GridLayout(8, 8, 5, 5));
        gameGrid.setVisible(true);
        this.add(gameGrid);

        statusLabel = new Label("Det är din tur, välj var du ska lägga din bricka");
        statusLabel.setAlignment(Label.CENTER);
        this.add(statusLabel);

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
                gameGrid.add(btn);
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
        if(!game.isPlayersTurn())
            return;
        for (int i = 0; i < pieceButtons.length; i++) {
            for (int j = 0; j < pieceButtons[i].length; j++) {
                if (pieceButtons[i][j] == e.getSource()) {
                    boolean wasLegalMove = game.tryPlaceDisk(i, j, Disk.Color.Black);
                    if(!wasLegalMove)
                        statusLabel.setText("Det är inte ett gilltigt drag, välj ett gilltigt drag");
                    else
                        statusLabel.setText("Det är datorns tur nu, den tänker");
                }
            }
        }
        drawBoard();
    }
}
