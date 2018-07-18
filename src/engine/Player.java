package engine;

public class Player {
    protected Game game;
    protected Grid grid;
    protected Ship[] ships;
    protected boolean isDefeated = false;
    protected boolean isHuman = false;

    public Player(Game game, boolean isHuman) {
        this.game = game;
        this.isHuman = isHuman;
        this.grid = new Grid(this);
    }
}
