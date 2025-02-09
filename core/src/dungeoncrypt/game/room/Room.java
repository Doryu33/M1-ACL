package dungeoncrypt.game.room;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import dungeoncrypt.game.entities.monsters.Boss;
import dungeoncrypt.game.entities.monsters.Monster;
import dungeoncrypt.game.entities.Player;
import dungeoncrypt.game.tiles.Tile;
import dungeoncrypt.game.tiles.special.Exit;
import dungeoncrypt.game.tiles.special.SpecialTile;

import java.util.ArrayList;

import static dungeoncrypt.game.data.Data.*;

/**
 * Connait les tuiles et les entitées qui compose la salle
 */
public final class Room {

    private Tile[][] tiles;
    private final ArrayList<Monster> monsters;
    private final Player player;
    private final ArrayList<SpecialTile> specialTileList;
    private final World world;
    private final Stage stage;

    /**
     * Constructeur générant le joueur et la liste (vide) des monstres
     * @param tiles salle de base
     */
    public Room(World world,Stage stage, Tile[][] tiles) {
        this.world = world;
        this.tiles = tiles;
        this.player = new Player();
        this.monsters = new ArrayList<>();
        this.specialTileList = new ArrayList<>();
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
     * Genere un tableau de char representant la salle actuelle.
     * @return un tableau de char.
     */
    public char[][] getRoomComposition() {
        char[][] room = new char[ROOM_SIZE][ROOM_SIZE];
        for (int y = 0; y < ROOM_SIZE; y++) {
            for (int x = 0; x < ROOM_SIZE; x++) {
                    switch (tiles[y][x].getType()){
                        case EXIT_TYPE:
                            room[y][x] = EXIT_TILE;
                            break;
                        case WALL_TYPE:
                            room[y][x] = WALL_TILE;
                            break;
                        case FLOOR_TYPE:
                            room[y][x] = FLOOR_TILE;
                            break;
                        case TRAP_TYPE:
                            room[y][x] = TRAP_TILE;
                            break;
                        case HEALING_TILE_TYPE:
                            room[y][x] = HEALING_TILE;
                            break;
                    }
                }
        }
        return room;
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

    public Exit getExit(){
        if(specialTileList.get(0).getType().equals(EXIT_TYPE)){
            return (Exit) specialTileList.get(0);
        }
        return null;
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
        this.player.inputUpdate(actualRoom);
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
     * Permet de tuer un monstre. Le retire de la salle et supprime son affichage + ses collisions
     * @param monster à tuer
     */
    public void killMonster(Monster monster){
        if (monsters.contains(monster)){
            if(monster.getSpecialType().equals(BOSS_TYPE)){
                Boss boss = (Boss) monster;
                boss.deleteAllProjectiles();
            }
            monsters.remove(monster);
            //System.out.println("MONSTRE MORT");
            monster.setVisible(false);
            player.addScore(monster.getScore());
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
     * Set la position du joueur a des coordonnées précises
     * @param x pos X
     * @param y pos Y
     */
    public void setPlayerPosition(float x, float y){
        this.player.setX(x);
        this.player.setY(y);
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

    /**
     * Crée les Bodies des tuiles
     */
    public void createBodyTiles(){
        int yPos = ROOM_SIZE*RENDER_SCALE;
        Body body;
        for (int y = 0; y < ROOM_SIZE ; y++){
            for (int x = 0; x < ROOM_SIZE ; x++){
                if (tiles[y][x].getType().equals(FLOOR_TYPE)) {
                    body = this.world.createBody(tiles[y][x].createBodyDef(x * RENDER_SCALE + RENDER_SCALE, yPos, false));
                }
                else {
                    body = this.world.createBody(tiles[y][x].createBodyDef(x * RENDER_SCALE + RENDER_SCALE, yPos, true));
                }
                body.setUserData(tiles[y][x]);
                FixtureDef shape = tiles[y][x].createShape();
                body.createFixture(shape);
                shape.shape.dispose();
                this.stage.addActor(tiles[y][x]);
            }
            yPos-=RENDER_SCALE;
        }
    }

    /**
     * Crée le Body du joueur
     */
    public void createBodyPlayer(){
        Body body = this.world.createBody(player.createBodyDef());
        player.setBody(body);
        player.getBody().setUserData(player);
        FixtureDef shape = player.createShape(false);
        body.createFixture(shape);
        shape.shape.dispose();
        this.stage.addActor(player);
        this.stage.addActor(player.getWeapon());
    }

    /**
     * Crée les Bodies des monstres
     */
    public void createBodyMonsters(){
        Body body;
        FixtureDef shape;
        for (Monster monster : monsters){
            if(monster.getSpecialType().equals(BOSS_TYPE)){
                body = this.world.createBody(monster.createBodyDefBoss());
            }else{
                body = this.world.createBody(monster.createBodyDef());
            }
            monster.setBody(body);
            monster.getBody().setUserData(monster);
            if (monster.getSpecialType().equals(GHOST_TYPE)){
                shape = monster.createShape(true);
            }else if(monster.getSpecialType().equals(BOSS_TYPE)){
                shape = monster.createShapeBoss();
            } else{
                shape = monster.createShape(false);
            }

            body.createFixture(shape);
            shape.shape.dispose();
            this.stage.addActor(monster);
        }
    }

    /**
     * Getter des PV du joueur.
     * @return les PV du joueur.
     */
    public int getPlayerHP(){ return player.getHealthPoint();}

    /**
     * Setter des PV du joueur.
     * @param hp la nouvelle valeur de PV du joueur.
     */
    public void setPlayerHP(int hp){
        this.player.setHealthPoint(hp);
    }

    /**
     * Getter du score du joueur
     * @return le score du joueur
     */
    public int getPlayerScore(){return player.getScore();}

    /**
     * Setter du score du joueur
     * @param score le nouveau score du joueur
     */
    public void setPlayerScore(int score){
        this.player.setScore(score);
    }

    public float getPlayerPosX(){
        return player.getBody().getPosition().x;
    }

    public float getPlayerPosY(){
        return player.getBody().getPosition().y;
    }

    /**
     * Détruit tous les body du monde. Les tuiles, le joueur et les monstres sont détruites.
     */
    public void clearBodies(){
        if(!world.isLocked()){
            Array<Body> list = new Array<>();
            world.getBodies(list);
            int nb = world.getBodyCount();
            for (int i = 0; i < nb; i++) {
                world.destroyBody(list.get(i));
            }
        }
    }

    /**
     * Savoir si la salle est vide, càd s'il n'y a plus de monstre en vie
     * @return vrai si plus aucun monstre vivant
     */
    public boolean isEmpty() {
        return monsters.size() == 0;
    }

    public Boss getBoss() {
        for (Monster m: monsters) {
            if(m.getSpecialType().equals(BOSS_TYPE)){
                return (Boss) m;
            }
        }
        return null;
    }

    /**
     * Retourne le nombre de point de bouclier du joueur
     * @return int point de bouclier du joueur
     */
    public int getPlayerShield(){
        return player.getShieldPoint();
    }

    /**
     * Retourne le statut du cooldown de l'épée
     * @return int statut
     */
    public int getPlayerSwordCooldownStatut(){
        return player.getCooldownSwordStatut();
    }

    public void setShieldPlayer(int shield){
        player.addShieldPoint(shield);
    }

    public boolean isPlayerWeaponActive() {
        return player.isWeaponActive();
    }

    public Controller getController(){
        return this.player.getController();
    }
}

