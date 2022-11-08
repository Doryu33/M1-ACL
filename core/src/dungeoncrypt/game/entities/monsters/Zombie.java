package dungeoncrypt.game.entities.monsters;

import dungeoncrypt.game.room.Room;

import static dungeoncrypt.game.data.Data.*;

public final class Zombie extends Monster {

    public Zombie(int x, int y) {
        super(x, y,ZOMBIE_INITIAL_HP,ZOMBIE_TYPE,"sprites/entities/monsters/zombie.gif");
    }

    @Override
    //Créer le pathfinding
    public void updatePosition(Room actualRoom) {
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
    }
}

//https://isaaccomputerscience.org/concepts/dsa_search_a_star?examBoard=all&stage=all
