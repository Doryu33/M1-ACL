package dungeoncrypt.game.tiles.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.entities.Entity;
import dungeoncrypt.game.room.RoomManager;

import static dungeoncrypt.game.data.Data.*;

public final class Exit extends SpecialTile {

    private final RoomManager roomManager;
    private boolean isOpen = false;

    //Ici isEnable est définit comme vrai lorsque la porte est fermée.
    public Exit(String specialType, RoomManager roomManager) {
        super(EXIT_TYPE,specialType,true,"sprites/tiles/special/closedExit.png");
        this.roomManager = roomManager;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean hasSpecialEffect() {
        return true;
    }

    /**
     * Créer la FixtureDef d'une tuile
     * @return FixtureDef
     */
    @Override
    public FixtureDef createShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RENDER_SCALE/2f,RENDER_SCALE/2f);
        fixturePolygon = new FixtureDef();
        fixturePolygon.isSensor = false;
        fixturePolygon.shape = shape;
        return fixturePolygon;
    }

    /**
     * Définir la sortie comme open --> Si le joueur touche la sortie il passe au niveau suivant
     * Effectuement également le changement de sprite
     */
    public void setOpen(){
        isOpen = true;
        fixturePolygon.isSensor = true;
        setSpriteToDisabled();
    }

    /**
     * Savoir l'état de la sortie
     * @return vrai si la sortie est ouverte
     */
    public boolean isOpen(){
        return isOpen;
    }

    @Override
    protected boolean isMonstersCanBeAffected() {
        return false;
    }

    @Override
    protected int getMaxUsageEffect() {
        return MAX_USE_EXIT;
    }

    /**
     * Replace le joueur aux positions de départ
     * Vérifie si c'est bien le joueur et pas un monstre
     * @param entity qui doit être Joueur
     */
    @Override
    protected void useEffect(Entity entity) {
        if(entity.getType().equals(PLAYER_TYPE) && isOpen){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run () { roomManager.goToNextRoom(getSpecialType().equals(EXIT_SPECIAL_TYPE_NEW_LEVEL));
                }
            });
        }
    }

    @Override
    protected void setSpriteToDisabled() {
        setTextureToSprite("sprites/tiles/special/openedExit.png");
        SoundManager soundManager = SoundManager.getInstance();
        soundManager.playDoorOpenSound();
    }
}
