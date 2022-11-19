package dungeoncrypt.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.entities.monsters.Monster;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données communes d'une entitée
 */
public abstract class Entity extends Actor {

    private float x;
    private float y;
    private final String type;
    private Body body;
    protected Sprite sprite;
    //Entier du nombre de point de vie
    private int healthPoint;
    private int timerKnockback;

    //Forces du knockback
    private int knockbackVertical;
    private int knockbackHorizontal;

    private SoundManager soundManager;

    public Entity(String type,int healthPoint,String spritePath){
        this.type = type;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        this.healthPoint = healthPoint;
        this.timerKnockback = 0;
        this.knockbackVertical = 0;
        this.knockbackHorizontal = 0;
        this.soundManager = SoundManager.getInstance();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    /**
     * Redéfinit le nombre de point de vie
     * @param healthPoint le nombre à redéfinir
     */
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    /**
     * Ajouter des points de vie
     * @param healthPoint le nombre à ajouter
     */
    public void addHealthPoint(int healthPoint){
        this.healthPoint += healthPoint;
    }

    /**
     * Enlever des points de vie
     * @param healthPoint le nombre à enlever
     */
    public void subtractHealthPoint(int healthPoint){
        this.healthPoint -= healthPoint;
        soundManager.playDamageEntitySound(getPathDamageSound());
    }

     protected abstract String getPathDamageSound();

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    /**
     * Création des Bodys pour les monstres et du joueur
     * @return le bodydef
     */
    public BodyDef createBodyDef(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getX()*RENDER_SCALE+RENDER_SCALE,(ROOM_SIZE-this.getY())*RENDER_SCALE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(getX()*RENDER_SCALE,getY()*RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
        return bodyDef;
    }

    /**
     * Créer la forme de l'entity / BodyShape rond 1x1
     * @return la forme
     */
    public FixtureDef createShape(boolean isSensor){
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(RENDER_SCALE_ENTITY/2f);

        FixtureDef fixtureCircle = new FixtureDef();
        fixtureCircle.isSensor = isSensor;
        fixtureCircle.shape = shape;

        return fixtureCircle;
    }


    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }

    public int getKnockbackHorizontal() {
        return knockbackHorizontal;
    }

    public int getKnockbackVertical() {
        return knockbackVertical;
    }

    /**
     * Redéfinit les différentes forces du knockback
     * @param knockbackVertical Force vertical du knockback
     * @param knockbackHorizontal Force horizontal du knockback
     */
    public void setknockbackDirection(int knockbackVertical,int knockbackHorizontal){
        this.knockbackVertical = knockbackVertical;
        this.knockbackHorizontal = knockbackHorizontal;
    }

    /**
     * Applique un effet de knockback quand une entitée subit des dégâts
     */
    protected void knockbackMove(){
        int verticalForce = 0;
        int horizontalForce = 0;
        if(knockbackVertical != 0 || knockbackHorizontal != 0){
            getBody().setLinearVelocity(knockbackHorizontal*MOVE_SPEED_MONSTER*(-1f),knockbackVertical*MOVE_SPEED_MONSTER*(-1f));
            if (timerKnockback == MAX_TIMER_KNOCKBACK){
                getBody().setLinearVelocity(horizontalForce*MOVE_SPEED_MONSTER*(-1f),verticalForce*MOVE_SPEED_MONSTER*(-1f));
                this.knockbackVertical = 0;
                this.knockbackHorizontal = 0;
                timerKnockback = 0;
            }else{
                timerKnockback++;
            }
            this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
        }
    }
}
