package program;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Johanna on 2016-02-15.
 */

public class Robot {
    private int sizeOfBoard;
    private Point currentPosition;
    private Random rnd = new Random();
    private Point currentHeading;
    private Point[] possibleHeadings;

    public Robot(int sizeOfBoard) {
        this.sizeOfBoard = sizeOfBoard;

        currentPosition = new Point(rnd.nextInt(sizeOfBoard), rnd.nextInt(sizeOfBoard));
        possibleHeadings = new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0)};
        currentHeading = possibleHeadings[rnd.nextInt(4)];

    }

    public void move() {
        currentHeading = getValidHeadingToMove();
        currentPosition.addPoint(currentHeading);
    }

    private Point getValidHeadingToMove() {
        HashSet<Point> illegalHeadings = getIllegalHeadings();

        if (illegalHeadings.contains(currentHeading) || rnd.nextDouble() >= 0.7) {
            illegalHeadings.add(currentHeading);
            return newRandomHeading(illegalHeadings);
        } else {
            return currentHeading;
        }
    }

    private HashSet<Point> getIllegalHeadings() {
        HashSet<Point> illegalHeadings = new HashSet<>();

        if (currentPosition.x >= sizeOfBoard - 1)
            illegalHeadings.add(new Point(1, 0));
        else if (currentPosition.x <= 0)
            illegalHeadings.add(new Point(-1, 0));
        if (currentPosition.y >= sizeOfBoard - 1)
            illegalHeadings.add(new Point(0, 1));
        else if (currentPosition.y <= 0)
            illegalHeadings.add(new Point(0, -1));
        return illegalHeadings;
    }

    private Point newRandomHeading(HashSet<Point> illegalHeadings) {
        int index;

        do {
            index = rnd.nextInt(4);
        }while (illegalHeadings.contains(possibleHeadings[index]));

        return possibleHeadings[index];
    }

    public Point sensorOutput() {
        double probability = rnd.nextDouble();

        if (probability < 0.1)
            return currentPosition();
        else if (probability < 0.5)
            return getLowProbabilitySensorOutput();
        else if (probability < 0.9)
            return getHighProbabilitySensorOutput();
        else
            return State.NOTHING;
    }

    private Point getLowProbabilitySensorOutput() {
        Point p = new Point(currentPosition.x + rnd.nextInt(3) - 1, currentPosition.y + rnd.nextInt(3) - 1);

        if (pointIsOutsideBoard(p))
            return State.NOTHING;
        else
            return p;
    }

    private boolean pointIsOutsideBoard(Point p) {
        return p.x < 0 || p.x >= sizeOfBoard || p.y < 0 || p.y >= sizeOfBoard;
    }

    private Point getHighProbabilitySensorOutput() {
        int tempX;
        int tempY;

        do {
            tempX = rnd.nextInt(5) - 2;
            tempY = rnd.nextInt(5) - 2;
        } while (tempX < 2 && tempY < 2);

        Point p = new Point(currentPosition.x + tempX, currentPosition.y + tempY);

        if (pointIsOutsideBoard(p))
            return State.NOTHING;
        else
            return p;
    }


    public Point currentPosition() {
        return new Point(currentPosition.x, currentPosition.y);
    }
}
