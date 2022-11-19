package dungeoncrypt.game.entities.monsters;

import dungeoncrypt.game.room.Room;

import static dungeoncrypt.game.data.Data.*;

public final class Ghost extends Monster{

    public Ghost(int x, int y) {
        super(x, y, GHOST_INITIAL_HP, GHOST_TYPE, "sprites/entities/monsters/ghost.gif");
    }

    @Override
    public void updatePosition(Room actualRoom) {
        float playerPosX = actualRoom.getPlayerPosX();
        float playerPosY = actualRoom.getPlayerPosY();

        float monsterPosX = getBody().getPosition().x;
        float monsterPosY = getBody().getPosition().y;

        this.verticalForce = 0;
        this.horizontalForce = 0;

        if(playerPosY > monsterPosY) {
            verticalForce = verticalForce + 1;
        } else if (playerPosY <= monsterPosY) {
            verticalForce = verticalForce - 1;
        }

        if (playerPosX > monsterPosX) {
            horizontalForce = horizontalForce + 1;
        } else if (playerPosX <= monsterPosX){
            horizontalForce = horizontalForce - 1;
        }

        if (getKnockbackVertical() != 0 || getKnockbackHorizontal() != 0){
            knockbackMove();
        }else{
            getBody().setLinearVelocity(horizontalForce*MOVE_SPEED_MONSTER,verticalForce*MOVE_SPEED_MONSTER);
        }
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
    }

    protected String getPathDamageSound() {
        return "sounds/Ghost_Damage.mp3";
    }
}
