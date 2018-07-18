package engine;

public class Puppeteer {
    protected Game game;
    protected Player bot;

    public Puppeteer(Game game, Player bot) {
        this.game = game;
        this.bot = bot;
    }
}
