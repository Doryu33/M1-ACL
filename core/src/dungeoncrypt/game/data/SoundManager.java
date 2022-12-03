package dungeoncrypt.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager {

    private static final SoundManager instance = new SoundManager(){};

    public static SoundManager getInstance() {
        return instance;
    }

    private Music musicInGame;

    private Music musicEndGame;

    private Music musicMainMenu;

    private float musicVolume;

    private float soundVolume;

    private boolean musicEnabled;
    private boolean soundEnabled;

    private SoundManager(){
        AppPreferences app = new AppPreferences();
        this.musicVolume = app.getMusicVolume();
        this.soundVolume = app.getSoundVolume();
        this.soundEnabled =  app.isSoundEffectsEnabled();
        this.musicEnabled = app.isMusicEnabled();

        musicMainMenu = Gdx.audio.newMusic(Gdx.files.internal("musics/DeadSilence_Theme.mp3"));
        musicInGame = Gdx.audio.newMusic(Gdx.files.internal("musics/Halloween_Theme.mp3"));
        musicEndGame = Gdx.audio.newMusic(Gdx.files.internal("musics/Saw_Theme.mp3"));
    }
    /**
     * Joue la musique en jeu
     */
    public void playMusicInGame(){
        stopAllMusic();
        if(!musicInGame.isPlaying()){

            musicInGame.setVolume(musicVolume);
            musicInGame.setLooping(true);
            playMusic(musicInGame);
        }
    }


    /**
     * Arrête la musique en jeu
     */
    public void stopMusicInGame(){
        if(musicInGame!=null){
            musicInGame.dispose();
        }
    }

    /**
     * Joue la musique de fin de jeu
     */
    public void playMusicEndGame(){
        stopAllMusic();
        if(!musicEndGame.isPlaying()) {
            musicEndGame.setVolume(musicVolume);
            musicEndGame.setLooping(true);
            playMusic(musicEndGame);
        }
    }


    /**
     * Arrête la musique de fin de jeu
     */
    public void stopMusicEndGame(){
        if(musicEndGame!=null){
            musicEndGame.stop();
        }

    }

    /**
     * Joue la musique du menu principal
     */
    public void playMusicMainMenu(){
        stopAllMusic();
        if(!musicMainMenu.isPlaying()){
            musicMainMenu.setVolume(musicVolume);
            musicMainMenu.setLooping(true);
            playMusic(musicMainMenu);
        }
    }

    /**
     * Arrête la musique du menu principal
     */
    public void stopMusicMainMenu(){
        if(musicMainMenu!=null){
            musicMainMenu.stop();
        }
    }

    /**
     * Arrete toutes les musiques
     */
    public void stopAllMusic(){
        stopMusicInGame();
        stopMusicEndGame();
        stopMusicMainMenu();
    }

    /**
     * Joue un son
     * @param pathSound Chemin correspondant
     */
    public void playSound(String pathSound){
        Music sound;
        sound = Gdx.audio.newMusic(Gdx.files.internal(pathSound));
        sound.setVolume(soundVolume);
        playSound(sound);
    }




    /**
     * Joue le son de l'ouverture de la porte
     */
    public void playDoorOpenSound(){
        Music sound;
        sound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Door_Sound.mp3"));
        sound.setVolume(soundVolume);
        playSound(sound);
    }

    public void setMusicVolume(float volume){
        this.musicVolume = volume;
        this.musicMainMenu.setVolume(volume);
    }

    public void setSoundVolume(float volume){
        this.soundVolume = volume;
    }

    public void setMusicEnabled(boolean musicEnabled){
        this.musicEnabled = musicEnabled;
        if (musicEnabled){
            playMusic(musicMainMenu);
        }else {
            musicMainMenu.stop();
        }

    }

    public void setSoundEnabled(boolean soundEnabled){
        this.soundEnabled = soundEnabled;
    }

    /**
     * Vérifie si la musique est activé avant de la jouer
     * @param music
     */
    public void playMusic(Music music){
        if (musicEnabled){
            music.play();
        }
    }

    /**
     * Vérifie si le son est activé avant de le jouer
     * @param sound
     */
    public void playSound(Music sound){
        if (soundEnabled){
            sound.play();
        }
    }

}
