package terminal.tiles.special;

import terminal.entities.Entity;
import terminal.entities.Player;
import terminal.tiles.special.SpecialTile;

import static terminal.data.Data.*;

public final class Exit extends SpecialTile {

    private boolean isOpen = false;

    public Exit(String specialType) {
        super(EXIT_TYPE,specialType);
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
     * Définir la sortie comme open --> Si le joueur touche la sortie il passe au niveau suivant
     */
    public void setOpen(){
        isOpen = true;
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
            //Cast vers joueur
            Player player = (Player) entity;
            player.setInitialPosition();
        }
    }
}
