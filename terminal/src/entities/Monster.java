package entities;

import static data.Data.MONSTER_TYPE;

/**
 * Gère le comportement et données d'un monstre
 */
public final class Monster extends Entity {

    public Monster(int x, int y) {
        super(MONSTER_TYPE);
        setX(x);
        setY(y);
    }
}
