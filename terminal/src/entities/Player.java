package entities;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import static data.Data.*;

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

    /**
     * Met à jour la postion du joueur en fonction des touches entrées
     */
    public void inputUdapte() {
        Scanner scanner = new Scanner(System.in);
        String keyPressed = scanner.next();
        if(keyPressed.equals(PLAYER_TOP_KEY) || keyPressed.equals(PLAYER_TOP_KEY_MIN)) {
            setY(getY()-1);
        } else if (keyPressed.equals(PLAYER_BOTTOM_KEY) || keyPressed.equals(PLAYER_BOTTOM_KEY_MIN)) {
            setY(getY()+1);
        } else if (keyPressed.equals(PLAYER_LEFT_KEY) || keyPressed.equals(PLAYER_LEFT_KEY_MIN)) {
            setX(getX()-1);
        } else if (keyPressed.equals(PLAYER_RIGHT_KEY) || keyPressed.equals(PLAYER_RIGHT_KEY_MIN)) {
            setX(getX()+1);
        }
    }

}
