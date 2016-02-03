package gui;

import game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Gui extends JFrame {

    private JPanel cards;
    private GameView gameView;

    public Gui(Game game){

        JPanel setup = createPanel(new SetupGameView(this, game));
        gameView = new GameView(game);

        cards = new JPanel(new CardLayout());
        cards.add(setup);
        cards.add(gameView);

        setVisible(true);
        add(cards);

        setTitle("Othello");
        setSize(950, 1000);
        setLocationRelativeTo(null);
    }

    private JPanel createPanel(JPanel panel) {

        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(8, 8, 5, 5));
        panel.setVisible(true);

        return panel;
    }

    public void switchToGameView(){
        CardLayout layout = (CardLayout)(cards.getLayout());
        layout.last(cards);
        gameView.drawBoard();
    }
}
