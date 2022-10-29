package dungeoncrypt.game.tiles.classic;
import dungeoncrypt.game.tiles.Tile;

import static dungeoncrypt.game.data.Data.WALL_TYPE;

public final class Wall extends Tile {

    public Wall(){
        super(WALL_TYPE,"sprites/wall.png");
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
