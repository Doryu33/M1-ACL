package dungeoncrypt.game.tiles;
import static dungeoncrypt.game.data.Data.WALL_TYPE;

public class Wall extends Tile{

    public Wall(){
        super(WALL_TYPE,"sprites/wall.png");
    }

    @Override
    public boolean isWalkable() {
        return false;
    }



}
