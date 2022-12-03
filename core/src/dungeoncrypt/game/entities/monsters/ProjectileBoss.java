package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static dungeoncrypt.game.data.Data.*;

public class ProjectileBoss extends Actor {

    private final Sprite sprite;
    private final int horizontalForce;
    private final int verticalForce;
    private Body body;
    private boolean isOffBoundaries = false;
    private World world;
    private boolean isVisible = true;

    public ProjectileBoss(int horizontalForce, int verticalForce) {
        this.horizontalForce = horizontalForce;
        this.verticalForce = verticalForce;
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

    public void move(){
        body.setLinearVelocity(horizontalForce*MOVE_SPEED_BOSS_PROJECTILE,verticalForce*MOVE_SPEED_BOSS_PROJECTILE);
    }

    public void updateSpritePosition(){
        this.sprite.setPosition(body.getPosition().x,body.getPosition().y);
    }

    /**
     * Vérifier si le projectile est en dehors de la salle
     * Une marge de 20 est ajouté pour s'assurer qu'il est bien invisible pour le joueur
     * @return l'objet si en dehors
     */
    public ProjectileBoss checkIfOffLimit() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        if(x < -20 || x > PIXEL_ROOM_SIZE+20 || y < -20 || y > PIXEL_ROOM_SIZE+20){
            isVisible = false;
            return this;
        }
        return null;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        if(isVisible){
            sprite.draw(batch);
        }
    }

    public void setBody(Body body) {
        this.body = body;
        this.world = body.getWorld();
    }

    public void setUserData(ProjectileBoss pb) {
        body.setUserData(pb);
    }

    public Body getBody() {
        return body;
    }
}
