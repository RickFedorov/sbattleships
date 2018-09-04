package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import engine.log.MessageLog;

public class Puppeteer {
    protected Game game;
    protected Player bot;
    protected final String[] botNames = new String[]{"John","Rick","Bob","Rob"};


    public Puppeteer(Game game, Player bot) {
        this.game = game;
        this.bot = bot;

        //set bot name
        this.bot.setName(this.botNames[new Random().nextInt(this.botNames.length)]);

        //set bot ships in grid
        this._placeShips();
    }

    private void _placeShips(){

        for (Ship ship : this.bot.getShips()) {
            this._findPlace(ship);
        }
        //Debug
        //this.bot.getGrid().gridPrintDebug();
    }

    private boolean _findPlace (Ship ship){
        int shipSize = ship.getShipType().getSize();
        Grid gridMap = ship.getPlayer().getGrid();

        int randRowStart = (int )(Math.random() * ship.getPlayer().getGrid().getRows() + 1);
        int randColStart = (int )(Math.random() * ship.getPlayer().getGrid().getColumns() + 1);
        int randRowEnd = randRowStart;
        int randColEnd = randColStart;

        if(new Random().nextBoolean()){
            randRowEnd = randRowStart + shipSize;
        }else{
            randColEnd = randColStart + shipSize;
        }

        if (!gridMap.placeShip(ship,new Point(randRowStart, randColStart),new Point(randRowEnd, randColEnd))){
            return this._findPlace(ship);
        }
       //new MessageLog(new String().format(ship.getShipType().getName()+" ["+randRowStart+","+randColStart+"]:["+randRowEnd+","+randColEnd+"]%n"));

        return true;
    }

    public void doTurn(){
        Player enemyPlayer = null;
        Point[] enemyGrid;
        ArrayList<Point> enemyGridFog = new ArrayList<>();

        for (Player player: this.game.getPlayers()) {
            if(!player.equals(this.bot)){
                enemyPlayer = player;
            }
        }

        enemyGrid = enemyPlayer.getGrid().getGridMap().toArray(new Point[enemyPlayer.getGrid().getGridMap().size()]);
        for (Point point :
                enemyGrid) {
            if (point.getStatus() == PointType.FOG){
                enemyGridFog.add(point);
            }
        }

        //todo  take enemy grid
        //todo check hit history
        //if not empty then connected hits
        //if connected then which direction and fire
        //if not connected than fire random
        //todo exclude empty
        //todo fire


        if (this.bot.hitHistory.isEmpty()){
            //no hit in the history = choose randomly
            this.fire(enemyGridFog.toArray(new Point[enemyGridFog.size()]));
        }
        else{
           //hit in history log, try to locate the rest of the ship
            this.fire(this._pickNearbyPoints());
        }

    }

    private Point[] _pickNearbyPoints(){
        this.bot.hitHistory.getFirst();

    }

    protected void fire (Point[] possibleTargetPoints){
        Point targetPoint = possibleTargetPoints[new Random().nextInt(possibleTargetPoints.length)];
        if (targetPoint.processFire() == PointType.HIT){
            //todo hit history
            this.bot.hitHistory.add(targetPoint);
        }
    }

}
