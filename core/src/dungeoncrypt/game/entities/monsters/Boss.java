package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import dungeoncrypt.game.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final ArrayList<ProjectileBoss> projectiles = new ArrayList<>(32);

    public Boss(int x, int y) {
        super(x, y, BOSS_INITIAL_HP, DAMAGE_POINT_BOSS, BOSS_TYPE, BOSS_SCORE, "sprites/entities/monsters/Boss2.png", "sounds/Ghost_Damage.mp3");
        random = new Random();
    }

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
            //shoot();
        }

        moveProjectiles();
//        if(pb.getBody() != null)
//            pb.setPos(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));

        getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE_BOSS/2f)-((2*RENDER_SCALE)/2f),getBody().getPosition().y-(RENDER_SCALE_BOSS/2f)-RENDER_SCALE/2f);
    }

    private void moveProjectiles() {
        ProjectileBoss projectile;
        ArrayList<ProjectileBoss> projectilesToDelete = new ArrayList<>();

        for (ProjectileBoss p : projectiles) {
            projectile = p.checkIfOffLimit();
            if(projectile != null){
                //Détruit les bodies des projectiles en dehors de l'écran
                getBody().getWorld().destroyBody(projectile.getBody());
                projectilesToDelete.add(projectile);
            }else{
                p.updateSpritePosition();
            }
        }
        //Supprime les projectiles
        projectiles.removeAll(projectilesToDelete);
        projectilesToDelete.clear();
    }

    private void shoot() {
        shootHorizontalAndVertical();
        shootDiagonally();
    }

    private ProjectileBoss createProjectile(int horizontalForce, int verticalForce){
        Body body;
        ProjectileBoss pb = new ProjectileBoss(horizontalForce,verticalForce);
        body = getBody().getWorld().createBody(pb.createBodyDef(getBody().getPosition().x/RENDER_SCALE, (PIXEL_ROOM_SIZE - getBody().getPosition().y)/RENDER_SCALE));
        pb.setBody(body);
        pb.setUserData(pb);
        FixtureDef shape = pb.createShape();
        body.createFixture(shape);
        shape.shape.dispose();
        getStage().addActor(pb);
        return pb;
    }

    private void shootHorizontalAndVertical(){
        ProjectileBoss[] p = new ProjectileBoss[4];
        p[0] = createProjectile(-1,0); //Gauche
        p[0].move();
        p[1] = createProjectile(1,0); //Droite
        p[1].move();
        p[2] = createProjectile(0,1); //Haut
        p[2].move();
        p[3] = createProjectile(0,-1); //Bas
        p[3].move();
        projectiles.addAll(Arrays.asList(p));
    }

    private void shootDiagonally(){
        ProjectileBoss[] p = new ProjectileBoss[4];
        p[0] = createProjectile(-1,1); //Gauche Haut
        p[0].move();
        p[1] = createProjectile(1,1); //Droite Haut
        p[1].move();
        p[2] = createProjectile(-1,-1); //Gauche Bas
        p[2].move();
        p[3] = createProjectile(1,-1); //Droite Bas
        p[3].move();
        projectiles.addAll(Arrays.asList(p));
    }

    //TODO détruire les projectiles lorsqu'ils sont hors de l'écran
    private void destroyProjectiles(Body body){

    }

    @Override
    protected int getMovingSpeed() {
        return MOVE_SPEED_BOSS;
    }
}
