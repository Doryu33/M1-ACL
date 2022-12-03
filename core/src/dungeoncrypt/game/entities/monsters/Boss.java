package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import dungeoncrypt.game.room.Room;

import java.util.Random;

import static dungeoncrypt.game.data.Data.*;

public class Boss extends Monster {

    private final Random random;
    private float timeSeconds = 0f;
    private final float period = 6f;    //Temps en seconde
    private int randomPosY;
    private int randomPosX;
    private boolean isMovingOrd = false;
    private boolean isMovingAbs = false;
    private ProjectileBoss pb;

    public Boss(int x, int y) {
        super(x, y, BOSS_INITIAL_HP, DAMAGE_POINT_BOSS, BOSS_TYPE, BOSS_SCORE, "sprites/entities/monsters/Boss2.png", "sounds/Ghost_Damage.mp3");
        random = new Random();
        pb = new ProjectileBoss();
    }

    //TODO Position du sprite à refaire
    @Override
    public void updatePosition(Room actualRoom) {
        timeSeconds += Gdx.graphics.getDeltaTime();
        if(timeSeconds > period){
            timeSeconds -= period;
            int borneSup = PIXEL_ROOM_SIZE-RENDER_SCALE-RENDER_SCALE_BOSS;
            randomPosX = random.nextInt(borneSup);
            randomPosY = random.nextInt(borneSup);
            shoot();
        }

        int bossPosX = (int) getBody().getPosition().x-(RENDER_SCALE_BOSS);
        int bossPosY = (int) getBody().getPosition().y-(RENDER_SCALE_BOSS);

        this.verticalForce = 0;
        this.horizontalForce = 0;
        if (bossPosY > randomPosY) {
            verticalForce = verticalForce - 1;
            isMovingOrd = true;
        } else if (bossPosY < randomPosY) {
            verticalForce = verticalForce + 1;
            isMovingOrd = true;
        }else{
            isMovingOrd = false;
        }

        if (bossPosX > randomPosX) {
            horizontalForce = horizontalForce - 1;
            isMovingAbs = true;
        } else if (bossPosX < randomPosX) {
            horizontalForce = horizontalForce + 1;
            isMovingAbs = true;
        }else{
            isMovingAbs = false;
        }

        if(!isMovingAbs && !isMovingOrd){
            shoot();
        }
        if(pb.getBody() != null)
            pb.setPos(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));

        //getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE_BOSS/2f)-((2*RENDER_SCALE)/2f),getBody().getPosition().y-(RENDER_SCALE_BOSS/2f)-RENDER_SCALE/2f);
    }

    private void shoot() {
        Body body;
        body = getBody().getWorld().createBody(pb.createBodyDef(getBody().getPosition().x/RENDER_SCALE, (PIXEL_ROOM_SIZE - getBody().getPosition().y)/RENDER_SCALE));
        pb.setBody(body);
        pb.setUserData(pb);
        pb.move();
        FixtureDef shape = pb.createShape();
        body.createFixture(shape);
        shape.shape.dispose();
        getStage().addActor(pb);
    }

    private ProjectileBoss createProjectile(){
        return null;
    }

    private void shootHorizontalAndVertical(){
        ProjectileBoss[] projectiles = new ProjectileBoss[4];
        for (int i = 0; i < 4; i++) {
            projectiles[i] = createProjectile();
        }
        projectiles[0].move(-1,0); //Gauche
        projectiles[1].move(1,0); //Droite
        projectiles[2].move(0,1); //Haut
        projectiles[3].move(0,-1); //Bas
    }

    private void shootDiagonally(){
        ProjectileBoss[] projectiles = new ProjectileBoss[4];
        for (int i = 0; i < 4; i++) {
            projectiles[i] = createProjectile();
        }
        projectiles[0].move(-1,1); //Gauche Haut
        projectiles[1].move(1,1); //Droite Haut
        projectiles[2].move(-1,-1); //Gauche Bas
        projectiles[3].move(1,-1); //Droite Bas
    }

    @Override
    protected int getMovingSpeed() {
        return MOVE_SPEED_BOSS;
    }
}
