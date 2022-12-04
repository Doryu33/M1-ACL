package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dungeoncrypt.game.data.SoundManager;

import static dungeoncrypt.game.data.Data.*;

public class EndScreen implements Screen {

    private ScreenManager parent;
    private Stage stage;
    public EndScreen(ScreenManager screenManager){
        /* Definition du parent */
        parent = screenManager;

        /* Creation du stage */
        stage = new Stage(new ScreenViewport());

        /* Creation de la table pour les items du menu */
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        /* Definition du background du menu */
        Texture background = new Texture("images/background.jpg");
        table.setBackground(new TextureRegionDrawable(new TextureRegion(background)));

        /* Chargement du skin du menu */
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* Creation des boutons du menu */
        TextButton newGame = new TextButton("Nouvelle Partie", skin);
        TextButton exit = new TextButton("Quitter", skin);
        /* Remplissage des labels */
        Label titleLab = new Label("Game Over", skin);
        /* Remplissage de la table */
        table.add(titleLab).colspan(2);
        table.row().pad(PADDING_TOP, 0, PADDING_DOWN, 0);
        table.add(newGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(exit).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);

        /* Bouton quitter */
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        /* Bouton nouvelle partie */
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(APPLICATION);
                SoundManager soundManager = SoundManager.getInstance();
                soundManager.stopMusicEndGame();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        /* Changement du son */
        this.parent.getSm().playMusicEndGame();
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
