package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import dungeoncrypt.game.aStarAlgorithm.PathFinding;
import dungeoncrypt.game.aStarAlgorithm.Point;
import dungeoncrypt.game.room.Room;

import java.util.List;

import static dungeoncrypt.game.data.Data.*;

public final class Zombie extends Monster {

    private float timeSeconds = 0f;
    private final float period = 0.1f;    //Temps en seconde
    private List<Point> pathOfPoints;
    private int integerPartXMonster;
    private int integerPartYMonster;
    private int posXTarget;
    private int posYTarget;
    private boolean movingLeft;
    private boolean movingDown;

    public Zombie(int x, int y) {
        super(x, y,ZOMBIE_INITIAL_HP, DAMAGE_POINT_ZOMBIE, ZOMBIE_TYPE, ZOMBIE_SCORE,"sprites/entities/monsters/zombie.gif","sounds/Zombie_Damage.mp3");
    }

    /**
     * Met à jour la direction du monstre.
     * Toutes les 0.1 secondes, le monstre génère le chemin le plus court vers le joueur
     */
    @Override
    public void updatePosition(Room actualRoom) {
        timeSeconds += Gdx.graphics.getDeltaTime();
        this.verticalForce = 0;
        this.horizontalForce = 0;

        if(timeSeconds > period){
            timeSeconds -= period;
            getPath(actualRoom.getPlayerPosX(),actualRoom.getPlayerPosY());
        }

        if (getKnockBackVertical() != 0 || getKnockBackHorizontal() != 0){
            knockBackMove();
        }else{
            if(pathOfPoints != null && pathOfPoints.size() > 0){
                posXTarget = pathOfPoints.get(0).x;
                posYTarget = pathOfPoints.get(0).y;
            }

            if(pathOfPoints != null && pathOfPoints.size() <= 15) {
                fixPosition();

                if(integerPartXMonster > posXTarget){
                    //Gauche
                    horizontalForce = horizontalForce - 1;
                    movingLeft = true;
                }else if(integerPartXMonster < posXTarget){
                    //Droite
                    horizontalForce = horizontalForce + 1;
                    movingLeft = false;
                }
                if(integerPartYMonster > posYTarget){
                    //Haut
                    verticalForce = verticalForce + 1;
                    movingDown = false;
                }else if(integerPartYMonster < posYTarget) {
                    //Bas
                    verticalForce = verticalForce - 1;
                    movingDown = true;
                }
            }
            if (getKnockBackVertical() != 0 || getKnockBackHorizontal() != 0){
                knockBackMove();
            }else{
                getBody().setLinearVelocity(horizontalForce*getMovingSpeed(),verticalForce*getMovingSpeed());
            }
        }
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE_SPRITE),getBody().getPosition().y-(RENDER_SCALE_SPRITE));
    }

    /**
     * Fixer les positions du monstre pour matcher avec l'algo de pathfinding
     */
    private void fixPosition(){
        float monsterPosX = getBody().getPosition().x-(RENDER_SCALE);
        float monsterPosY = getBody().getPosition().y-(RENDER_SCALE);

        integerPartXMonster = (int) monsterPosX/RENDER_SCALE;
        integerPartYMonster = (int) monsterPosY/RENDER_SCALE;
        integerPartYMonster = ROOM_SIZE-1 - integerPartYMonster;

        if(movingDown && movingLeft){
            integerPartYMonster++;
        }
        else if(movingLeft){
            integerPartXMonster++;
        }
        else if(movingDown){
            integerPartYMonster--;
        }
    }

    /**
     * Récupérer le chemin pour atteindre le joueur via l'algo de pathfinding A*
     * @param playerX position en X du joueur
     * @param playerY position en Y du joueur
     */
    private void getPath(float playerX, float playerY){
        fixPosition();
        Point startPoint = new Point(integerPartXMonster, integerPartYMonster);

        float playerPosX = playerX-(RENDER_SCALE/2f);
        float playerPosY = playerY-(RENDER_SCALE/2f);

        int integerPartXPlayer = (int) playerPosX/RENDER_SCALE;
        int integerPartYPlayer = (int) playerPosY/RENDER_SCALE;
        integerPartYPlayer = ROOM_SIZE-1 - integerPartYPlayer;

        Point targetPoint = new Point(integerPartXPlayer, integerPartYPlayer);
        pathOfPoints = PathFinding.findPath(startPoint, targetPoint, false);
    }

    @Override
    protected int getMovingSpeed() {
        return MOVE_SPEED_ZOMBIE;
    }
}
