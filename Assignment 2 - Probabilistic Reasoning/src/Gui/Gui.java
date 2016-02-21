package Gui;

import program.Point;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by Johanna on 2016-02-15.
 */

public class Gui implements KeyListener {
    private JFrame frame;
    MsgPanel msgPanel;
    MatrixPanel matrixPanel;

    private Point currentRobotPosition;
    private Point currentSensorPosition;
    private Point estimateCurrentPosition;

    private int numberOfIterations;
    private int estimate;
    private boolean run;

    public Gui(int size, long waitTime) {
        numberOfIterations = 0;
        estimate = 0;
        run = true;

        frame = new JFrame("Moving Bot simulation");
        JPanel mainPanel = new JPanel(new BorderLayout());
        msgPanel = new MsgPanel(waitTime);
        matrixPanel = new MatrixPanel(size);


        mainPanel.add(msgPanel, BorderLayout.SOUTH);
        mainPanel.add(matrixPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.addKeyListener(this);


        currentRobotPosition = new Point(0, 0);
        currentSensorPosition = new Point(0, 0);
        estimateCurrentPosition = new Point(0, 0);
    }

    public void updateView(Point newRobotPosition, Point newSensorPosition, Point newEstimatePosition) {
        numberOfIterations++;
        double correctEstimates = 100 * ((double) estimate) / numberOfIterations;

        msgPanel.updateView(newRobotPosition, newSensorPosition, newEstimatePosition, correctEstimates);

        setRobotPosition(newRobotPosition);
        setSensorPosition(newSensorPosition);
        setEstimatePosition(newEstimatePosition);

        if (newRobotPosition.equals(newEstimatePosition)) {
            estimate++;
        }
    }

    private void setRobotPosition(Point newRobotPosition) {
        matrixPanel.setBotPosition(newRobotPosition, currentRobotPosition);
        currentRobotPosition = newRobotPosition;
    }

    private void setSensorPosition(Point newSensorPosition) {
        matrixPanel.setSensorPosition(newSensorPosition, currentSensorPosition, currentRobotPosition, msgPanel.getMessageLabel());
        if(newSensorPosition.x >= 0 && newSensorPosition.y >= 0)
            currentSensorPosition = newSensorPosition;
    }

    private void setEstimatePosition(Point newEstimatePosition) {
        matrixPanel.setEstimatePos(newEstimatePosition, estimateCurrentPosition,currentRobotPosition, currentSensorPosition);
        estimateCurrentPosition = newEstimatePosition;
    }

    public void close() {
        frame.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        run = false;

    }

    public boolean hasNotGottenCloseCommand() {
        return run;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;

    }
}
