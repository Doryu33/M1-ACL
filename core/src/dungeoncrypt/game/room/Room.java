package dungeoncrypt.game.room;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dungeoncrypt.game.entities.Monster;
import dungeoncrypt.game.entities.Player;
import dungeoncrypt.game.tiles.Tile;

import java.util.ArrayList;

import static dungeoncrypt.game.data.Data.*;

/**
 * Connait les tuiles et les entitées qui compose la salle
 */
public final class Room {

    private Tile[][] tiles;
    private final ArrayList<Monster> monsters;
    private final Player player;

    private World world;
    private Stage stage;


    /**
     * Constructeur générant le joueur et la liste (vide) des monstres
     * @param tiles salle de base
     */
    public Room(World world,Stage stage, Tile[][] tiles) {
        this.world = world;
        this.tiles = tiles;
        this.player = new Player();
        this.monsters = new ArrayList<>();
        this.stage = stage;
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
    }

    /**
     * Met à jour la position du joueur dans la salle
     * @param actualRoom
     */
    public void updateInputPlayer(Room actualRoom){
        this.player.inputUdapte(actualRoom);
    }

    /**
     * Met à jour la postion des monstres dans la salle
     * @param actualRoom
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
     * Crée les Bodys des tuiles
     */
    public void createBodyTiles(){
        for (int y = ROOM_SIZE-1; y >= 0 ; y--){
            for (int x = 0; x < ROOM_SIZE ; x++){
                Body body = this.world.createBody(tiles[y][x].createBodyDef(x*32+32,y*32+32));
                FixtureDef shape = tiles[y][x].createShape();
                body.createFixture(shape);
                shape.shape.dispose();
                this.stage.addActor(tiles[y][x]);
            }
        }
    }

    /**
     * Crée le Body du joueur
     */
    public void createBodyPlayer(){
        Body body = this.world.createBody(player.createBodyDef());
        player.setBody(body);
        FixtureDef shape = player.createShape();
        body.createFixture(shape);
        shape.shape.dispose();
        this.stage.addActor(player);
    }

    /**
     * Crée les Bodys des monstres
     */
    public void createBodyMonsters(){
        for (Monster monster : monsters){
            Body body = this.world.createBody(monster.createBodyDef());
            monster.setBody(body);
            FixtureDef shape = monster.createShape();
            body.createFixture(shape);
            shape.shape.dispose();
            this.stage.addActor(monster);
        }
    }
}

