package dungeoncrypt.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dungeoncrypt.game.room.Room;

import static dungeoncrypt.game.data.Data.*;

/**
 * Gère le comportement et données du joueur
 */
public final class Player extends Entity {

    private int score;

    public Player(){
        super(PLAYER_TYPE,PLAYER_INITIAL_HP,"sprites/entities/player/isaac.png");
        setInitialPosition();
        this.score = 0;
    }

    /**
     * Positionne le joueur à des coordonnées spécifiques.
     * Centré en x. A une case du bord du bas.
     */
    public void setInitialPosition(){
        setX(INITIAL_PLAYER_POS_X);
        setY(INITIAL_PLAYER_POS_Y);
    }

    /**
     * Met à jour la postion du joueur en fonction des touches entrées
     * @param actualRoom
     */
    public void inputUdapte(Room actualRoom) {
        int verticalForce = 0;
        int horizontalForce = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            /*if(actualRoom.isTileWalkable(getX(),getY()-1)){
                if(!actualRoom.thereIsAMonster(getX(),getY()-1)){*/
            verticalForce = verticalForce - 1;
               /* }
            }*/
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            /*if(actualRoom.isTileWalkable(getX(),getY()+1)){
                if(!actualRoom.thereIsAMonster(getX(),getY()+1)){*/
            verticalForce = verticalForce + 1;
                /*}
            }*/
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            /*if(actualRoom.isTileWalkable(getX()-1,getY())){
                if(!actualRoom.thereIsAMonster(getX()-1,getY())){*/
            horizontalForce = horizontalForce - 1;
               /* }
            }*/
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
           /* if(actualRoom.isTileWalkable(getX()+1,getY())){
                if(!actualRoom.thereIsAMonster(getX()+1,getY())){*/
                    horizontalForce = horizontalForce + 1;
                /*}
            }*/
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            addScore(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            subtractScore(1);
        }

        getBody().setLinearVelocity(horizontalForce*MOVE_SPEED,verticalForce*MOVE_SPEED);

        this.sprite.setPosition(getBody().getPosition().x-(RENDER_SCALE), getBody().getPosition().y-(RENDER_SCALE));
    }

    public void addScore(int point){
        this.score += point;
    }

    public void subtractScore(int point){
        this.score -= point;
    }

    public int getScore(){
        return this.score;
    }
}
