package dungeoncrypt.game.tiles;
import static dungeoncrypt.game.data.Data.EXIT_TYPE;

public final class Exit extends Tile {

    public Exit() {
        super(EXIT_TYPE,"sprites/exit.png");
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

}
