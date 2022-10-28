package terminal.tiles.classic;

import terminal.entities.Entity;
import terminal.entities.Player;
import terminal.tiles.special.SpecialTile;

import static terminal.data.Data.*;

public final class Exit extends SpecialTile {

    public Exit() {
        super(EXIT_TYPE,EXIT_TYPE);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean hasSpecialEffect() {
        return true;
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
        if(entity.getType().equals(PLAYER_TYPE)){
            //Cast vers joueur
            Player player = (Player) entity;
            player.setInitialPosition();
        }
    }
}
