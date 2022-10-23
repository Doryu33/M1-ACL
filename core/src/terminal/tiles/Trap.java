package terminal.tiles;

import terminal.entities.Entity;

import static terminal.data.Data.*;

public class Trap extends SpecialTile {

    public Trap() {
        super(TRAP_TYPE, HEALTH_SPECIAL_TYPE);
    }

    @Override
    protected boolean isMonstersCanBeAffected() {
        return true;
    }

    @Override
    protected int getMaxUsageEffect() {
        return MAX_USE_TRAP;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    /**
     * Effet : enlever HP_TRAP à l'entité
     * @param entity qui perd de la santé
     */
    @Override
    protected void useEffect(Entity entity) {
        entity.subtractHealthPoint(HP_TRAP);
    }
}