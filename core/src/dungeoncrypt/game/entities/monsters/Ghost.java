package dungeoncrypt.game.entities.monsters;

import dungeoncrypt.game.room.Room;

import static dungeoncrypt.game.data.Data.*;

public final class Ghost extends Monster{

    public Ghost(int x, int y) {
        super(x, y, GHOST_INITIAL_HP, GHOST_TYPE, GHOST_SCORE,"sprites/entities/monsters/ghost.gif");
    }

    @Override
    public void updatePosition(Room actualRoom) {
        float playerPosX = actualRoom.getPlayerPosX()/RENDER_SCALE;
        float playerPosY = actualRoom.getPlayerPosY()/RENDER_SCALE;

        float monsterPosX = getBody().getPosition().x/RENDER_SCALE;
        float monsterPosY = getBody().getPosition().y/RENDER_SCALE;

        double distanceWithPlayer = Math.sqrt(((playerPosY-monsterPosY)*(playerPosY-monsterPosY)+
                (playerPosX-monsterPosX)*(playerPosX-monsterPosX)));

        this.verticalForce = 0;
        this.horizontalForce = 0;

        if(distanceWithPlayer < 7){
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
