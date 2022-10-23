package terminal.tiles;

public abstract class Tile {

    private final String type;

    /**
     * Savoir si le joueur ou les monstres peuvent marcher sur la tuile
     * @return vrai si c'est possible
     */
    public abstract boolean isWalkable();

    /**
     * Savoir si la tuile possédent une effet particulier
     * Sol, mur n'en ont pas
     * Piège, soin en ont
     * @return vrai si la tuile à un effet
     */
    public abstract boolean hasSpecialEffect();

    public Tile(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
