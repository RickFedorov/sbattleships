package cz.sxmartin.sbattleships.engine;

import cz.sxmartin.sbattleships.engine.log.MessageLog;

public class Game {
    private static final String TAG = Game.class.getName();

    protected Player[] players;
    protected Puppeteer puppeteer;
    protected Player currentPlayer;

    public Game() {
        this.players = new Player[2];
        this.players[0] = new Player(this,true); //player
        this.players[1] = new Player(this,false); //bot

        this.puppeteer = new Puppeteer(this, this.getPlayers()[1]);

        //TODO debug puppeteer bot vs bot
                Puppeteer debugPuppeter = new Puppeteer(this, this.getPlayers()[0]);

                this.puppeteer.bot.getGrid().gridPrintDebug();
                debugPuppeter.bot.getGrid().gridPrintDebug();
                for (int d = 1; d < 100; d++){
                    this.puppeteer.doTurn();
                    debugPuppeter.doTurn();

                    if (this.puppeteer.bot.isDefeated()){
                        new MessageLog(String.format("We have the winner: DebugPuppeter " +debugPuppeter.bot.getName() + "%n"));
                        break;
                    }
                    if (debugPuppeter.bot.isDefeated()){
                        new MessageLog(String.format("We have the winner:  Puppeter" +this.puppeteer.bot.getName() + "%n"));
                        break;
                    }
                }
                this.puppeteer.bot.getGrid().gridPrintDebug();
                debugPuppeter.bot.getGrid().gridPrintDebug();

        this.currentPlayer = this.getPlayers()[0];
    }


    public Player[] getPlayers() {
        return players;
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
