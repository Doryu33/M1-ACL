package dungeoncrypt.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class AppPreferences {
    //Constantes pour les options du jeu
    private static final String MUSIC_VOLUME = "volume";
    private static final String MUSIC_ENABLED = "music.enabled";
    private static final String SOUND_ENABLED = "sound.enabled";
    private static final String SOUND_VOL = "sound";
    private static final String NAME = "Name";

    private SoundManager soundManager = SoundManager.getInstance();


    public Preferences getPrefs() {
        return Gdx.app.getPreferences(NAME);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
        soundManager.setSoundEnabled(isSoundEffectsEnabled());
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
        soundManager.setMusicEnabled(isMusicEnabled());
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(MUSIC_VOLUME, volume);
        getPrefs().flush();
        soundManager.setMusicVolume(getMusicVolume());
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(SOUND_VOL, volume);
        getPrefs().flush();
        soundManager.setSoundVolume(getSoundVolume());
    }
}
