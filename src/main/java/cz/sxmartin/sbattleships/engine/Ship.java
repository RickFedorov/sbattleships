package cz.sxmartin.sbattleships.engine;

import java.util.Arrays;

import cz.sxmartin.sbattleships.ShipTextView;

public class Ship {
    protected Player player;
    protected Point[] points;
    protected ShipType shipType;
    protected boolean isDestroyed = false;
    protected ShipTextView shipTextView;

    public Ship(Player player, ShipType shipType) {
        this.player = player;
        this.shipType = shipType;
    }

    public void processHit(){
        if(Arrays.stream(this.points).filter(point -> point.getStatus() == PointType.HIT).count() == this.points.length && isDestroyed == false){
            this.isDestroyed = true;
            this.shipTextView.shipDestroyed();
        }
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public void setShipTextView(ShipTextView shipTextView) {
        this.shipTextView = shipTextView;
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
