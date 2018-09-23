package cz.sxmartin.sbattleships.engine;

import java.util.ArrayList;
import java.util.Arrays;

import cz.sxmartin.sbattleships.Updateable;
import cz.sxmartin.sbattleships.engine.log.MessageLog;

public class Grid implements Updateable{
    private static final String TAG = Grid.class.getName();

    protected Player player;
    protected ArrayList<Point> gridMap = new ArrayList<>();
    protected int columns = 10;
    protected int rows = 10;

    public Grid(Player player) {
        this.player = player;
        this._generateGrid();
    }

    /**
     * Generate gir based on rows x columns setting
     */
    private void _generateGrid() {
        for (int r = 1; r <= this.rows; r++) {
            for (int c = 1; c <= this.columns; c++) {
                gridMap.add(new Point(this, r, c));
            }
        }
    }


    /**
     * @param ship  ship which should be allocated in grid
     * @param start defined start point
     * @param end   defined end point
     * @return if successful
     */
    public boolean placeShip(Ship ship, Point start, Point end) {

        try {
            Point[] designatedPoints = this.findPointsInGrid(this.pointsInLine(start, end));
            if (isAreaEmpty(designatedPoints)) {

                //Grid is empty ship can be allocated, update points and ships references
                if (ship.getPoints() != null){
                    Arrays.stream(ship.getPoints()).forEach(p -> p.setShip(null)); //for reallocation
                }

                Arrays.stream(designatedPoints).forEach(p -> p.setShip(ship)); //for new allocation

                ship.setPoints(designatedPoints); //set new points

                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            //out of grid, recurse above
            return false;
        }

    }


    public boolean isAreaEmpty(Point[] gridPoints) {
        if (Arrays.stream(gridPoints).filter(b -> !b.isEmpty()).count() > 0) {
            return false;
        }
        return true;
    }


    public Point[] findPointsInGrid(Point[] points) throws IndexOutOfBoundsException {
        Point[] gridPoints = new Point[points.length];
        int i = 0;
        for (Point point : points) {
            if (!this.gridMap.contains(point)) {
                //line is outside of grid
                throw new IndexOutOfBoundsException();
            } else {
                gridPoints[i++] = this.gridMap.get(this.gridMap.indexOf(point));
            }
        }
        return gridPoints;
    }

    public void updateView() {
        for (Point point :
                gridMap) {
            point.getView().updateView();
        }
    }

    public Point[] pointsInLine(Point start, Point end) {

        int rowChange = end.getRow() - start.getRow();
        int colChange = end.getColumn() - start.getColumn();
        int size = Math.abs(rowChange + colChange) + 1;

        if (size < 1) {
            throw new ArithmeticException("Size should be always positive.");
        }

        Point[] points = new Point[size];


        for (int i = 0; i < size; i++) {
            if (rowChange != 0) {
                points[i] = new Point(start.getRow() + (rowChange > 0 ? i : -i), start.getColumn());
            } else {
                points[i] = new Point(start.getRow(), start.getColumn() + (colChange > 0 ? i : -i));
            }
        }

        //points in line
        return points;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Point> getGridMap() {
        return gridMap;
    }

    public Point getPointXY(int row, int column) {
        int index = this.gridMap.indexOf(new Point(row, column));
        if (index < 0) {
            return null;
        }
        return this.gridMap.get(index);
    }

    public void gridPrintDebug() {
        //for debug only
        new MessageLog(String.format("Player: " + this.player.getName() + "%n"));
        for (int r = 1; r <= rows; r++) {
            String row = "";
            for (int c = 1; c <= columns; c++) {
                Point point = gridMap.get(gridMap.indexOf(new Point(r, c)));
                String fog = point.isEmpty() ? "." : String.valueOf(point.getShip().getShipType().getSize());

                switch (point.getStatus()) {
                    case FOG:
                        row = row + fog;
                        break;
                    case EMPTY:
                        row = row + "*";
                        break;
                    case HIT:
                        row = row + "X";
                        break;
                }

            }
            new MessageLog(String.format(row + "%n"));
        }
    }
}
