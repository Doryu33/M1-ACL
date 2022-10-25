package terminal.tiles.classic;

import terminal.tiles.Tile;

import static terminal.data.Data.FLOOR_TYPE;

public final class Floor extends Tile {

    public Floor() {
        super(FLOOR_TYPE);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean hasSpecialEffect() {
        return false;
    }
}
