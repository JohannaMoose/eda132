package program;

import Gui.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Johanna on 2016-02-15.
 */
public class Program {
    private static int GRID_SIZE = 8;
    private static long WAIT_TIME = 100;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the robot simulation");
        System.out.print("How many milliseconds delay would you like between each robot move? This is purely for your viewing convinence in the GUI (recommended 1000): ");

        try {
            WAIT_TIME = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ok, running the program with that time, please view the GUI. To exit the program, simply hit any key on the keyboard");

        Robot robot = new Robot(GRID_SIZE);
        Gui gui = new Gui(GRID_SIZE, WAIT_TIME);
        Algorithm algorithm = new Algorithm(GRID_SIZE);

        int correctEstimates = 0;
        int nbrOfItr = 0;

        while (gui.hasNotGottenCloseCommand()) {

            Point estimatePosition = algorithm.getEstimatedLocation(robot.sensorOutput());

            nbrOfItr++;
            if (robot.currentPosition().equals(estimatePosition))
                correctEstimates++;

            gui.updateView(robot.currentPosition(), robot.sensorOutput(), estimatePosition);

            robot.move();
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double prob = ((double) (correctEstimates)) / nbrOfItr;
        System.out.println("Ratio of correct estimates this run of the program: " + prob + " with the number of iterations done: " + nbrOfItr);
        gui.close();
    }
}
