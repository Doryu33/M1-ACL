package dungeoncrypt.game.views;

import com.badlogic.gdx.Game;
import dungeoncrypt.game.tools.SaveManager;

import static dungeoncrypt.game.data.Data.*;


public class ScreenManager extends Game {

    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;
    private SaveManager saveManager = null;

    @Override
    /**
     * Pendant le chargement du jeu on affiche un temps de chargement
     */
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }


    public SaveManager getSaveManager() {
        return saveManager;
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
                this.saveManager = null;
                if(gameScreen == null) gameScreen = new GameScreen(this);
                this.setScreen(gameScreen);
                break;
            case LOAD_APPLICATION:
                this.saveManager = new SaveManager();
                this.saveManager.loadProgression("PathToSave");
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
