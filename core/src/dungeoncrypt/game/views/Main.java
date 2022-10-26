package dungeoncrypt.game.views;

import com.badlogic.gdx.Game;
import static dungeoncrypt.game.data.Data.*;


public class Main extends Game {

    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;

    @Override
    /**
     * Pendant le chargement du jeu on affiche un temps de chargement
     */
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }


    /**
     * Permet de changer le screen a afficher
     * @param screen Numero du screen a afficher
     */
    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if(gameScreen == null) gameScreen = new GameScreen(this);
                this.setScreen(gameScreen);
                break;
            case ENDGAME:
                if(endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
    }

}
