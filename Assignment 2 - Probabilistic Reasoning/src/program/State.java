package program;
/**
 * Created by Johanna on 2016-02-15.
 */
public class State {
    public static final Point NOTHING = new Point(-1, -1);

    Point point;
    Direction heading;

    public State(Point point, Direction heading) {
        this.point = point;
        this.heading = heading;
    }

    public boolean isNeighbourTo(State state) {
        return
                isNorthNeighbour(state, state.point)
                        || isSouthNeighbour(state, state.point)
                        || isEastNeighbour(state, state.point)
                        || isWestNeighbour(state, state.point);
    }

    private boolean isWestNeighbour(State state, Point point) {
        return point.x == this.point.x - 1 &&
                point.y == this.point.y &&
                state.heading == Direction.West;
    }

    private boolean isEastNeighbour(State state, Point point) {
        return point.x == this.point.x + 1 &&
                point.y == this.point.y &&
                state.heading == Direction.East;
    }

    private boolean isSouthNeighbour(State state, Point point) {
        return point.x == this.point.x &&
                point.y == this.point.y + 1 &&
                state.heading == Direction.South;
    }

    private boolean isNorthNeighbour(State state, Point point) {
        return point.x == this.point.x &&
                point.y == this.point.y - 1 &&
                state.heading == Direction.North;
    }

    public int numberOfNeighbours(int gridSize) {
        int nbrOfNeighbours = 4;
        if (point.x <= 0)
            nbrOfNeighbours--;
        if(point.x >= gridSize - 1)
            nbrOfNeighbours--;
        if (point.y <= 0)
            nbrOfNeighbours--;
        if(point.y >= gridSize - 1)
            nbrOfNeighbours--;

        return nbrOfNeighbours;
    }

    public boolean isEncounteringWall(int gridSize) {
        return isHeadingIntoNorthWall() ||
                isHeadingIntoSouthWall(gridSize) ||
                isHeadingIntoWestWall() ||
                isHeadingIntoEastWall(gridSize);
    }

    private boolean isHeadingIntoEastWall(int gridSize) {
        return heading == Direction.East && point.x >= gridSize - 1;
    }

    private boolean isHeadingIntoWestWall() {
        return heading == Direction.West && point.x <= 0;
    }

    private boolean isHeadingIntoSouthWall(int gridSize) {
        return heading == Direction.South && point.y >= gridSize - 1;
    }

    private boolean isHeadingIntoNorthWall() {
        return heading == Direction.North && point.y <= 0;
    }

    @Override
    public boolean equals(Object obj) {
        State other = (State) obj;
        return other != null &&
                (other.point.equals(point) && other.heading == heading);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 82 * hash + point.hashCode();
        hash = 82 * hash + heading.hashCode();
        return hash;
    }

}
