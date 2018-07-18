package engine;

public class Point {
    protected Grid grid;
    protected int row;
    protected int column;
    protected PointType status;

    public Point(Grid grid) {
        this.grid = grid;
    }
}
