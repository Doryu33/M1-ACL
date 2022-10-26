package dungeoncrypt.game.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Tile extends Actor {

    private final String type;

    protected Sprite sprite;

    public abstract boolean isWalkable();

    public Tile(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * Crée le BodyDef d'une tuile
     * @param x
     * @param y
     * @return BodyDef
     */
    public abstract BodyDef createBodyDef(int x,int y);

    /**
     * Crée la FixtureDef d'une tuile
     * @return FixtureDef
     */
    public abstract FixtureDef createShape();

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }

}
