package terminal.tiles.classic;

import terminal.tiles.Tile;

import static terminal.data.Data.EXIT_TYPE;

//TODO passer en special tile ?
public final class Exit extends Tile {

    public Exit() {
        super(EXIT_TYPE);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean hasSpecialEffect() {
        return false; //Mettre a true ?
    }
}
