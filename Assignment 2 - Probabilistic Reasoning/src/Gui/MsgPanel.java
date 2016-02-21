package Gui;

import program.*;
import program.Point;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Johanna on 2016-02-20.
 */
public class MsgPanel extends JPanel {

    private JLabel message;
    private JLabel estimateLabel;
    private long waitTime;

    public MsgPanel(long waitTime){
        super(new GridLayout(1, 2));
        this.waitTime = waitTime;

        message = new JLabel("Info goes here");
        estimateLabel = new JLabel("estimates goes here");
        this.add(message);
        this.add(estimateLabel);
        this.setSize(100, 20);
    }

    public void updateView(Point botPosition, Point sensorPosition, Point estimatePosition, double estimation){
        message.setOpaque(false);

        if (sensorPosition.equals(State.NOTHING)) {
            message.setText("<html>Robot: " + botPosition
                    + "<br>Sensor: Nothing<br>Estimate: " + estimatePosition
                    + "</html>");

        } else {
            message.setText("<html>Robot: " + botPosition + "<br>Sensor: "
                    + sensorPosition + "<br>Estimate: " + estimatePosition
                    + "</html>");
        }

        setEstimation(estimation);
    }

    private void setEstimation(double estimation){
        estimateLabel.setText("<html>Correct estimates: " + estimation + "%<br>Wait Time: " + waitTime);
    }

    public JLabel getMessageLabel(){
        return message;
    }
}
