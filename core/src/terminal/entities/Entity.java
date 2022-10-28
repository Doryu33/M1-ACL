package terminal.entities;

/**
 * Gère le comportement et données communes d'une entitée
 */
public abstract class Entity {

    private int x;
    private int y;
    private final String type;
    //Entier du nombre de point de vie
    private int healthPoint;

    public Entity(String type, int healthPoint){
        this.type = type;
        this.healthPoint = healthPoint;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    /**
     * Redéfinit le nombre de point de vie
     * @param healthPoint le nombre à redéfinir
     */
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    /**
     * Ajouter des points de vie
     * @param healthPoint le nombre à ajouter
     */
    public void addHealthPoint(int healthPoint){
        this.healthPoint += healthPoint;
    }

    /**
     * Enlever des points de vie
     * @param healthPoint le nombre à enlever
     */
    public void subtractHealthPoint(int healthPoint){
        this.healthPoint -= healthPoint;
    }
}
