package gui;

import game.GameBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Gui extends JFrame {

    private JPanel cards;
    private GameBoard board;

    public Gui(GameBoard board){
        this.board = board;


        JPanel setup = createPanel(new SetupGameView(this));
        JPanel game = createPanel(new GameBoardView(board));

        cards = new JPanel(new CardLayout());
        cards.add(setup);
        cards.add(game);

        setVisible(true);
        add(cards);

        setTitle("Othello");
        setSize(850, 850);
        setLocationRelativeTo(null);
    }

    private JPanel createPanel(JPanel panel) {

        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(8, 8, 5, 5));
        panel.setVisible(true);

        return panel;
    }

    public void startGame(){
        CardLayout layout = (CardLayout)(cards.getLayout());
        layout.last(cards);
    }
}
