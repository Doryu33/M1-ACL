package terminal.tiles;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Tile extends Actor {

    private final String type;

    /**
     * Savoir si le joueur ou les monstres peuvent marcher sur la tuile
     * @return vrai si c'est possible
     */
    public abstract boolean isWalkable();

    /**
     * Savoir si la tuile possédent une effet particulier
     * Sol, mur n'en ont pas
     * Piège, soin en ont
     * @return vrai si la tuile à un effet
     */
    public abstract boolean hasSpecialEffect();

    public Tile(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public BodyDef createBodyDef(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        return bodyDef;
    }

    public PolygonShape createShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32/2,32/2);
        shape.dispose();
        return shape;
    }
}
