package cz.sxmartin.sbattleships.engine;

import cz.sxmartin.sbattleships.engine.exception.GameException;
import cz.sxmartin.sbattleships.engine.log.MessageLog;

public class Game {
    private static final String TAG = Game.class.getName();

    protected Player[] players;
    protected Puppeteer puppeteer;
    protected Puppeteer debugPuppeter;
    protected Player currentPlayer;

    public Game() {
        this.players = new Player[2];
        this.players[0] = new Player(this,true); //player
        this.players[1] = new Player(this,false); //bot

        this.puppeteer = new Puppeteer(this, this.getPlayers()[1]);
        this.debugPuppeter = new Puppeteer(this, this.getPlayers()[0]);

        this.currentPlayer = this.getPlayers()[0];
    }

    public void testTURN(){
        //this.puppeteer.bot.getGrid().gridPrintDebug();
        //debugPuppeter.bot.getGrid().gridPrintDebug();
            this.puppeteer.doTurn();
            debugPuppeter.doTurn();

            if (this.puppeteer.bot.isDefeated()){
                new MessageLog(String.format("We have the winner: DebugPuppeter " +debugPuppeter.bot.getName() + "%n"));
            }
            if (debugPuppeter.bot.isDefeated()){
                new MessageLog(String.format("We have the winner:  Puppeter" +this.puppeteer.bot.getName() + "%n"));
            }

        //this.puppeteer.bot.getGrid().gridPrintDebug();
        //debugPuppeter.bot.getGrid().gridPrintDebug();
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(boolean getHumanPlayer) throws GameException{
        for (Player player: this.players) {
            if(player.isHuman() == getHumanPlayer){
                return player;
            }
        }
        throw new GameException("Players not initialized or not found!");
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Puppeteer getPuppeteer() {
        return puppeteer;
    }

    public void setPuppeteer(Puppeteer puppeteer) {
        this.puppeteer = puppeteer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
