package cz.sxmartin.sbattleships.engine;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Puppeteer.
 * Act as AI in the game.
 */
public class Puppeteer {
    /**
     * The Game holder.
     */
    protected Game game;
    /**
     * The Bot. Who is the bot.
     */
    protected Player bot = null;
    /**
     * The Enemy player.
     */
    protected Player enemyPlayer = null;
    /**
     * The Bot names. Use randomly.
     */
    protected final String[] botNames = new String[]{"John","Rick","Bob","Rob"};


    /**
     * Instantiates a new Puppeteer.
     *
     * @param game the game
     * @param bot  the bot
     */
    public Puppeteer(Game game, Player bot) {
        this.game = game;
        this.bot = bot;

        //who is enemy
        for (Player player: this.game.getPlayers()) {
            if(!player.equals(this.bot)){
                this.enemyPlayer = player;
            }
        }

        //set bot name
        this.bot.setName(this.botNames[new Random().nextInt(this.botNames.length)]);

        //set bot ships in grid
        this._placeShips();
    }

    /**
     * Allocate ships into grid
     */
    private void _placeShips(){

        for (Ship ship : this.bot.getShips()) {
            this._findPlace(ship);
        }
        //Debug
        //this.bot.getGrid().gridPrintDebug();
    }

    /**
     *
     * @param ship which ship to allocate
     * @return
     */
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
            return this._findPlace(ship); //recursion there has to be a place to put a ship
        }
       //new MessageLog(new String().format(ship.getShipType().getName()+" ["+randRowStart+","+randColStart+"]:["+randRowEnd+","+randColEnd+"]%n"));

        return true;
    }

    /**
     * Do turn.
     */
    public void doTurn(){

        this.fire(this._pickNearbyPoints());
    }

    /**
     * Try to find Points near to the last hit if there is no such place then select randomly from fog Points
     * @return Point[] of possible fire solution
     */
    private Point[] _pickNearbyPoints(){

        if (this.bot.getHitHistory().isEmpty()) //recursion base if no hit history
        {
            return this._pickFOGPoints();
        }
        else{
            Point lastHit = this.bot.getHitHistory().getLast();

            if (lastHit.getShip().isDestroyed()){ //last hit destroyed ship so remove the from hit history and start look elsewhere
                this.bot.getHitHistory().remove(lastHit);
                return this._pickNearbyPoints(); //recursion
            }
            else {
                List<Point> nearby = lastHit.getNearbyPoints();

                if (nearby.size() < 1) //last point does not have any nearby point => remove from queue
                {
                    this.bot.getHitHistory().remove(lastHit);
                    return this._pickNearbyPoints(); //recursion
                } else {
                    return nearby.toArray(new Point[nearby.size()]);
                }
            }
        }

    }

    /**
     * Pick all fog Points in enemy grid and return them.
     * @return Point[] of possible fire solution
     */
    private Point[] _pickFOGPoints(){
        List<Point> enemyGridFog = this.enemyPlayer.getGrid().getGridMap().stream()
                .filter(P -> PointType.FOG == P.getStatus())
                .collect(Collectors.toList());

        return enemyGridFog.toArray(new Point[enemyGridFog.size()]);
    }

    /**
     * Fire.
     *
     * @param possibleTargetPoints the possible target points
     */
    protected void fire (Point[] possibleTargetPoints){
        Point targetPoint = possibleTargetPoints[new Random().nextInt(possibleTargetPoints.length)];
        if (targetPoint.processFire() == PointType.HIT){
            //todo hit history
            this.bot.getHitHistory().add(targetPoint);
        }
    }

}
