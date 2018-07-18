package engine;

public class Point {
    private static final String TAG = Point.class.getName();

    protected Grid grid;
    protected int row;
    protected int column;
    protected PointType status;

    public Point(Grid grid) {
        this.grid = grid;
    }
}
