package dungeoncrypt.game.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static dungeoncrypt.game.data.Data.EXIT_TYPE;
import static dungeoncrypt.game.data.Data.RENDER_SCALE;

public final class Exit extends Tile {

    public Exit() {
        super(EXIT_TYPE);
        this.sprite = new Sprite(new Texture(Gdx.files.internal("sprites/exit.png")));
        this.sprite.setBounds(getX(),getY(),RENDER_SCALE,RENDER_SCALE);
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
        this.sprite.setBounds(x,y,RENDER_SCALE,RENDER_SCALE);
        return bodyDef;
    }

    public FixtureDef createShape(){
        CircleShape circle = new CircleShape();
        circle.setPosition(new Vector2(0, 0));
        circle.setRadius(RENDER_SCALE/2f);

        FixtureDef fixturePolygon = new FixtureDef();

        fixturePolygon.shape = circle;

        return fixturePolygon;
    }
}
