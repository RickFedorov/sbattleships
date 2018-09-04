package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 */
public class Point {
    private static final String TAG = Point.class.getName();

    protected Grid grid;
    protected int row;
    protected int column;
    protected Ship ship;
    protected PointType status = PointType.FOG;


    /**
     * Constructor for points in gridMap
     * @param grid
     * @param row
     * @param column
     */
    public Point(Grid grid, int row, int column) {
        this.grid = grid;
        this.row = row;
        this.column = column;
    }

    /**
     * Constructor for search Point
     * @param row
     * @param column
     */
    public Point(int row, int column){
        this.row = row;
        this.column = column;
    }

    public boolean isEmpty(){
        if (this.ship == null){
            return true;
        }
        return false;
    }

    public PointType processFire(){
        if (this.isEmpty()){
            this.status = PointType.EMPTY;
        }
        else{
            //if not empty then it is hit and ship has to be informed
            this.status = PointType.HIT;
            this.ship.processHit();
        }
        return this.status;
    }

    public List<Point> getNearbyPoints(){
        List<Point> nearby = this.grid.getGridMap().stream()
                .filter(P ->
                        (
                                (this.row - 1 == P.getRow() && this.column == P.getColumn()) ||
                                (this.row + 1== P.getRow() && this.column == P.getColumn()) ||
                                (this.row == P.getRow() && this.column + 1 == P.getColumn()) ||
                                (this.row == P.getRow() && this.column - 1 == P.getColumn())
                        )
                                && PointType.FOG == P.getStatus())

                .collect(Collectors.toList());

        return nearby;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public PointType getStatus() {
        return status;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return row == point.row &&
                column == point.column;
    }

    @Override
    public int hashCode() {

        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return String.format("Point{" +
                " row=" + row +
                ", column=" + column +
                ", status=" + status +
                ", ship=" + ship +
                "}");
    }
}
