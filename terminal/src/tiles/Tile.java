package tiles;

public abstract class Tile {

    private final String type;

    public Tile(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
