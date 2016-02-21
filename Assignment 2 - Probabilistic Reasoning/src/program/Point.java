package program;

/**
 * Created by Johanna on 2016-02-15.
 */

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addPoint(Point p) {
        this.x += p.x;
        this.y += p.y;
    }

    public double distance(Point p) {
        int dX = this.x - p.x;
        int dY = this.y - p.y;
        return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
    }

    public String toString() {
        return this.x + ", " + this.y;
    }

    public boolean equals(Object obj) {
        Point other = (Point) obj;
        return other != null && (other.x == x && other.y == y);
    }

    public int hashCode() {
        int hash = 7;
        hash = 91 * hash + this.x;
        hash = 91 * hash + this.y;
        return hash;
    }
}
