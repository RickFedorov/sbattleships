package engine;

import java.util.Arrays;

public class Ship {
    protected Player player;
    protected Point[] points;
    protected ShipType shipType;
    protected boolean isDestroyed = false;


    public Ship(Player player, ShipType shipType) {
        this.player = player;
        this.shipType = shipType;
    }

    public void processHit(){
        if(Arrays.stream(this.points).filter(point -> point.getStatus() == PointType.HIT).count() == this.points.length){
            this.isDestroyed = true;
        }
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public String toString() {
        return String.format("Ship{" +
                "player=" + player.getName() +
                ", shiptype=" + shipType.getName() +
                "}");
    }


}
