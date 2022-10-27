package dungeoncrypt.game.tiles;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import static dungeoncrypt.game.data.Data.FLOOR_TYPE;
import static dungeoncrypt.game.data.Data.RENDER_SCALE;

public class Floor extends Tile{

    public Floor() {
        super(FLOOR_TYPE);
        this.sprite = new Sprite(new Texture(Gdx.files.internal("sprites/floor.png")));
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    public BodyDef createBodyDef(int x, int y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        bodyDef.active = false;
        this.sprite.setBounds(x-RENDER_SCALE,y-RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
        return bodyDef;
    }

    public FixtureDef createShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RENDER_SCALE/2f,RENDER_SCALE/2f);

        FixtureDef fixturePolygon = new FixtureDef();

        fixturePolygon.shape = shape;
        fixturePolygon.isSensor = true;

        return fixturePolygon;
    }

}
