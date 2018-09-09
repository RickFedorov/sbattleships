package cz.sxmartin.sbattleships.engine.exception;

public class GameException extends Exception {

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(String message) {
        super(message);
    }

}
