package engine;

import java.util.HashMap;

public class Grid {
    private static final String TAG = Grid.class.getName();

    protected Player player;
    protected Point[] points;
    protected HashMap<Integer,Point> gridMap;
    protected int columns = 10;
    protected int rows = 10;

    public Grid(Player player) {
        this.player = player;
    }


}
