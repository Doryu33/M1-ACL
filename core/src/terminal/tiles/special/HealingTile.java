package terminal.tiles.special;

import terminal.entities.Entity;

import static terminal.data.Data.*;

public final class HealingTile extends SpecialTile {

    public HealingTile() {
        super(HEALING_TILE_TYPE, HEALTH_SPECIAL_TYPE);
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
        entity.addHealthPoint(HP_HEALING_TILE);
    }


}
