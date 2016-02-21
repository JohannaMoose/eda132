package program;

/**
 * Created by Johanna on 2016-02-15.
 */
public enum Direction {
    North (0),
    South (1),
    East (2),
    West (3);

    private final int value;
    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
