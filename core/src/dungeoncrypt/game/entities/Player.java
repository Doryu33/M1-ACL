package dungeoncrypt.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.room.Room;
import dungeoncrypt.game.weapons.Sword;
import dungeoncrypt.game.weapons.Weapon;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données du joueur
 */
public final class Player extends Entity {

    private int score;
    private int direction;
    private final Weapon weapon;
    private float timeSeconds = 0;
    private final float period = 2f; //Temps que l'arme est affiché

    private Controller controller;
    public static final int BUTTON_A = 0;
    public static final int BUTTON_START = 7;
    public static final int BUTTON_FLECHE_H = 11;
    public static final int BUTTON_FLECHE_B = 12;
    public static final int BUTTON_FLECHE_G = 13;
    public static final int BUTTON_FLECHE_D = 14;

    private boolean buttonPressedA;
    private boolean buttonPressedUp;
    private boolean buttonPressedDown;
    private boolean buttonPressedLeft;
    private boolean buttonPressedRight;


    private boolean isWeaponActive = false;
    protected float timeSecondsWeaponDelay;
    //Entier du nombre de point de bouclier
    private int shieldPoint;

    public Player(){
        super(PLAYER_TYPE,PLAYER_TYPE,PLAYER_INITIAL_HP,"sprites/entities/player/PlayerHaut.png", "sounds/Player_Damage.mp3");
        setInitialPosition();
        this.score = 0;
        this.weapon = new Sword();
        weapon.createBodyDef(weapon.getX(), weapon.getY());
        weapon.createShape();
        this.shieldPoint = MAX_SHIELD;
        this.timeSecondsWeaponDelay = 0f;

        for (Controller controller : Controllers.getControllers()){
            this.controller = controller;
        }
    }

    /**
     * Positionne le joueur à des coordonnées spécifiques.
     * Centré en x. A une case du bord du bas.
     */
    public void setInitialPosition(){
        setX(INITIAL_PLAYER_POS_X);
        setY(INITIAL_PLAYER_POS_Y);
    }

    /**
     * Met à jour la postion du joueur en fonction des touches entrées
     * @param actualRoom salle actuelle
     */
    public void inputUpdate(Room actualRoom) {
        int verticalForce = 0;
        int horizontalForce = 0;
        Body body = getBody();

        if(controller != null){
            buttonPressedA = controller.getButton(BUTTON_A);
            buttonPressedUp = controller.getButton(BUTTON_FLECHE_H);
            buttonPressedDown = controller.getButton(BUTTON_FLECHE_B);
            buttonPressedLeft = controller.getButton(BUTTON_FLECHE_G);
            buttonPressedRight = controller.getButton(BUTTON_FLECHE_D);
        }

        if (!isWeaponActive){
            this.timeSecondsWeaponDelay += Gdx.graphics.getDeltaTime();
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || buttonPressedA) && !isWeaponActive && timeSecondsWeaponDelay > WEAPON_DELAY) {
            this.timeSecondsWeaponDelay = 0f;
            weapon.setVisible(true);
            isWeaponActive = true;
            if (weapon.getBody() != null) {
                clearBodyWeapon(body.getWorld());
            }
            createBodyWeapon(body.getWorld(), getStage());
            SoundManager soundManager = SoundManager.getInstance();
            soundManager.playSound("sounds/Sword_Slash.mp3");

        }else {
            if (Gdx.input.isKeyPressed(Input.Keys.S) || buttonPressedDown) {
                verticalForce = verticalForce - 1;
                direction = 2;
                clearBodyAndResetWeapon();
                this.sprite.setTexture(new Texture(Gdx.files.internal("sprites/entities/player/PlayerBas.png")));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)|| buttonPressedUp) {
                verticalForce = verticalForce + 1;
                direction = 1;
                clearBodyAndResetWeapon();
                this.sprite.setTexture(new Texture(Gdx.files.internal("sprites/entities/player/PlayerHaut.png")));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)|| buttonPressedLeft) {
                horizontalForce = horizontalForce - 1;
                direction = 3;
                clearBodyAndResetWeapon();
                this.sprite.setTexture(new Texture(Gdx.files.internal("sprites/entities/player/PlayerGauche.png")));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)|| buttonPressedRight) {
                horizontalForce = horizontalForce + 1;
                direction = 4;
                clearBodyAndResetWeapon();
                this.sprite.setTexture(new Texture(Gdx.files.internal("sprites/entities/player/PlayerDroite.png")));
            }

            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
                addScore(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.O)) {
                subtractScore(1);
            }
            if (isWeaponActive){
                timeSeconds += Gdx.graphics.getDeltaTime();
            }
            if(timeSeconds > period ){
                timeSeconds -= period;
                if (weapon.getBody() != null){
                    clearBodyWeapon(body.getWorld());
                }
                isWeaponActive =false;
                weapon.setVisible(false);
            }
        }

        if (getKnockBackVertical() != 0 || getKnockBackHorizontal() != 0){
            knockBackMove();
        }else {
            body.setLinearVelocity(horizontalForce * getMovingSpeed(), verticalForce * getMovingSpeed());
        }

        if (weapon.getBody() != null) {
            weapon.getBody().setLinearVelocity(horizontalForce * getMovingSpeed(), verticalForce * getMovingSpeed());
        }
        this.sprite.setPosition(body.getPosition().x-RENDER_SCALE_SPRITE, body.getPosition().y-RENDER_SCALE_SPRITE);

        switch (direction){
            case 1:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE_SPRITE),body.getPosition().y-(RENDER_SCALE_SPRITE)+RENDER_SCALE);
                break;
            case 2:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE_SPRITE),body.getPosition().y-(RENDER_SCALE_SPRITE)-RENDER_SCALE);
                break;
            case 3:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE_SPRITE)-RENDER_SCALE,body.getPosition().y-(RENDER_SCALE_SPRITE));
                break;
            default:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE_SPRITE)+RENDER_SCALE,body.getPosition().y-(RENDER_SCALE_SPRITE));
                break;
        }

    }

    public void addScore(int point){
        this.score += point;
    }

    public void subtractScore(int point){
        this.score -= point;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getDirection() {return direction;}

    public void createBodyWeapon(World world, Stage stage){
        Body body;
        switch (direction){
            case 1:
                body = world.createBody(weapon.createBodyDef(((getBody().getPosition().x) / RENDER_SCALE)-1,((ROOM_SIZE*RENDER_SCALE - getBody().getPosition().y)/ RENDER_SCALE)-1));
                break;
            case 2:
                body = world.createBody(weapon.createBodyDef(((getBody().getPosition().x) / RENDER_SCALE)-1,((ROOM_SIZE*RENDER_SCALE - getBody().getPosition().y)/ RENDER_SCALE)+1));
                break;
            case 3:
                body = world.createBody(weapon.createBodyDef(((getBody().getPosition().x)  / RENDER_SCALE)-2,(ROOM_SIZE*RENDER_SCALE - getBody().getPosition().y)/ RENDER_SCALE));
                break;
            default:
                body = world.createBody(weapon.createBodyDef(((getBody().getPosition().x) / RENDER_SCALE),(ROOM_SIZE*RENDER_SCALE - getBody().getPosition().y)/ RENDER_SCALE));
                break;
        }

        weapon.setBody(body);
        weapon.getBody().setUserData(weapon);
        weapon.setDirection(direction);
        FixtureDef shape = weapon.createShape();
        body.createFixture(shape);
        shape.shape.dispose();
        stage.addActor(weapon);
    }

    public void clearBodyWeapon(World world){
        world.destroyBody(weapon.getBody());
        weapon.setBody(null);
    }

    private void clearBodyAndResetWeapon(){
        if (weapon.getBody() != null){
         clearBodyWeapon(getBody().getWorld());
         isWeaponActive =false;
         weapon.setVisible(false);
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    protected int getMovingSpeed() {
        return MOVE_SPEED_PLAYER;
    }

    /**
     * Ajouter des points de bouclier
     * @param shieldPoint le nombre à ajouter
     */
    public void addShieldPoint(int shieldPoint){
        if (this.shieldPoint + shieldPoint >= MAX_SHIELD ){
            this.shieldPoint = MAX_SHIELD;
        }else {
            this.shieldPoint += shieldPoint;
        }
    }

    /**
     * Retire des points de bouclier si il en reste au joueur sinon retire des points de vie au joueur
     * @param damage le nombre de dégâts subis par le joueur
     */
    public void takeDamage(int damage){
        if(shieldPoint > 0){
            shieldPoint = shieldPoint - damage;
            if (shieldPoint < 0){
                damage = shieldPoint * (-1);
                subtractHealthPoint(damage);
                shieldPoint = 0;
            } else {
                SoundManager.getInstance().playSound("sounds/Shield_Damage.mp3");
            }
        }else{
            subtractHealthPoint(damage);
        }
    }

    public int getShieldPoint(){
        return shieldPoint;
    }

    public boolean isWeaponActive() {
        return isWeaponActive;
    }

    /**
     * Retourne le statut du cooldown de l'épée (utile pour l'affichage)
     * @return int statut
     */
    public int getCooldownSwordStatut(){
        int statut = 1;
        if (timeSecondsWeaponDelay > 0 && timeSecondsWeaponDelay < WEAPON_DELAY*((1/5f)*1)){
            statut = 2;
        }
        else if (timeSecondsWeaponDelay > WEAPON_DELAY*((1/5f)*1) && timeSecondsWeaponDelay < WEAPON_DELAY*((1/5f)*2)){
            statut = 3;
        }
        else if (timeSecondsWeaponDelay > WEAPON_DELAY*((1/5f)*2) && timeSecondsWeaponDelay < WEAPON_DELAY*((1/5f)*3)){
            statut = 4;
        }
        else if (timeSecondsWeaponDelay > WEAPON_DELAY*((1/5f)*3) && timeSecondsWeaponDelay < WEAPON_DELAY*((1/5f)*4)){
            statut = 5;
        }
        else if (timeSecondsWeaponDelay > WEAPON_DELAY*((1/5f)*4) && timeSecondsWeaponDelay < WEAPON_DELAY){
            statut = 6;
        }
        else if (timeSecondsWeaponDelay > WEAPON_DELAY){
            statut = 7;
        }
        return statut;
    }
}
