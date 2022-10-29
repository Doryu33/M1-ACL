package dungeoncrypt.game.tiles.classic;
import dungeoncrypt.game.tiles.Tile;

import static dungeoncrypt.game.data.Data.FLOOR_TYPE;

public final class Floor extends Tile {

    public Floor() {
        super(FLOOR_TYPE,"sprites/tiles/classic/floor.png");
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
