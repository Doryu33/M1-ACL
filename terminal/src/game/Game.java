package game;

import room.RoomManager;

/**
 * Permet de lancer le jeu
 */
public final class Game {

    public static void main(String[] args) {
        RoomManager rm = new RoomManager();
        System.out.println(rm.displayRoom());
    }
}
