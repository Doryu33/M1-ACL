package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dungeoncrypt.game.data.AppPreferences;

import static dungeoncrypt.game.data.Data.*;

public class PreferencesScreen implements Screen {

    private final ScreenManager parent;
    private final Stage stage;
    private Label titleLab;
    private Label volumeMusicLab;
    private Label volumeSoundLabel;
    private Label musicOnOffLab;
    private Label soundOnOffLab;

    public PreferencesScreen(ScreenManager screenManager){
        parent = screenManager;
        final AppPreferences appPref = new AppPreferences();

        /* Chargement du skin du menu */
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* Gestion Volume Musique */
        final Slider volumeMusicSlider = new Slider(0f,1f,0.1f,false,skin);
        volumeMusicSlider.setValue(appPref.getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                appPref.setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });
        /* Gestion Musique ON/OFF*/
        final CheckBox enableMusicBox = new CheckBox(null, skin);
        enableMusicBox.setChecked(appPref.isMusicEnabled());
        enableMusicBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isMusicOn = enableMusicBox.isChecked();
                appPref.setMusicEnabled(isMusicOn);
                return false;
            }
        });
        /* Gestion Volume des Sons/Effets */
        final Slider volumeSoundEffectsSlider = new Slider(0f,1f,0.1f,false,skin);
        volumeSoundEffectsSlider.setValue(appPref.getSoundVolume());
        volumeSoundEffectsSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                appPref.setSoundVolume(volumeSoundEffectsSlider.getValue());
                return false;
            }
        });
        /* Gestion Musique ON/OFF*/
        final CheckBox enableSoundEffectBox = new CheckBox(null, skin);
        enableSoundEffectBox.setChecked(appPref.isSoundEffectsEnabled());
        enableSoundEffectBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean isSoundEffectOn = enableSoundEffectBox.isChecked();
                appPref.setSoundEffectsEnabled(isSoundEffectOn);
                return false;
            }
        });
        /* Retour au menu principale */
        final TextButton backMainMenu = new TextButton("Retour",skin);
        backMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MENU);
            }
        });

        /* Remplissage des labels */
        titleLab = new Label("Options", skin);
        volumeMusicLab = new Label("Volume de la Musique", skin);
        volumeSoundLabel = new Label("Volume des effets sonnores", skin);
        musicOnOffLab = new Label("Musique", skin);
        soundOnOffLab = new Label("Effets sonnores", skin);
        /* Creation du stage */
        stage = new Stage(new ScreenViewport());;
        /* Creation de la table pour les items du menu */
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        /* Remplissage de la table */
        table.add(titleLab).colspan(2);
        table.row().pad(PADDING_TOP, 0, PADDING_DOWN, 0);
        table.add(volumeMusicLab).fillX().uniformX().left();
        table.add(volumeMusicSlider).fillX().uniformX();
        table.row().pad(PADDING_TOP, 0, PADDING_DOWN, 0);
        table.add(musicOnOffLab).fillX().uniformX();
        table.add(enableMusicBox).fillX().uniformX();
        table.row().pad(PADDING_TOP, 0, PADDING_DOWN, 0);
        table.add(volumeSoundLabel).fillX().uniformX();
        table.add(volumeSoundEffectsSlider).fillX().uniformX();
        table.row().pad(PADDING_TOP, 0, PADDING_DOWN, 0);
        table.add(soundOnOffLab).fillX().uniformX();
        table.add(enableSoundEffectBox).fillX().uniformX();
        table.row().pad(PADDING_TOP, 0, PADDING_DOWN, 0);
        table.add(backMainMenu).fillX().uniformX().colspan(2).maxWidth(BUTTON_MAXWIDTH);
        /* Definition du background du menu */
        Texture background = new Texture("images/background.jpg");
        table.setBackground(new TextureRegionDrawable(new TextureRegion(background)));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
