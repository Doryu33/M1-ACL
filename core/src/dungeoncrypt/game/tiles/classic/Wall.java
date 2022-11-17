package dungeoncrypt.game.tiles.classic;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dungeoncrypt.game.tiles.Tile;

import static dungeoncrypt.game.data.Data.*;

public final class Wall extends Tile {

    public Wall(){
        super(WALL_TYPE,"sprites/tiles/classic/wall.png");
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean hasSpecialEffect() {
        return false;
    }

    @Override
    /**
     * Crée la FixtureDef d'une tuile
     * @return FixtureDef
     */
    public FixtureDef createShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RENDER_SCALE/2f,RENDER_SCALE/2f);

        FixtureDef fixturePolygon = new FixtureDef();
        fixturePolygon.isSensor = false;
        fixturePolygon.shape = shape;
        return fixturePolygon;
    }

    public char getCharTile(){
        return WALL_TILE;
    }

}
