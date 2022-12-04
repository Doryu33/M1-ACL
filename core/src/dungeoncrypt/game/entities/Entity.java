package dungeoncrypt.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dungeoncrypt.game.data.SoundManager;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données communes d'une entitée
 */
public abstract class Entity extends Actor {

    private float x;
    private float y;
    private final String type;
    private final String specialType;
    private final String damageSoundPath;
    private Body body;
    protected Sprite sprite;
    //Entier du nombre de point de vie
    private int healthPoint;
    private final int initialHealthPoint;
    private int timerKnockback;

    //Forces du knockback
    private int knockBackVertical;
    private int knockBackHorizontal;

    private final SoundManager soundManager;

    public Entity(String type, String specialType, int healthPoint, String spritePath, String damageSoundPath){
        this.type = type;
        this.specialType = specialType;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        this.healthPoint = healthPoint;
        this.initialHealthPoint = healthPoint;
        this.damageSoundPath = damageSoundPath;
        this.timerKnockback = 0;
        this.knockBackVertical = 0;
        this.knockBackHorizontal = 0;
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
     * @param hpToAdd le nombre à ajouter
     */
    public void addHealthPoint(int hpToAdd){
        if(specialType.equals(PLAYER_TYPE)){
            if(healthPoint+hpToAdd > initialHealthPoint){
                this.healthPoint = initialHealthPoint;
            }else{
                this.healthPoint += hpToAdd;
            }
        }else{
            this.healthPoint += hpToAdd;
        }
    }

    /**
     * Enlever des points de vie
     * @param healthPoint le nombre à enlever
     */
    public void subtractHealthPoint(int healthPoint){
        this.healthPoint -= healthPoint;
        soundManager.playDamageEntitySound(damageSoundPath);
    }

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
        bodyDef.position.set(this.getX()*RENDER_SCALE+RENDER_SCALE_SPRITE,(ROOM_SIZE-this.getY())*RENDER_SCALE+RENDER_SCALE_SPRITE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(getX()*RENDER_SCALE+RENDER_SCALE,getY()*RENDER_SCALE+RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
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

    /**
     * Création des Bodys pour les monstres et du joueur
     * @return le bodydef
     */
    public BodyDef createBodyDefBoss(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getX()*RENDER_SCALE+RENDER_SCALE,(ROOM_SIZE-this.getY())*RENDER_SCALE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(this.getX()*RENDER_SCALE,getY()*RENDER_SCALE,RENDER_SCALE_BOSS+RENDER_SCALE,RENDER_SCALE_BOSS);
        return bodyDef;
    }

    /**
     * Créer la forme du boss / BodyShape rond 2x2
     * @return la forme
     */
    public FixtureDef createShapeBoss(){
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius((RENDER_SCALE_BOSS*3/4f)/2f);

        FixtureDef fixtureCircle = new FixtureDef();
        fixtureCircle.isSensor = false;
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

    public String getSpecialType() {
        return specialType;
    }

    public int getKnockBackHorizontal() {
        return knockBackHorizontal;
    }

    public int getKnockBackVertical() {
        return knockBackVertical;
    }

    /**
     * Redéfinit les différentes forces du knockback
     * @param knockBackVertical Force vertical du knockback
     * @param knockBackHorizontal Force horizontal du knockback
     */
    public void setKnockBackDirection(int knockBackVertical, int knockBackHorizontal){
        this.knockBackVertical = knockBackVertical;
        this.knockBackHorizontal = knockBackHorizontal;
    }

    public void applyKnockBackToPlayer(float monsterX, float monsterY){

        float playerPosX = (getBody().getPosition().x-(RENDER_SCALE/2f))/RENDER_SCALE;
        float playerPosY = ROOM_SIZE-1 - (getBody().getPosition().y-(RENDER_SCALE/2f))/RENDER_SCALE;

        float monsterPosX = (monsterX-(RENDER_SCALE/2f))/RENDER_SCALE;
        float monsterPosY = ROOM_SIZE-1 - (monsterY-(RENDER_SCALE/2f))/RENDER_SCALE;

        int verticalForce = 0;
        int horizontalForce = 0;

        float differenceY = Math.abs(playerPosY-monsterPosY);
        float differenceX = Math.abs(playerPosX-monsterPosX);

        if(monsterPosY > playerPosY && differenceY >= 0.5f) {
            verticalForce = verticalForce - 3;
        }else if (monsterPosY < playerPosY && differenceY >= 0.5f) {
            verticalForce = verticalForce + 3;
        }

        if (monsterPosX > playerPosX && differenceX >= 0.5f) {
            horizontalForce = horizontalForce + 3;
        } else if (monsterPosX < playerPosX && differenceX >= 0.5f){
            horizontalForce = horizontalForce - 3;
        }
        setKnockBackDirection(verticalForce,horizontalForce);
    }

    public void applyKnockBackToMonster(int knockBackDirection){
        int verticalForce = 0;
        int horizontalForce = 0;

        if (knockBackDirection == 2) {
            verticalForce = verticalForce + 2;
        }
        if (knockBackDirection == 1) {
            verticalForce = verticalForce - 2;
        }
        if (knockBackDirection == 3) {
            horizontalForce = horizontalForce + 2;
        }
        if (knockBackDirection == 4) {
            horizontalForce = horizontalForce - 2;
        }
        setKnockBackDirection(verticalForce,horizontalForce);
    }

    /**
     * Applique un effet de knockback quand une entitée subit des dégâts
     */
    protected void knockBackMove(){
        int verticalForce = 0;
        int horizontalForce = 0;
        if(knockBackVertical != 0 || knockBackHorizontal != 0){
            getBody().setLinearVelocity(knockBackHorizontal*getMovingSpeed()*(-1f),knockBackVertical*getMovingSpeed()*(-1f));
            if (timerKnockback == MAX_TIMER_KNOCKBACK){
                getBody().setLinearVelocity(horizontalForce*getMovingSpeed()*(-1f),verticalForce*getMovingSpeed()*(-1f));
                this.knockBackVertical = 0;
                this.knockBackHorizontal = 0;
                timerKnockback = 0;
            }else{
                timerKnockback++;
            }
            this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
        }
    }

    protected abstract int getMovingSpeed();

    public boolean hasFullHealth() {
        return healthPoint == initialHealthPoint;
    }
}
