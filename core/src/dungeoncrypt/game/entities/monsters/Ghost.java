package dungeoncrypt.game.entities.monsters;

import dungeoncrypt.game.room.Room;

import static dungeoncrypt.game.data.Data.*;

public final class Ghost extends Monster{

    public Ghost(int x, int y) {
        super(x, y, GHOST_INITIAL_HP, GHOST_TYPE, "sprites/entities/monsters/ghost.gif");
    }

    @Override
    public void updatePosition(Room actualRoom) {

    }
}
