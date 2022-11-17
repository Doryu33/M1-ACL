package dungeoncrypt.game.tools;

import dungeoncrypt.game.entities.Player;

public class SingletonGetPosPlayer {
    public static final SingletonGetPosPlayer instance = new SingletonGetPosPlayer();
    private Player player;

    private SingletonGetPosPlayer(){

    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public float getX() {
        return player.getBody().getPosition().x;
    }

    public float getY() {
        return player.getBody().getPosition().y;
    }
}
