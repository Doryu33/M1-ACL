package terminal.entities;

import terminal.room.Room;

import java.util.Random;

import static terminal.data.Data.MONSTER_TYPE;

/**
 * Gère le comportement et données d'un monstre
 */
public final class Monster extends Entity {

    public Monster(int x, int y) {
        super(MONSTER_TYPE);
        setX(x);
        setY(y);
    }

    /**
     * Met à jour la position du monstre en fonction d'un nombre aléatoire entre 0 compris et 4 non compris
     * @param actualRoom
     */
    public void updatePosition(Room actualRoom){
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        if(randomDirection == 0) {
            if(actualRoom.isTileWalkable(getX(),getY()-1) && !actualRoom.isPlayerHere(getX(),getY()-1)){
                setY(getY()-1);
            }
        } else if (randomDirection == 1) {
            if(actualRoom.isTileWalkable(getX(),getY()+1) && !actualRoom.isPlayerHere(getX(),getY()+1)){
                setY(getY()+1);
            }
        } else if (randomDirection == 2) {
            if(actualRoom.isTileWalkable(getX()-1,getY()) && !actualRoom.isPlayerHere(getX()-1,getY())){
                setX(getX()-1);
            }
        } else {
            if(actualRoom.isTileWalkable(getX()+1,getY()) && !actualRoom.isPlayerHere(getX()+1,getY())){
                setX(getX()+1);
            }
        }
    }
}
