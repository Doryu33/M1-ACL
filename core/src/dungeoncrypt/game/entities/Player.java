package dungeoncrypt.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dungeoncrypt.game.room.Room;

import java.util.Scanner;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données du joueur
 */
public final class Player extends Entity {

    public Player(){
        super(PLAYER_TYPE);
        setInitialPosition();
        this.sprite = new Sprite(new Texture(Gdx.files.internal("sprites/isaac.png")));
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
     * @param actualRoom
     */
    public void inputUdapte(Room actualRoom) {
        int verticalForce = 0;
        int horizontalForce = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            /*if(actualRoom.isTileWalkable(getX(),getY()-1)){
                if(!actualRoom.thereIsAMonster(getX(),getY()-1)){*/
            verticalForce = verticalForce - 1;
               /* }
            }*/
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            /*if(actualRoom.isTileWalkable(getX(),getY()+1)){
                if(!actualRoom.thereIsAMonster(getX(),getY()+1)){*/
            verticalForce = verticalForce + 1;
                /*}
            }*/
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            /*if(actualRoom.isTileWalkable(getX()-1,getY())){
                if(!actualRoom.thereIsAMonster(getX()-1,getY())){*/
            horizontalForce = horizontalForce - 1;
               /* }
            }*/
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
           /* if(actualRoom.isTileWalkable(getX()+1,getY())){
                if(!actualRoom.thereIsAMonster(getX()+1,getY())){*/
                    horizontalForce = horizontalForce + 1;
                /*}
            }*/
        }

        getBody().setLinearVelocity(horizontalForce*50,verticalForce*50);
    }

    public BodyDef createBodyDef(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getX()*RENDER_SCALE+RENDER_SCALE,this.getY()*RENDER_SCALE+RENDER_SCALE);
        bodyDef.fixedRotation = true;
        this.sprite.setBounds(getX()*RENDER_SCALE+RENDER_SCALE,getY()*RENDER_SCALE+RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
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
