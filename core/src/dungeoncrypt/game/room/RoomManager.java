package dungeoncrypt.game.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dungeoncrypt.game.tiles.special.Exit;
import dungeoncrypt.game.tools.SaveManager;

import static dungeoncrypt.game.data.Data.DEBUG_SAVE_NAME;
import dungeoncrypt.game.data.SoundManager;

/**
 * Gère les différents comportement et données de la salle où le joueur est présent
 */
public final class RoomManager {

    private final RoomGenerator roomGenerator;
    private final Room actualRoom;
    private World world;
    private final Stage stage;
    private final SaveManager saveManager;

    /**
     * Constructeur par défaut. Instancie une nouvelle partie.
     * @param world Le monde du jeu
     * @param stage Le stage du jeu
     */
    public RoomManager(World world, Stage stage){
        this.world = world;
        this.stage = stage;
        this.saveManager = new SaveManager();
        this.roomGenerator = new RoomGenerator(this);
        actualRoom = new Room(world, stage, roomGenerator.generateSimpleRoom());
        createNextRoom();
    }

    /**
     * Constructeur via sauvegarde. Instancie une partie en se basant sur l'état d'une sauvegarde.
     * @param world Le monde du jeu.
     * @param stage Le stage du jeu.
     * @param save La sauvegarde du jeu a restaurée.
     */
    public RoomManager(World world, Stage stage, SaveManager save){
        this.saveManager = save;
        this.world = world;
        this.stage = stage;
        this.roomGenerator = new RoomGenerator(this);
        actualRoom = new Room(world, stage, roomGenerator.generateSimpleRoom());
        loadRoom(this.saveManager);
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
        createBodies();
    }

    /**
     * Permet de créer une salle depuis une sauvegarde afin de restaurer l'état de la partie.
     * @param save
     */
    public void loadRoom(SaveManager save){
        this.actualRoom.clearRoom();
        this.actualRoom.setEnvironment(this.roomGenerator.generateLoadedRoom(save.tilesList));
        this.actualRoom.setSpecialTileList(this.roomGenerator.getSpecialTileList());
        this.actualRoom.setInitialPlayerPosition();
        this.actualRoom.setPlayerHP(save.PlayerHealth);
        this.actualRoom.setPlayerScore(save.PlayerScore);
        this.stage.clear();
        createBodies();
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            this.saveManager.saveProgression(DEBUG_SAVE_NAME,this.actualRoom);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            this.saveManager.loadProgression(DEBUG_SAVE_NAME);
            this.loadRoom(this.saveManager);
        }
    }

    /**
     * Afficher la salle actuelle
     */
    public void createBodies(){
        this.actualRoom.clearBodies();
        this.actualRoom.createBodyTiles();
        this.actualRoom.createBodyPlayer();
        this.actualRoom.createBodyMonsters();
    }

    public Room getActualRoom() {
        return actualRoom;
    }

    public void checkRoomIsEmpty() {
        if (actualRoom.isEmpty()){
            Exit exit = actualRoom.getExit();
            if(exit != null){
                exit.setOpen();
            }
        }
    }
}

