package game;

import room.RoomManager;

import java.io.IOException;

/**
 * Permet de lancer le jeu
 */
public final class Game {

    public static void main(String[] args) {
        RoomManager rm = new RoomManager();
        System.out.println(rm.displayRoom());
        boolean inGame = true;
        //Boucle de jeu
        while(inGame){
            rm.updatePositionRoom();
            rm.checkNextRoomIsRequested();
            System.out.println(rm.displayRoom());
        }

    }
}
