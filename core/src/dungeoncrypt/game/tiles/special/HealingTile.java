package dungeoncrypt.game.tiles.special;


import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.entities.Entity;

import static dungeoncrypt.game.data.Data.*;

public final class HealingTile extends SpecialTile {

    public HealingTile() {
        super(HEALING_TILE_TYPE, HEALTH_SPECIAL_TYPE,true,"sprites/tiles/special/healingTileEnabled.png");
    }


    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    protected boolean isMonstersCanBeAffected() {
        return false;
    }

    @Override
    protected int getMaxUsageEffect() {
        return MAX_USE_HEALING_TILE;
    }

    /**
     * Effet : ajouter HP_HEALING_TILE à l'entité
     * @param entity qui gagne de la santé
     */
    @Override
    protected void useEffect(Entity entity) {
        if(!entity.hasFullHealth()){
            SoundManager.getInstance().playSound("sounds/Heal.mp3");
            entity.addHealthPoint(HP_HEALING_TILE);
            addOneAppliedEffectCounter();
            setSpriteToDisabled();
        }
    }

    @Override
    protected void setSpriteToDisabled() {
        setTextureToSprite("sprites/tiles/special/healingTileDisabled.png");
    }
}
