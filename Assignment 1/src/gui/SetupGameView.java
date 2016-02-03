package gui;

import game.Disk;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Johanna on 2016-02-03.
 */
public class SetupGameView extends JPanel implements ActionListener {

    private Gui gui;
    private Game game;

    public SetupGameView(Gui gui, Game game){
        this.gui = gui;
        this.game = game;
        Label greeting = new Label("Välkommen till Johannas Othello spel");
        this.add(greeting);

        Label chooseColor = new Label("Välj vilken färg du vill spela som");
        this.add(chooseColor);
        JRadioButton whiteBtn = new JRadioButton("Vit");

        JRadioButton blackBtn = new JRadioButton("Svart");
        blackBtn.setSelected(true);
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(whiteBtn);
        colorGroup.add(blackBtn);
        this.add(whiteBtn);
        this.add(blackBtn);

        Label chooseTimeForComputer = new Label("Hur snabbt måste datorn svara i hela sekunder?");
        TextField timeFiled = new TextField();
        timeFiled.setText("1");

        this.add(chooseTimeForComputer);
        this.add(timeFiled);

        JButton start = new JButton("Starta spelet");
        start.addActionListener(this);
        this.add(start);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.startGame(Disk.Color.Black, 1); //TODO: Take user selected values
        gui.switchToGameView();
    }
}
