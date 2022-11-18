package dungeoncrypt.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import dungeoncrypt.game.AStar.AStarManager;
import dungeoncrypt.game.AStar.Node;
import dungeoncrypt.game.AStar2.PathFinding;
import dungeoncrypt.game.AStar2.Point;
import dungeoncrypt.game.room.Room;
import dungeoncrypt.game.tools.SingletonGetPosPlayer;

import java.util.List;
import java.util.Random;

import static dungeoncrypt.game.data.Data.*;

public final class Zombie extends Monster {

    private final AStarManager aStarManager;
    private final SingletonGetPosPlayer singletonPlayerPos;
    private float playerPosX;
    private float playerPosY;
    protected float timeSeconds = 0f;
    protected float period = 0.1f;    //Temps en seconde
    private List<Node> path;
    private List<Point> pathOfPoints;
    private int integerPartXMonster;
    private int integerPartYMonster;
    private int integerPartXPlayer;
    private int integerPartYPlayer;
    private int index = 0;
    private int posXTarget;
    private int posYTarget;

    public Zombie(int x, int y) {
        super(x, y,ZOMBIE_INITIAL_HP,ZOMBIE_TYPE,"sprites/entities/monsters/zombie.gif");
        aStarManager = AStarManager.instance;
        singletonPlayerPos = SingletonGetPosPlayer.instance;
    }

    public void aux(){
        float decimalPart = ((getBody().getPosition().x)+(RENDER_SCALE/2f))/RENDER_SCALE;
        integerPartXMonster = (int) decimalPart;

        float decimalPartXMonster = decimalPart-integerPartXMonster;
        if(decimalPartXMonster >= 0.5f){
            integerPartXMonster++;
        }

        decimalPart = ((getBody().getPosition().y)+(RENDER_SCALE/2f))/RENDER_SCALE;
        integerPartYMonster = (int) decimalPart;
        float decimalPartYMonster = decimalPart-integerPartYMonster;
        if(decimalPartYMonster >= 0.5f){
            integerPartYMonster++;
        }
        integerPartYMonster = ROOM_SIZE - integerPartYMonster;
    }
    
    
    @Override
    //Créer le pathfinding
    public void updatePosition(Room actualRoom) {
        timeSeconds += Gdx.graphics.getDeltaTime();

        if(timeSeconds > period){
            timeSeconds -= period;
            getPath();
        }

        this.verticalForce = 0;
        this.horizontalForce = 0;

        if(pathOfPoints != null && pathOfPoints.size() > 0){
            posXTarget = pathOfPoints.get(0).x;
            posYTarget = pathOfPoints.get(0).y;

        }

        aux();

        if(integerPartXMonster > posXTarget){
            horizontalForce = horizontalForce - 1;
        }else if(integerPartXMonster < posXTarget){
            horizontalForce = horizontalForce + 1;
        }
        if(integerPartYMonster > posYTarget){
            verticalForce = verticalForce + 1;
        }else if(integerPartYMonster < posYTarget) {
            verticalForce = verticalForce - 1;
        }
        getBody().setLinearVelocity(horizontalForce*50,verticalForce*50);
        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE),getBody().getPosition().y-(RENDER_SCALE));
    }

    private void getPath(){
        
        aux();

        String tileMonster = (char) (integerPartXMonster+64) + String.valueOf(integerPartYMonster);
        Point startPoint = new Point(integerPartXMonster, integerPartYMonster);

        playerPosX = singletonPlayerPos.getX();
        playerPosY = singletonPlayerPos.getY();

        float decimalPart = (playerPosX +(RENDER_SCALE/2f))/RENDER_SCALE;
        integerPartXPlayer = (int) decimalPart;
        float decimalPartXPlayer = decimalPart-integerPartXPlayer;
        if(decimalPartXPlayer > 0.5f){
            integerPartXPlayer++;
        }

        decimalPart = (playerPosY +(RENDER_SCALE/2f))/RENDER_SCALE;
        integerPartYPlayer = (int) decimalPart;
        float decimalPartYPlayer = decimalPart-integerPartYPlayer;
        if(decimalPartYPlayer > 0.5f){
            integerPartYPlayer++;
        }
        integerPartYPlayer = ROOM_SIZE - integerPartYPlayer;

        String tilePlayer = (char) (integerPartXPlayer+64) + String.valueOf(integerPartYPlayer);
        Point targetPoint = new Point(integerPartXPlayer, integerPartYPlayer);

/*
        aStarManager.printGraph();
        Node n = aStarManager.aStar(tileMonster,tilePlayer);
        path = aStarManager.getPath(n);
        aStarManager.printPath(n);

 */


        pathOfPoints = PathFinding.findPath(startPoint, targetPoint, false);

        for (Point point : pathOfPoints) System.out.print(point);
        System.out.println("");



        index = 0;
    }
}

//https://stackabuse.com/graphs-in-java-a-star-algorithm/

//https://isaaccomputerscience.org/concepts/dsa_search_a_star?examBoard=all&stage=all
//https://brilliant.org/wiki/a-star-search/
