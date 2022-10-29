package dungeoncrypt.game.entities;

import dungeoncrypt.game.room.Room;
import java.util.Random;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données d'un monstre
 */
public final class Monster extends Entity {

    private int timer;

    private int horizontalForce;
    private int verticalForce;

    public Monster(int x, int y) {
        super(MONSTER_TYPE,MONSTER_INITIAL_HP,"sprites/monster.png");
        setX(x);
        setY(y);
        this.timer = 0;
        this.horizontalForce = 0;
        this.verticalForce = 0;

    }

    /**
     * Met à jour la position du monstre en fonction d'un nombre aléatoire entre 0 compris et 4 non compris
     * @param actualRoom
     */
    public void updatePosition(Room actualRoom){
        if(timer == 0){
            Random random = new Random();
            int randomDirection = random.nextInt(4);
            this.verticalForce = 0;
            this.horizontalForce = 0;
            if(randomDirection == 0) {
                //if(actualRoom.isTileWalkable(getX(),getY()-1) && !actualRoom.isPlayerHere(getX(),getY()-1)){
                verticalForce = verticalForce - 1;
                //}
            } else if (randomDirection == 1) {
                //if(actualRoom.isTileWalkable(getX(),getY()+1) && !actualRoom.isPlayerHere(getX(),getY()+1)){
                verticalForce = verticalForce + 1;
                // }
            } else if (randomDirection == 2) {
                //if(actualRoom.isTileWalkable(getX()-1,getY()) && !actualRoom.isPlayerHere(getX()-1,getY())){
                horizontalForce = horizontalForce - 1;
                //}
            } else {
                //if(actualRoom.isTileWalkable(getX()+1,getY()) && !actualRoom.isPlayerHere(getX()+1,getY())){
                horizontalForce = horizontalForce + 1;
                //}
            }
        }
        timer++;
        if (timer == 100){
            timer =0;
        }
        getBody().setLinearVelocity(horizontalForce*50,verticalForce*50);
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));

    }


}
