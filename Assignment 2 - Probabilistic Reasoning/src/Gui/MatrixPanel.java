package Gui;

import program.Point;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * Created by Johanna on 2016-02-20.
 */
public class MatrixPanel extends JPanel {

    private HashSet<JLabel> previousBot;

    private JLabel[][] board;
    private Color labelColor = new JLabel().getBackground();

    public MatrixPanel(int boardSize)
    {
        super(new GridLayout(boardSize, boardSize));

        board = new JLabel[boardSize][boardSize];
        previousBot = new HashSet<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[j][i] = new JLabel();
                board[j][i].setSize(50, 50);
                board[j][i].setBorder(BorderFactory
                        .createLineBorder(Color.BLACK));
                this.add(board[j][i]);
            }
        }
    }

    public void setBotPosition(Point position, Point botPosition) {
        HashSet<JLabel> toBeRemoved = new HashSet<>();
        for (JLabel label : previousBot) {
            int color = label.getBackground().getGreen();
            if (color <= 50) {
                toBeRemoved.add(label);
            } else {
                label.setBackground(new Color(0, color - 50, 0));
            }
        }
        if (!toBeRemoved.isEmpty()) {
            for (JLabel l : toBeRemoved) {
                l.setBackground(labelColor);
                l.setOpaque(false);
                previousBot.remove(l);
            }
        }
        board[botPosition.x][botPosition.y].setText("");
        board[position.x][position.y].setText("Bot");
        board[position.x][position.y].setOpaque(true);
        board[position.x][position.y].setBackground(new Color(0, 250, 0));
        previousBot.add(board[position.x][position.y]);
    }

    public void setEstimatePos(Point newEstimatePosition, Point currentEstimatedPosition, Point currentRobotPosition, Point currentSensorPosition) {
        if (!currentEstimatedPosition.equals(currentRobotPosition) && !currentEstimatedPosition.equals(currentSensorPosition)) {
            board[currentEstimatedPosition.x][currentEstimatedPosition.y].setText("");
            board[currentEstimatedPosition.x][currentEstimatedPosition.y]
                    .setBackground(labelColor);
            board[currentEstimatedPosition.x][currentEstimatedPosition.y].setOpaque(false);
        }

        if (newEstimatePosition.equals(currentRobotPosition) && newEstimatePosition.equals(currentSensorPosition)) {
            board[newEstimatePosition.x][newEstimatePosition.y].setText("<html>Bot, Est <br>and Sensor</html>");
            board[newEstimatePosition.x][newEstimatePosition.y].setBackground(Color.white);
        } else if (newEstimatePosition.equals(currentRobotPosition)) {
            board[newEstimatePosition.x][newEstimatePosition.y].setText("<html> Bot and <br> Est </html>");
            board[newEstimatePosition.x][newEstimatePosition.y].setOpaque(true);
            board[newEstimatePosition.x][newEstimatePosition.y].setBackground(Color.cyan);
        } else if (newEstimatePosition.equals(currentSensorPosition)) {
            board[newEstimatePosition.x][newEstimatePosition.y].setText("<html> Est and <br> Sensor </html>");
            board[newEstimatePosition.x][newEstimatePosition.y].setOpaque(true);
            board[newEstimatePosition.x][newEstimatePosition.y].setBackground(Color.magenta);
        } else {
            board[newEstimatePosition.x][newEstimatePosition.y].setText("Est");
            board[newEstimatePosition.x][newEstimatePosition.y].setOpaque(true);
            board[newEstimatePosition.x][newEstimatePosition.y].setBackground(Color.blue);
        }
    }

    public void setSensorPosition(Point newSensorPosition, Point currentSensorPosition, Point currentRobotPosition, JLabel message) {
        if (!currentSensorPosition.equals(currentRobotPosition)) {
            board[currentSensorPosition.x][currentSensorPosition.y].setText("");
            board[currentSensorPosition.x][currentSensorPosition.y].setBackground(labelColor);
            board[currentSensorPosition.x][currentSensorPosition.y].setOpaque(false);
        }
        if (newSensorPosition.equals(currentRobotPosition) && newSensorPosition.x >= 0 && newSensorPosition.y >= 0) {
            board[newSensorPosition.x][newSensorPosition.y].setText("<html>Bot and<br>Sensor</html>");
            board[newSensorPosition.x][newSensorPosition.y].setBackground(Color.orange);
        } else if (newSensorPosition.x >= 0 && newSensorPosition.y >= 0) {
            board[newSensorPosition.x][newSensorPosition.y].setText("Sensor");
            board[newSensorPosition.x][newSensorPosition.y].setOpaque(true);
            board[newSensorPosition.x][newSensorPosition.y].setBackground(Color.red);
        } else {
            message.setOpaque(true);
            message.setBackground(Color.red);
        }
    }
}
