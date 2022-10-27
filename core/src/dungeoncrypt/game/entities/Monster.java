package dungeoncrypt.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dungeoncrypt.game.room.Room;

import java.util.Random;

import static dungeoncrypt.game.data.Data.MONSTER_TYPE;
import static dungeoncrypt.game.data.Data.RENDER_SCALE;


/**
 * Gère le comportement et données d'un monstre
 */
public final class Monster extends Entity {

    private int timer;

    private int horizontalForce;
    private int verticalForce;

    public Monster(int x, int y) {
        super(MONSTER_TYPE);
        setX(x);
        setY(y);
        this.timer = 0;
        this.horizontalForce = 0;
        this.verticalForce = 0;
        this.sprite = new Sprite(new Texture(Gdx.files.internal("sprites/monster.png")));
    }

    /**
     * Met à jour la position du monstre en fonction d'un nombre aléatoire entre 0 compris et 4 non compris
     * @param actualRoom
     */
    public void updatePosition(Room actualRoom){
        if(timer == 0){
            Random random = new Random();
            int randomDirection = random.nextInt(4);
            this.verticalForce = 0;
            this.horizontalForce = 0;
            if(randomDirection == 0) {
                //if(actualRoom.isTileWalkable(getX(),getY()-1) && !actualRoom.isPlayerHere(getX(),getY()-1)){
                verticalForce = verticalForce - 1;
                //}
            } else if (randomDirection == 1) {
                //if(actualRoom.isTileWalkable(getX(),getY()+1) && !actualRoom.isPlayerHere(getX(),getY()+1)){
                verticalForce = verticalForce + 1;
                // }
            } else if (randomDirection == 2) {
                //if(actualRoom.isTileWalkable(getX()-1,getY()) && !actualRoom.isPlayerHere(getX()-1,getY())){
                horizontalForce = horizontalForce - 1;
                //}
            } else {
                //if(actualRoom.isTileWalkable(getX()+1,getY()) && !actualRoom.isPlayerHere(getX()+1,getY())){
                horizontalForce = horizontalForce + 1;
                //}
            }
        }
        timer++;
        if (timer == 100){
            timer =0;
        }
        getBody().setLinearVelocity(horizontalForce*50,verticalForce*50);
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));

    }

    public BodyDef createBodyDef(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getX()*RENDER_SCALE+RENDER_SCALE,this.getY()*RENDER_SCALE+RENDER_SCALE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(getX()*RENDER_SCALE,getY()*RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
        return bodyDef;
    }

    public FixtureDef createShape(){
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(RENDER_SCALE/2f);

        FixtureDef fixtureCircle = new FixtureDef();

        fixtureCircle.shape = shape;

        return fixtureCircle;
    }
}
