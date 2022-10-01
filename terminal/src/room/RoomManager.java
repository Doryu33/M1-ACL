package room;

/**
 * Gère les différents comportement et données de la salle où le joueur est présent
 */
public final class RoomManager {

    private final RoomGenerator roomGenerator = new RoomGenerator();
    private final Collision collision = new Collision();
    private final Room actualRoom;

    public RoomManager(){
        actualRoom = new Room(roomGenerator.generateSimpleRoom());
    }

    /**
     * Lorsque le joueur a atteint la fin d'un étage, il passe à l'étage suivant.
     * Une nouvelle salle simple est générée
     */
    public void createNewLevel(){
        actualRoom.setEnvironment(roomGenerator.generateSimpleRoom());
    }

    /**
     * Lorsque le joueur a tué tous les monstres et qu'il touche la tuile Sortie, il entre dans une nouvelle salle
     */
    public void createNextRoom(){

    }

    /**
     * Afficher la salle actuelle
     * @return chaine de caractère de la salle actuelle
     */
    public String displayRoom(){
        return actualRoom.toString();
    }
}
