package terminal.entities;

import terminal.room.Room;

import java.util.Scanner;
import static terminal.data.Data.*;

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
        setX(INITIAL_PLAYER_POS_X);
        setY(INITIAL_PLAYER_POS_Y);
    }

    /**
     * Met à jour la postion du joueur en fonction des touches entrées
     * @param actualRoom
     */
    public void inputUdapte(Room actualRoom) {
        Scanner scanner = new Scanner(System.in);
        String keyPressed = scanner.next();
        if(keyPressed.equals(PLAYER_TOP_KEY) || keyPressed.equals(PLAYER_TOP_KEY_MIN)) {
            if(actualRoom.isTileWalkable(getX(),getY()-1)){
                if(!actualRoom.thereIsAMonster(getX(),getY()-1)){
                    setY(getY()-1);
                }
            }
        } else if (keyPressed.equals(PLAYER_BOTTOM_KEY) || keyPressed.equals(PLAYER_BOTTOM_KEY_MIN)) {
            if(actualRoom.isTileWalkable(getX(),getY()+1)){
                if(!actualRoom.thereIsAMonster(getX(),getY()+1)){
                    setY(getY()+1);
                }
            }
        } else if (keyPressed.equals(PLAYER_LEFT_KEY) || keyPressed.equals(PLAYER_LEFT_KEY_MIN)) {
            if(actualRoom.isTileWalkable(getX()-1,getY())){
                if(!actualRoom.thereIsAMonster(getX()-1,getY())){
                    setX(getX()-1);
                }
            }
        } else if (keyPressed.equals(PLAYER_RIGHT_KEY) || keyPressed.equals(PLAYER_RIGHT_KEY_MIN)) {
            if(actualRoom.isTileWalkable(getX()+1,getY())){
                if(!actualRoom.thereIsAMonster(getX()+1,getY())){
                    setX(getX()+1);
                }
            }
        }
    }

}
