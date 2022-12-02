package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import dungeoncrypt.game.room.Room;

import java.util.Random;

import static dungeoncrypt.game.data.Data.*;

public class Boss extends Monster {

    private final Random random;
    private float timeSeconds = 0f;
    private final float period = 4f;    //Temps en seconde
    private int randomPosY;
    private int randomPosX;

    public Boss(int x, int y) {
        super(x, y, BOSS_INITIAL_HP, BOSS_TYPE, BOSS_SCORE, "sprites/entities/monsters/boss.png", "sounds/Ghost_Damage.mp3");
        random = new Random();
    }

    @Override
    public void updatePosition(Room actualRoom) {
        timeSeconds += Gdx.graphics.getDeltaTime();
        if(timeSeconds > period){
            timeSeconds -= period;
            int borneSup = PIXEL_ROOM_SIZE-RENDER_SCALE-RENDER_SCALE_BOSS;
            int borneInf = RENDER_SCALE+RENDER_SCALE_BOSS;
            randomPosX = borneInf + random.nextInt(borneSup - borneInf);
            randomPosY = borneInf + random.nextInt(borneSup - borneInf);
            System.out.println("randomPosX = " + randomPosX);
            System.out.println("randomPosY = " + randomPosY);
        }

        float bossPosX = getBody().getPosition().x-(RENDER_SCALE_BOSS);
        float bossPosY = getBody().getPosition().y-(RENDER_SCALE_BOSS);

        this.verticalForce = 0;
        this.horizontalForce = 0;
        if (bossPosY > randomPosY) {
            verticalForce = verticalForce - 1;
        } else if (bossPosY < randomPosY) {
            verticalForce = verticalForce + 1;
        }
        if (bossPosX > randomPosX) {
            horizontalForce = horizontalForce - 1;
        } else if (bossPosX < randomPosX) {
            horizontalForce = horizontalForce + 1;
        }

//        if (getKnockBackVertical() != 0 || getKnockBackHorizontal() != 0){
//            knockBackMove();
//        }else{
//            getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
//        }
        getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
    }

    @Override
    protected int getMovingSpeed() {
        return MOVE_SPEED_BOSS;
    }
}
