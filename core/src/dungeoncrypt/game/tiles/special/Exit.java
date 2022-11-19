package dungeoncrypt.game.tiles.special;

import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.entities.Entity;
import dungeoncrypt.game.entities.Player;

import static dungeoncrypt.game.data.Data.*;


public final class Exit extends SpecialTile {

    private boolean isOpen = false;

    //Ici isEnable est définit comme vrai lorsque la porte est fermée.
    public Exit(String specialType) {
        super(EXIT_TYPE,specialType,true,"sprites/tiles/special/closedExit.png");
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
     * Effectuement également le changement de sprite
     */
    public void setOpen(){
        isOpen = true;
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
            //Cast vers joueur
            Player player = (Player) entity;
            player.setInitialPosition();
        }
    }

    @Override
    protected void setSpriteToDisabled() {
        setTextureToSprite("sprites/tiles/special/openedExit.png");
        SoundManager soundManager = SoundManager.getInstance();
        soundManager.playDoorOpenSound();
    }
}
