package entities;

import java.util.Random;

import static data.Data.MONSTER_TYPE;

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
     */
    public void updatePosition(){
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        if(randomDirection == 0) {
            setY(getY()-1);
        } else if (randomDirection == 1) {
            setY(getY()+1);
        } else if (randomDirection == 2) {
            setX(getX()-1);
        } else {
            setX(getX()+1);
        }
    }
}
