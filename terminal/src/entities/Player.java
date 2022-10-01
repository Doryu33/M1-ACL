package entities;

import static data.Data.PLAYER_TYPE;
import static data.Data.ROOM_SIZE;

/**
 * Gère le comportement et données du joueur
 */
public final class Player extends Entity {

    public Player(){
        super(PLAYER_TYPE);
        setInitialPosition();
    }

    /**
     * Positionne le joueur à des coordonnées spécifiques.
     * Centré en x. A une case du bord du bas.
     */
    public void setInitialPosition(){
        setX(ROOM_SIZE/2);
        setY(ROOM_SIZE-3);
    }

}
