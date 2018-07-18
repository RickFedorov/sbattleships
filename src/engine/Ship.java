package engine;

public class Ship {
    protected Player player;
    protected Point[] points;
    protected ShipType shipType;
    protected boolean isDestroyed = false;


    public Ship(Player player, ShipType shipType) {
        this.player = player;
        this.shipType = shipType;

    }
}
