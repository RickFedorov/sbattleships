package engine;

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
