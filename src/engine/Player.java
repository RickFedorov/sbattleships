package engine;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

import engine.log.MessageLog;

public class Player {
    protected Game game;
    protected Grid grid;
    protected ArrayList<Ship> ships = new ArrayList<>();
    protected ArrayDeque<Point> hitHistory = new ArrayDeque<>();
    protected String name = "Unnamed player";
    protected boolean isDefeated = false;
    protected boolean isHuman;


    public Player(Game game, boolean isHuman) {
        this.game = game;
        this.isHuman = isHuman;
        this.grid = new Grid(this);

        //number of ships can be changed later
        this._initShips(new ShipType[]{ShipType.CARRIER, ShipType.BATTLESHIP, ShipType.CRUISER, ShipType.SUBMARINE, ShipType.DESTROYER});
    }

    private void _initShips(ShipType[] shipTypes){
        for (ShipType shipType : shipTypes) {
            this.ships.add(new Ship(this,shipType));
        }
    }

    public boolean isDefeated (){
        //check whether number of destroyed ships and if all are destroyed player is defeated.
        if(Arrays.stream((Ship[]) this.ships.toArray()).filter(ship -> ship.isDestroyed()).count() == this.ships.size()){
            this.isDefeated = true;
        }
        return this.isDefeated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public ArrayList<Ship> getShips() {
        return ships;
    }

    public Grid getGrid() {
        return grid;
    }


}
