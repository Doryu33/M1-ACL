package terminal.tiles;

import static terminal.data.Data.EXIT_TYPE;

public final class Exit extends Tile {

    public Exit() {
        super(EXIT_TYPE);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
