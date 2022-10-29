package dungeoncrypt.game.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Gère les différents comportement et données de la salle où le joueur est présent
 */
public final class RoomManager {

    private final RoomGenerator roomGenerator = new RoomGenerator();
    private final Room actualRoom;
    private World world;
    private final Stage stage;

    public RoomManager(World world, Stage stage){
        this.world = world;
        this.stage = stage;
        actualRoom = new Room(world, stage, roomGenerator.generateSimpleRoom());
        createNextRoom();
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
        this.actualRoom.setSpecialTileList(this.roomGenerator.getSpecialTileList());
        this.actualRoom.setInitialPlayerPosition();
        this.stage.clear();
        createBodys();
    }

    /**
     * Afficher la salle actuelle en mode terminal
     * @return chaine de caractère de la salle actuelle
     */
    public String displayRoomConsole(){
        return actualRoom.toString();
    }

    /**
     * Met à jour les positions dans la salle actuelle
     */
    public void updatePositionRoom(){
        this.actualRoom.updateInputPlayer(this.actualRoom);
        this.actualRoom.updatePositionMonster(this.actualRoom);
        if(Gdx.input.isKeyPressed(Input.Keys.N)){
            createNextRoom();
        }
    }

    /**
     * Afficher la salle actuelle
     */
    public void createBodys(){
        this.actualRoom.clearBodies();
        this.actualRoom.createBodyTiles();
        this.actualRoom.createBodyPlayer();
        this.actualRoom.createBodyMonsters();
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

