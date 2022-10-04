package room;

import entities.Monster;
import entities.Player;
import tiles.Tile;
import java.util.ArrayList;

import static data.Data.*;

/**
 * Connait les tuiles et les entitées qui compose la salle
 */
public final class Room {

    private Tile[][] tiles;
    private final ArrayList<Monster> monsters;
    private final Player player;

    /**
     * Constructeur générant le joueur et la liste (vide) des monstres
     * @param tiles salle de base
     */
    public Room(Tile[][] tiles) {
        this.tiles = tiles;
        this.player = new Player();
        this.monsters = new ArrayList<>();
    }

    /**
     * Affiche la salle avec les entitées.
     * M pour monstre, P pour joueur
     * W pour mur, F pour sol et E pour sortie
     * @return la chaine de caractère de la salle
     */
    public String toString(){
        StringBuilder sb = new StringBuilder(ROOM_SIZE*ROOM_SIZE);
        for (int y = 0; y < ROOM_SIZE; y++) {
            for (int x = 0; x < ROOM_SIZE; x++) {
                if(thereIsAMonster(x,y)){
                    sb.append(MONSTER_TILE);
                }
                else if(player.getX() == x && player.getY() == y){
                    sb.append(PLAYER_TILE);
                }else{
                    switch (tiles[y][x].getType()){
                        case WALL_TYPE:
                            sb.append(WALL_TILE);
                            break;
                        case FLOOR_TYPE:
                            sb.append(FLOOR_TILE);
                            break;
                        case EXIT_TYPE:
                            sb.append(EXIT_TILE);
                            break;
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Savoir si à la position x,y un monstre est présent. Retourne vrai s'il y en a un
     * @param x position x
     * @param y position y
     * @return vrai si présent, faux sinon
     */
    private boolean thereIsAMonster(int x, int y) {
        for (Monster m:monsters) {
            if (m.getX() == x && m.getY() == y){
                return true;
            }
        }
        return false;
    }

    /**
     * Définit l'environnement de la salle (murs, sols, sortie et pièges)
     * @param generatedRoom environnement de la salle à définir
     */
    public void setEnvironment(Tile[][] generatedRoom) {
        tiles = generatedRoom;
    }

    /**
     * Vider la salle des entitées et repositionner le joueur à sa position de base
     */
    public void clearRoom(){
        monsters.clear();
        player.setInitialPosition();
    }

    /**
     * Met à jour la position du joueur dans la salle
     */
    public void updateInputPlayer(){
        this.player.inputUdapte();
    }

    /**
     * Met à jour la postion des monstres dans la salle
     */
    public void updatePositionMonster(){
        for (Monster m:monsters) {
            m.updatePosition();
        }
    }
}
