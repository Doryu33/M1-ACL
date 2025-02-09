package terminal.room;

import terminal.entities.Monster;
import terminal.entities.Player;
import terminal.tiles.Tile;
import terminal.tiles.special.SpecialTile;

import java.util.ArrayList;

import static terminal.data.Data.*;

/**
 * Connait les tuiles et les entitées qui compose la salle
 */
public final class Room {

    private Tile[][] tiles;
    private final ArrayList<Monster> monsters;
    private final Player player;
    private final ArrayList<SpecialTile> specialTileList;

    /**
     * Constructeur générant le joueur et la liste (vide) des monstres
     * @param tiles salle de base
     */
    public Room(Tile[][] tiles) {
        this.tiles = tiles;
        this.player = new Player();
        this.monsters = new ArrayList<>();
        this.specialTileList = new ArrayList<>();
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
                        case EXIT_TYPE:
                            sb.append(EXIT_TILE);
                            break;
                        case WALL_TYPE:
                            sb.append(WALL_TILE);
                            break;
                        case FLOOR_TYPE:
                            sb.append(FLOOR_TILE);
                            break;
                        case TRAP_TYPE:
                            sb.append(TRAP_TILE);
                            break;
                        case HEALING_TILE_TYPE:
                            sb.append(HEALING_TILE);
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
    public boolean thereIsAMonster(int x, int y) {
        boolean res = false;
        for (Monster m:monsters) {
            if (m.getX() == x && m.getY() == y) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Savoir s'il est possible de marcher sur la case à la position x,y.
     * @param x position x
     * @param y position y
     * @return vrai si possible, faux sinon
     */
    public boolean isTileWalkable(int x, int y){
        return this.tiles[y][x].isWalkable();
    }

    /**
     * Savoir si le joueur se situe à cette position (x,y)
     * @param x position x
     * @param y position y
     * @return vrai si le joueur est ici, faux sinon
     */
    public boolean isPlayerHere(int x, int y){
        boolean res = false;
        if(player.getX() == x && player.getY() == y){
            res = true;
        }
        return res;
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
        specialTileList.clear();
    }

    /**
     * Met à jour la position du joueur dans la salle
     * @param actualRoom salle actuelle
     */
    public void updateInputPlayer(Room actualRoom){
        this.player.inputUdapte(actualRoom);
    }

    /**
     * Met à jour la postion des monstres dans la salle
     * @param actualRoom salle actuelle
     */
    public void updatePositionMonster(Room actualRoom){
        for (Monster m:monsters) {
            m.updatePosition(actualRoom);
        }
    }

    /**
     * Verifie si les positions du joueur sont sur celle de la sortie
     * @return un boolean
     */
    public boolean isPlayerOnExit() {
        return this.player.getX() == EXIT_POS_X && this.player.getY() == EXIT_POS_Y;
    }

    /**
     * set les positions du joueur a celle initiale
     */
    public void setInitialPlayerPosition() {
        this.player.setInitialPosition();
    }

    /**
     * Définir les monstres de la salle à partir de la liste générée par RoomGenerator
     * @param generatedMonsters liste des monstres de la salle
     */
    public void setMonsters(ArrayList<Monster> generatedMonsters) {
        monsters.addAll(generatedMonsters);
    }

    /**
     * Définit la liste de toutes les tuiles spéciales de la salle
     * @param specialTileList liste des tuiles spéciales
     */
    public void setSpecialTileList(ArrayList<SpecialTile> specialTileList) {
        this.specialTileList.addAll(specialTileList);
    }
}
