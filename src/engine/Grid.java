package engine;

import java.util.ArrayList;
import java.util.Arrays;

import engine.log.MessageLog;

public class Grid {
    private static final String TAG = Grid.class.getName();

    protected Player player;
    protected ArrayList<Point> gridMap = new ArrayList<>();
    protected int columns = 10;
    protected int rows = 10;

    public Grid(Player player) {
        this.player = player;
        this._generateGrid();
    }

    private void _generateGrid(){
        for (int r = 1; r <= this.rows; r++){
            for (int c = 1; c <= this.columns; c++){
                gridMap.add(new Point(this, r, c));
            }
        }
    }

    public boolean placeShip (Ship ship, Point start, Point end) {

        try {
            Point[] gridPoints = this.findPointsInGrid(this.pointsInLine(start,end));
            if (isAreaEmpty(gridPoints)){
                Arrays.stream(gridPoints).forEach(p -> p.setShip(ship));
                return true;
            }
            else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

    }


    public boolean isAreaEmpty (Point[] gridPoints){
        if (Arrays.stream(gridPoints).filter(b -> !b.isEmpty()).count()>0){
            return false;
        }
        return true;
    }

    public Point[] findPointsInGrid(Point[] points) throws IndexOutOfBoundsException {
        Point[] gridPoints = new Point[points.length];
        int i = 0;
        for (Point point : points) {
            if (!this.gridMap.contains(point)){
                throw new IndexOutOfBoundsException();
            }
            else {
                gridPoints[i++] = this.gridMap.get(this.gridMap.indexOf(point));
            }
        }
        return gridPoints;
    }


    public Point[] pointsInLine(Point start, Point end){

        int rowChange = end.getRow() - start.getRow();
        int colChange = end.getColumn() - start.getColumn();
        int size = rowChange + colChange;

        if (size < 1){ throw new ArithmeticException("Size should be always positive.");}

        Point[] points = new Point[size];


        for (int i = 0; i < size; i++) {
            if (rowChange != 0){
                points[i] = new Point(start.getRow()+i,start.getColumn());
            }
            else{
                points[i] = new Point(start.getRow(),start.getColumn()+i);
            }
        }



        return points;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void gridPrintDebug(){
        for (int r = 1; r <= rows; r++){
            String row = "";
            for (int c = 1; c <= columns; c++){
                if (gridMap.get(gridMap.indexOf(new Point(r,c))).isEmpty()){
                    row = row + ".";
                }
                else{
                    row = row + String.valueOf(gridMap.get(gridMap.indexOf(new Point(r,c))).getShip().getShipType().getSize());
                }
            }
            System.out.println(row);
        }
    }
}
