package dungeoncrypt.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dungeoncrypt.game.entities.Entity;

import static dungeoncrypt.game.data.Data.*;

public abstract class Weapon extends Actor {

    private float x;
    private float y;
    private String type;
    protected Sprite sprite;
    private Body body;
    private int damage;
    private boolean isVisible = false;

    public Weapon(String type,int damage,String spritePath){
        this.type = type;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        this.damage = damage;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setPositionWeapon(float x,float y){
        setX(x);
        setY(y);
    }

    /**
     * Création des Bodys pour l'arme
     * @return le bodydef
     */
    public BodyDef createBodyDef(float x,float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x*RENDER_SCALE+RENDER_SCALE,(ROOM_SIZE-y)*RENDER_SCALE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(x*RENDER_SCALE,y*RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
        return bodyDef;
    }

    /**
     * Créer la forme de l'arme / BodyShape rond 1x1
     * @return la forme
     */
    public FixtureDef createShape(){
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(RENDER_SCALE_ENTITY/2f);
        FixtureDef fixtureCircle = new FixtureDef();

        fixtureCircle.shape = shape;
        fixtureCircle.isSensor = true;
        return fixtureCircle;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamageTo(Entity entity){
        entity.subtractHealthPoint(damage);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public void setVisible(boolean visible) {
        isVisible = visible;
    }



    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        if (isVisible)
            sprite.draw(batch);
    }
}
