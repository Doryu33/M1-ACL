package dungeoncrypt.game.views;

import com.badlogic.gdx.Game;
import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.tools.SaveManager;

import java.io.File;

import static dungeoncrypt.game.data.Data.*;


public class ScreenManager extends Game {

    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;
    private SaveManager saveManager = null;
    private SoundManager sm;

    @Override
    /**
     * Pendant le chargement du jeu on affiche un temps de chargement
     */
    public void create() {
         sm = SoundManager.getInstance();
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }


    /**
     * Getter
     * @return saveManager
     */
    public SaveManager getSaveManager() {
        return saveManager;
    }

    /**
     * Setter
     * Si le saveManager n'est pas crée, alors en crée un nouveau. Si il existe, ne fait rien
     */
    public void initSaveManager() {
        if(saveManager == null){
            this.saveManager = new SaveManager();
        }
    }

    public SoundManager getSm() {
        return sm;
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
                //Check if save exist
                File saveFile = new File(SAVE_NAME+".txt");
                boolean saveExist = (saveFile.exists() && !saveFile.isDirectory());
                if(saveExist){
                    this.saveManager.loadProgression(SAVE_NAME);
                    if(gameScreen == null) gameScreen = new GameScreen(this);
                    this.setScreen(gameScreen);
                }
                break;
            case CONTINUE_LOAD_APPLICATION:
                this.saveManager = new SaveManager();
                //check if autosave exist
                File autoSaveFile = new File(AUTO_SAVE_NAME+".txt");
                boolean autoSaveExist = (autoSaveFile.exists() && !autoSaveFile.isDirectory());
                if(autoSaveExist){
                    this.saveManager.loadProgression(AUTO_SAVE_NAME);
                    if(gameScreen == null) gameScreen = new GameScreen(this);
                    this.setScreen(gameScreen);
                }
                break;
            case ENDGAME:
                if(endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
    }

}
