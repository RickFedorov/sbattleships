package engine;

public class Grid {
    private static final String TAG = Grid.class.getName();

    protected Player player;
    protected Point[] points;

    public Grid(Player player) {
        this.player = player;
    }
}
