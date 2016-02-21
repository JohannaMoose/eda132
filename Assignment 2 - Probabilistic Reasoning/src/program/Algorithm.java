package program;

/**
 * Created by Johanna on 2016-02-15.
 */

public class Algorithm {
    private double[] stateProbability;
    private double[][] transitionMatrix;
    private State[] stateMapping;
    private int nbrStates;
    private int gridSize;

    public Algorithm(int gridSize) {
        nbrStates = 4 * gridSize * gridSize;
        this.gridSize = gridSize;
        setStateData(gridSize);
        createTransitionMatrix(gridSize);
    }

    private void setStateData(int gridSize) {
        stateProbability = new double[nbrStates];
        stateMapping = new State[nbrStates];

        for (int i = 0; i < gridSize * gridSize; i++) {
            Point point = new Point(i / gridSize, i % gridSize);

            for (Direction direction : Direction.values()) {
                State state = createState(point, direction);
                int index = (i * 4) + direction.getValue();
                stateMapping[index] = state;
                stateProbability[index] = 1.0 / nbrStates;
            }
        }
    }

    private State createState(Point point, Direction dir) {
        State state = null;
        switch (dir) {
            case North:
                state = new State(point, Direction.North);
                break;
            case South:
                state = new State(point, Direction.South);
                break;
            case East:
                state = new State(point, Direction.East);
                break;
            case West:
                state = new State(point, Direction.West);
                break;
        }
        return state;
    }

    private void createTransitionMatrix(int gridSize) {
        transitionMatrix = new double[nbrStates][nbrStates];

        for (int i = 0; i < nbrStates; i++) {
            State state = stateMapping[i];
            for (int j = 0; j < nbrStates; j++) {
                State target = stateMapping[j];
                if (state.isNeighbourTo(target)) {
                    if (state.isEncounteringWall(gridSize)) {
                        transitionMatrix[i][j] = 1.0 / state.numberOfNeighbours(gridSize);
                    } else if (state.heading == target.heading) {
                        transitionMatrix[i][j] = 0.7;
                    } else {
                        transitionMatrix[i][j] = 0.3 / (state.numberOfNeighbours(gridSize) - 1);
                    }
                } else {
                    transitionMatrix[i][j] = 0;
                }
            }
        }
    }

    public Point getEstimatedLocation(Point point) {
        int index = multiplication(getObservationMatrix(point));
        return stateMapping[index].point;
    }

    private double[] getObservationMatrix(Point point) {
        double[] observationMatrix = new double[nbrStates];

        if (point.equals(State.NOTHING))
            createMatrixForNothing(observationMatrix);
        else
            createMatrixForState(point, observationMatrix);

        return observationMatrix;
    }

    private void createMatrixForNothing(double[] observationMatrix) {
        for (int i = 0; i < nbrStates; i++) {
            observationMatrix[i] = 0.1 + (0.025 * outsideBorder(stateMapping[i].point));
        }
    }

    private int outsideBorder(Point point) {
        int outside = 0;
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                Point tempPoint = new Point(point.x + x, point.y + y);

                if (tempPoint.x < 0 || tempPoint.y < 0 || tempPoint.x >= gridSize || tempPoint.y >= gridSize)
                    outside++;
            }
        }
        return outside;
    }

    private void createMatrixForState(Point point, double[] observationMatrix) {
        for (int i = 0; i < nbrStates; i++) {
            if (point.equals(stateMapping[i].point))
                observationMatrix[i] = 0.1;
            else if (checkStep(point, stateMapping[i], 1))
                observationMatrix[i] = 0.05;
            else if (checkStep(point, stateMapping[i], 2))
                observationMatrix[i] = 0.025;
            else
                observationMatrix[i] = 0;

        }
    }

    private boolean checkStep(Point point, State state, int step) {
        for (int x = -step; x <= step; x++) {
            for (int y = -step; y <= step; y++) {
                if (Math.abs(x) == step || Math.abs(y) == step) {
                    Point tmp = new Point(point.x + x, point.y + y);
                    if (tmp.equals(state.point))
                        return true;
                }
            }
        }
        return false;
    }

    private int multiplication(double[] observationMatrix) {
        double[] temp = new double[nbrStates];
        double alpha = populateTempAndCalculateAlpha(observationMatrix, temp);
        return getMostLikelyState(temp, 1 / alpha);
    }

    private double populateTempAndCalculateAlpha(double[] observationMatrix, double[] temp) {
        double alpha = 0;

        for (int row = 0; row < nbrStates; row++) {
            temp[row] = 0;
            for (int i = 0; i < nbrStates; i++) {
                temp[row] += transitionMatrix[i][row] * stateProbability[i] * observationMatrix[row];
            }
            alpha += temp[row];
        }

        return alpha;
    }

    private int getMostLikelyState(double[] temp, double alpha) {
        int mostLikelyState = -1;
        double max = 0;

        for (int i = 0; i < nbrStates; i++) {
            stateProbability[i] = temp[i] * alpha;
            if (temp[i] > max) {
                mostLikelyState = i;
                max = temp[i];
            }
        }

        return mostLikelyState;
    }

}
