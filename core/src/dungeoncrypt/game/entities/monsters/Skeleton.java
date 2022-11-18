package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import dungeoncrypt.game.room.Room;

import java.util.Random;

import static dungeoncrypt.game.data.Data.*;
import static dungeoncrypt.game.data.Data.RENDER_SCALE;

public final class Skeleton extends Monster {

    protected float timeSeconds = 0f;
    protected float period = 4f;    //Temps en seconde

    public Skeleton(int x, int y) {
        super(x, y, SKELETON_INITIAL_HP, SKELETON_TYPE, SKELETON_SCORE,"sprites/entities/monsters/skeleton.gif");
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
            Random random = new Random();
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
        getBody().setLinearVelocity(horizontalForce*50,verticalForce*50);
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
    }
}
