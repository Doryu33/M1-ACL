package dungeoncrypt.game.tiles.special;

import dungeoncrypt.game.entities.Entity;

import static dungeoncrypt.game.data.Data.*;

public final class Trap extends SpecialTile {

    public Trap() {
        super(TRAP_TYPE, HEALTH_SPECIAL_TYPE, true,"sprites/tiles/special/trap.png");
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
     * Si c'est le fantôme, ne rien faire
     * @param entity qui perd de la santé
     */
    @Override
    protected void useEffect(Entity entity) {
        //System.out.println(entity.getType()+" a subit des degat de "+this.getType());
        if(!entity.getSpecialType().equals(GHOST_TYPE)){
            entity.subtractHealthPoint(HP_TRAP);
        }
    }

    @Override
    protected void setSpriteToDisabled() {

    }

    public char getCharTile(){
        return TRAP_TILE;
    }
}