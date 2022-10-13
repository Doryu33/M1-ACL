package terminal.room;

/**
 * Gère les différents comportement et données de la salle où le joueur est présent
 */
public final class RoomManager {

    private final RoomGenerator roomGenerator = new RoomGenerator();
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
    public void createNextRoom() {
        this.actualRoom.clearRoom();
        this.actualRoom.setEnvironment(this.roomGenerator.generateRandomRoom());
        this.actualRoom.setMonsters(this.roomGenerator.getGeneratedMonsters());
        this.actualRoom.setInitialPlayerPosition();
    }

    /**
     * Afficher la salle actuelle
     * @return chaine de caractère de la salle actuelle
     */
    public String displayRoom(){
        return actualRoom.toString();
    }

    /**
     * Met à jour les positions dans la salle actuelle
     */
    public void updatePositionRoom(){
        this.actualRoom.updateInputPlayer(this.actualRoom);
        this.actualRoom.updatePositionMonster(this.actualRoom);
    }

    /**
     * Verifie si le joueur utilise la sortie
     */
    public void checkNextRoomIsRequested() {
        if (this.actualRoom.isPlayerOnExit()) {
            this.createNextRoom();
        }

    }
}
