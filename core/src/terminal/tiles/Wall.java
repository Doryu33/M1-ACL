package terminal.tiles;

import static terminal.data.Data.WALL_TYPE;

public final class Wall extends Tile {

    public Wall(){
        super(WALL_TYPE);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean hasSpecialEffect() {
        return false;
    }
}
