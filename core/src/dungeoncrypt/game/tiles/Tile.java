package dungeoncrypt.game.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static dungeoncrypt.game.data.Data.RENDER_SCALE;

public abstract class Tile extends Actor {

    private final String type;
    private Sprite sprite;

    public Tile(String type,String spritePath){
        this.type = type;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        // A méditer //this.sprite.setBounds(getX(),getY(),RENDER_SCALE,RENDER_SCALE);

    }

    public abstract boolean isWalkable();

    /**
     * Savoir si la tuile possédent une effet particulier
     * Sol, mur n'en ont pas
     * Piège, soin en ont
     * @return vrai si la tuile à un effet
     */
    public abstract boolean hasSpecialEffect();

    public String getType() {
        return type;
    }

    protected void setTextureToSprite(String spritePath){
        this.sprite.setTexture(new Texture(Gdx.files.internal(spritePath)));
    }

    /**
     * Crée le BodyDef d'une tuile
     * @param x
     * @param y
     * @return BodyDef
     */
    public BodyDef createBodyDef(int x,int y,boolean isActive){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        bodyDef.active = isActive;
        this.sprite.setBounds(x-(RENDER_SCALE),y-RENDER_SCALE,RENDER_SCALE,RENDER_SCALE);
        return bodyDef;
    }

    /**
     * Crée la FixtureDef d'une tuile
     * @return FixtureDef
     */
    public FixtureDef createShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RENDER_SCALE/2f,RENDER_SCALE/2f);

        FixtureDef fixturePolygon = new FixtureDef();

        fixturePolygon.shape = shape;

        return fixturePolygon;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }

}
