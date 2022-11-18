package dungeoncrypt.game.entities.monsters;

import dungeoncrypt.game.entities.Entity;
import dungeoncrypt.game.room.Room;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données d'un monstre
 */
public abstract class Monster extends Entity {

    protected int horizontalForce;
    protected int verticalForce;
    private int score;

    public Monster(int x, int y, int healthPoint, String specialType, int score, String spritePath) {
        super(MONSTER_TYPE,specialType,healthPoint,spritePath);
        this.score = score;
        setX(x);
        setY(y);
        this.horizontalForce = 0;
        this.verticalForce = 0;
    }

    /**
     * Mets à jour la position du monstre
     * @param actualRoom salle actuelle
     */
    public abstract void updatePosition(Room actualRoom);

    public int getScore(){
        return score;
    }
}
