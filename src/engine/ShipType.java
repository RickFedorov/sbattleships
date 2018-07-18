package engine;

public enum ShipType {
    CARRIER     (5),
    BATTLESHIP  (4),
    CRUISER     (3),
    SUBMARINE   (3),
    DESTROYER   (2);

    private final int points;

    ShipType(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
