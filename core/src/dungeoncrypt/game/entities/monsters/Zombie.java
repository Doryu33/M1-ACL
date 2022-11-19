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
        if (getKnockbackVertical() != 0 || getKnockbackHorizontal() != 0){
            knockbackMove();
        }else{
            getBody().setLinearVelocity(horizontalForce*MOVE_SPEED_MONSTER,verticalForce*MOVE_SPEED_MONSTER);
        }
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
    }

    @Override
    protected String getPathDamageSound() {
        return "sounds/Zombie_Damage.mp3";
    }
}

//https://isaaccomputerscience.org/concepts/dsa_search_a_star?examBoard=all&stage=all
