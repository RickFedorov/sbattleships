package engine;

public enum ShipType {
    CARRIER     ("Carrier",     5),
    BATTLESHIP  ("Battleship",  4),
    CRUISER     ("Cruiser",     3),
    SUBMARINE   ("Submarine",   3),
    DESTROYER   ("Destroyer",   2);

    private final String name;
    private final int size;


    ShipType(String name, int points) {
        this.name = name;
        this.size = points;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ShipType{"+this.name+"}";
    }
}
