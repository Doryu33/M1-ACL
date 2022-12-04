package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import dungeoncrypt.game.data.DataNonFinal;
import dungeoncrypt.game.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static dungeoncrypt.game.data.Data.*;

public class Boss extends Monster {

    //Intervalle de tire entre chaque tir
    private final float periodRange = 0.8f; //Temps en seconde
    private float timeSecondsRange = 0f;

    //Temps de tire
    private final float periodShooting = periodRange*10; //Temps en seconde
    private float timeSecondsShooting = 0f;

    private final Random random;
    private int randomPosY;
    private int randomPosX;
    private final ArrayList<ProjectileBoss> projectiles = new ArrayList<>(32);
    private boolean isMoving;
    private boolean isShooting;

    public Boss(int x, int y) {
        super(x, y, DataNonFinal.initialHpBoss(), DataNonFinal.damagePointBoss(), BOSS_TYPE, BOSS_SCORE, "sprites/entities/monsters/boss/boss.png", "sounds/Ghost_Damage.mp3");
        random = new Random();
        generateNewRandomPos();
    }

    private void generateNewRandomPos(){
        int borneSup = PIXEL_ROOM_SIZE-RENDER_SCALE-RENDER_SCALE_BOSS;
        randomPosX = random.nextInt(borneSup)/RENDER_SCALE;
        randomPosY = random.nextInt(borneSup)/RENDER_SCALE;
        isMoving = true;
    }

    /**
     * Le boss se déplace toutes les periodMoving secondes.
     * Puis tire pendant periodShooting secondes avec comme intervalle de tire periodRange
     * @param actualRoom salle actuelle
     */
    @Override
    public void updatePosition(Room actualRoom) {
        this.verticalForce = 0;
        this.horizontalForce = 0;

        if(isMoving){
            int bossPosX = (int) (getBody().getPosition().x-(RENDER_SCALE_BOSS/2))/RENDER_SCALE;
            int bossPosY = (int) (getBody().getPosition().y-(RENDER_SCALE_BOSS/2))/RENDER_SCALE;

            //Si on arrive presque à destination on arrête de bouger. Sinon on bouge
            if(Math.abs(bossPosX-randomPosX) < 1 && Math.abs(bossPosY-randomPosY) < 1){
                isMoving = false;
                isShooting = true;
            }else{
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
            }
        }else if(isShooting){
            timeSecondsShooting += Gdx.graphics.getDeltaTime();
            if(timeSecondsShooting > periodShooting) {
                timeSecondsShooting -= periodShooting;
                isShooting = false;
                generateNewRandomPos();
            }else{
                timeSecondsRange += Gdx.graphics.getDeltaTime();
                if(timeSecondsRange > periodRange) {
                    timeSecondsRange -= periodRange;
                    shoot();
                }
            }
        }
        moveProjectiles();
        getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
        this.sprite.setPosition(getBody().getPosition().x-RENDER_SCALE_BOSS/2f-RENDER_SCALE_SPRITE,getBody().getPosition().y-RENDER_SCALE_BOSS/2f);
    }

    /**
     * Déplacer tous les projectiles encore visible, détruire sinon
     */
    private void moveProjectiles() {
        if(projectiles.size() > 0){
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
    }

    /**
     * Tirer les projectiles. Vérifie les points de vie, si < 50%, tire en diagonale en plus
     */
    private void shoot() {
        shootHorizontalAndVertical();
        if(getHealthPoint() / (float) DataNonFinal.initialHpBoss() <= 0.5f){
            shootDiagonally();
        }
    }

    /**
     * Créer un projectile
     * @param horizontalForce du projectile
     * @param verticalForce du projectile
     * @return le projectile créé
     */
    private ProjectileBoss createProjectile(int horizontalForce, int verticalForce, float rotation){
        Body body;
        ProjectileBoss pb = new ProjectileBoss(horizontalForce,verticalForce, rotation);
        body = getBody().getWorld().createBody(pb.createBodyDef((getBody().getPosition().x- RENDER_SCALE)/RENDER_SCALE , (PIXEL_ROOM_SIZE - getBody().getPosition().y)/RENDER_SCALE));
        pb.setBody(body);
        pb.setUserData(pb);
        FixtureDef shape = pb.createShape();
        body.createFixture(shape);
        shape.shape.dispose();
        getStage().addActor(pb);
        return pb;
    }

    /**
     * Tire 4 projectile en horizontal et vertical
     */
    private void shootHorizontalAndVertical(){
        ProjectileBoss[] p = new ProjectileBoss[4];
        p[0] = createProjectile(-1,0,-90); //Gauche
        p[0].move();
        p[1] = createProjectile(1,0,90); //Droite
        p[1].move();
        p[2] = createProjectile(0,1,180); //Haut
        p[2].move();
        p[3] = createProjectile(0,-1,0); //Bas
        p[3].move();
        projectiles.addAll(Arrays.asList(p));
    }

    /**
     * Tire 4 projectiles en diagonal
     */
    private void shootDiagonally(){
        ProjectileBoss[] p = new ProjectileBoss[4];
        p[0] = createProjectile(-1,1,225); //Gauche Haut
        p[0].move();
        p[1] = createProjectile(1,1,135); //Droite Haut
        p[1].move();
        p[2] = createProjectile(-1,-1,-45); //Gauche Bas
        p[2].move();
        p[3] = createProjectile(1,-1,45); //Droite Bas
        p[3].move();
        projectiles.addAll(Arrays.asList(p));
    }

    @Override
    protected int getMovingSpeed() {
        return DataNonFinal.moveSpeedBoss();
    }

    public float getPosX(){
        return getBody().getPosition().x;
    }

    public float getPosY(){
        return getBody().getPosition().y;
    }

    public void deleteAllProjectiles() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                ArrayList<ProjectileBoss> projectilesToDelete = new ArrayList<>();
                World world = getBody().getWorld();
                for (ProjectileBoss p : projectiles) {
                    //Détruit les bodies des projectiles en dehors de l'écran
                    p.setVisible(false);
                    world.destroyBody(p.getBody());
                    projectilesToDelete.add(p);
                }
                //Supprime les projectiles
                projectiles.removeAll(projectilesToDelete);
                projectilesToDelete.clear();
            }
        });
    }
}
