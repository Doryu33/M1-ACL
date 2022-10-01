package room;

import tiles.Exit;
import tiles.Floor;
import tiles.Tile;
import tiles.Wall;

import static data.Data.ROOM_SIZE;

/**
 * Génère des salles soit par génération fixe soit aléatoirement
 */
public final class RoomGenerator {

    public RoomGenerator(){

    }

    /**
     * Génère une salle simple utilisée pour la première salle de chaque étage.
     * Une salle vide entourée de mur, le joueur est positionné au centre, la sortie est en haut au centre.
     * @return le tableau des éléments de la salle
     */
    public Tile[][] generateSimpleRoom(){
        Tile[][] tiles = new Tile[ROOM_SIZE][ROOM_SIZE];

        for (int y = 0; y < ROOM_SIZE; y++) {
            for (int x = 0; x < ROOM_SIZE; x++) {
                //Lorsque l'on est en haut au centre
                if(x == ROOM_SIZE/2 && y == 0){
                    tiles[y][x] = new Exit();
                }
                //Lorsque l'on est sur les bords
                else if(y == 0 || y == ROOM_SIZE-1 || x == 0 || x == ROOM_SIZE-1){
                    tiles[y][x] = new Wall();
                }else{
                    tiles[y][x] = new Floor();
                }
            }
        }
        return tiles;
    }
}
