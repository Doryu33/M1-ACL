package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import dungeoncrypt.game.data.DataNonFinal;
import dungeoncrypt.game.room.Room;

import java.util.Random;

import static dungeoncrypt.game.data.Data.*;
import static dungeoncrypt.game.data.Data.RENDER_SCALE;

public final class Skeleton extends Monster {

    private final Random random;
    private float timeSeconds = 0f;
    private final float period = 4f;    //Temps en seconde

    public Skeleton(int x, int y) {
        super(x, y, DataNonFinal.initialHpSkeleton(), DataNonFinal.damagePointSkeleton(), SKELETON_TYPE, SKELETON_SCORE,"sprites/entities/monsters/skeleton.gif","sounds/Skeleton_Damage.mp3");
        random = new Random();
    }

    /**
     * Met à jour la position du monstre en fonction d'un nombre aléatoire entre 0 compris et 4 non compris
     * @param actualRoom salle actuelle
     */
    @Override
    public void updatePosition(Room actualRoom) {
        timeSeconds += Gdx.graphics.getDeltaTime();
        if(timeSeconds > period){
            timeSeconds -= period;
            int randomDirection = random.nextInt(4);
            this.verticalForce = 0;
            this.horizontalForce = 0;
            if(randomDirection == 0) {
                verticalForce = verticalForce - 1;
            } else if (randomDirection == 1) {
                verticalForce = verticalForce + 1;
            } else if (randomDirection == 2) {
                horizontalForce = horizontalForce - 1;
            } else {
                horizontalForce = horizontalForce + 1;
            }
        }
        if (getKnockBackVertical() != 0 || getKnockBackHorizontal() != 0){
            knockBackMove();
        }else{
            getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
        }
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE_SPRITE),getBody().getPosition().y-(RENDER_SCALE_SPRITE));
    }

    @Override
    protected int getMovingSpeed() {
        return DataNonFinal.moveSpeedSkeleton();
    }


}
