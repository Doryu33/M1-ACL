package dungeoncrypt.game.weapons;

import static dungeoncrypt.game.data.Data.*;

public final class Sword extends Weapon{


    public Sword() {
        super(SWORD_TYPE, DAMAGE_SWORD,"sprites/weapons/sword.png");
        setInitialPosition();
    }

    public void setInitialPosition(){
                setX(INITIAL_PLAYER_POS_X);
                setY(INITIAL_PLAYER_POS_Y-1);
    }
}
