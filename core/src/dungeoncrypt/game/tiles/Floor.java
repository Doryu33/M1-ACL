package dungeoncrypt.game.tiles;
import static dungeoncrypt.game.data.Data.FLOOR_TYPE;

public class Floor extends Tile{

    public Floor() {
        super(FLOOR_TYPE,"sprites/floor.png");
    }

    @Override
    public boolean isWalkable() {
        return true;
    }


}
