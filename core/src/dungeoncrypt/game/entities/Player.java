package dungeoncrypt.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private final float period = 2f;
    private boolean isWeaponActive = false;

    public Player(){
        super(PLAYER_TYPE,PLAYER_TYPE,PLAYER_INITIAL_HP,"sprites/entities/player/isaac.png", "sounds/Player_Damage.mp3");
        setInitialPosition();
        this.score = 0;
        this.weapon = new Sword();
        weapon.createBodyDef(weapon.getX(), weapon.getY());
        weapon.createShape();
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isWeaponActive) {
            weapon.setVisible(true);
            isWeaponActive = true;
            if (weapon.getBody() != null) {
                clearBodyWeapon(body.getWorld());
            }
            createBodyWeapon(body.getWorld(), getStage());

        }else {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                verticalForce = verticalForce - 1;
                direction = 2;
                clearBodyAndResetWeapon();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                verticalForce = verticalForce + 1;
                direction = 1;
                clearBodyAndResetWeapon();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                horizontalForce = horizontalForce - 1;
                direction = 3;
                clearBodyAndResetWeapon();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                horizontalForce = horizontalForce + 1;
                direction = 4;
                clearBodyAndResetWeapon();
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
        this.sprite.setPosition(body.getPosition().x-(RENDER_SCALE), body.getPosition().y-(RENDER_SCALE));

        switch (direction){
            case 1:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE),body.getPosition().y-(RENDER_SCALE)+RENDER_SCALE);
                break;
            case 2:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE),body.getPosition().y-(RENDER_SCALE)-RENDER_SCALE);
                break;
            case 3:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE)-RENDER_SCALE,body.getPosition().y-(RENDER_SCALE));
                break;
            default:
                this.weapon.getSprite().setPosition(body.getPosition().x-(RENDER_SCALE)+RENDER_SCALE,body.getPosition().y-(RENDER_SCALE));
                break;
        }

    }

    public void addScore(int point){
        this.score += point;
        System.out.println("getHealthPoint() = " + getHealthPoint());
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
}
