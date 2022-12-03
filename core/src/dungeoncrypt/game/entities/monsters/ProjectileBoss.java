package dungeoncrypt.game.entities.monsters;

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

import static dungeoncrypt.game.data.Data.*;

public class ProjectileBoss extends Actor {

    private final Sprite sprite;
    private Body body;

    public ProjectileBoss() {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("sprites/entities/monsters/ProjectileBoss.png")));
    }

    /**
     * Création du body pour le projectile
     * @return le bodydef
     */
    public BodyDef createBodyDef(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x*RENDER_SCALE+RENDER_SCALE,(ROOM_SIZE-y)*RENDER_SCALE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(x*RENDER_SCALE,y*RENDER_SCALE,RENDER_SCALE,RENDER_SCALE*2);
        return bodyDef;
    }

    /**
     * Créer la forme du projectile / BodyShape rond 1x1
     * @return la forme
     */
    public FixtureDef createShape(){
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(RENDER_SCALE/2f);
        FixtureDef fixtureCircle = new FixtureDef();
        fixtureCircle.shape = shape;
        fixtureCircle.isSensor = true;
        return fixtureCircle;
    }

    public void move(int horizontalForce, int verticalForce){
        System.out.println("move");
        body.setLinearVelocity(10,10);
        this.sprite.setPosition(body.getPosition().x,body.getPosition().y);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
//        if (isVisible)
            sprite.draw(batch);
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setUserData(ProjectileBoss pb) {
        body.setUserData(pb);
    }

    public void setPos(float v, float v1) {
        this.sprite.setPosition(v,v1);
    }
}
