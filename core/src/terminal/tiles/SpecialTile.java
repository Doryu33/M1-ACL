package terminal.tiles;

import terminal.entities.Entity;

import static terminal.data.Data.*;

/**
 * Les tuiles héritant de SpecialTile possédent un effet particulier comme fait perdre de la vie ou en faire gagner.
 * Ces effets peuvent être activer plusieurs fois ou un seule fois.
 */
public abstract class SpecialTile extends Tile {

    private final String specialType;
    //Nombre de fois que l'effet a été utilisé
    private int appliedEffectCounter = 0;

    public SpecialTile(String type, String specialType) {
        super(type);
        this.specialType = specialType;
    }

    //Fonctions publiques

    public String getSpecialType() {
        return specialType;
    }

    /**
     * Appliquer l'effet de la tuile sur l'entité
     * Cette fonction vérifie les conditions avant d'appliquer l'effet
     * @param entity qui doit recevoir l'effet
     */
    public void applyEffectOn(Entity entity){
        //Si le compteur n'est pas dépassé ET si l'entité est le joueur OU que les monstres peuvent
        //avoir l'effet, alors appliquer l'effet sur l'entité
        if(isAppliedEffectCounterOver() &&
                (entity.getType().equals(PLAYER_TYPE) || isMonstersCanBeAffected())){
            useEffect(entity);
        }
        //S'il y a un nombre limité d'utilisation, alors incrémenter le nombre d'utilisation
        if(hasLimitedUse()){
            addOneAppliedEffectCounter();
        }
    }

    @Override
    public boolean hasSpecialEffect() {
        return true;
    }

    //Fonctions protégées

    /**
     * Appliquer l'effet de la tuile sur l'entité
     * @param entity qui doit recevoir l'effet
     */
    protected abstract void useEffect(Entity entity);

    /**
     * Savoir si les monstres peuvent être affectés par l'effet de la tuile
     * @return vrai s'ils peuvent être affectés
     */
    protected abstract boolean isMonstersCanBeAffected();

    /**
     * Définir le nombre maximum d'utilisation de la tuile
     */
    protected abstract int getMaxUsageEffect();



    //Fonctions privées

    /**
     * Savoir si le compteur d'utilisation max n'est pas dépassé
     * Ou qu'il n'y a pas de limite d'utilisation
     * @return vrai si le compteur n'est pas dépassé ou illimité
     */
    private boolean isAppliedEffectCounterOver(){
        return appliedEffectCounter < getMaxUsageEffect() || !hasLimitedUse();
    }

    /**
     * Ajouter 1 au nombre de fois que la tuile à effectué son effet
     */
    private void addOneAppliedEffectCounter() {
        this.appliedEffectCounter += 1;
    }

    /**
     * Savoir si la tuile à un nombre d'utilisation limité
     * @return vrai si le nombre max d'utilisation est supérieur ou égal à 1
     */
    private boolean hasLimitedUse(){
        return getMaxUsageEffect() >= 1;
    }

}
