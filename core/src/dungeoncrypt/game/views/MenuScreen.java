package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import static dungeoncrypt.game.data.Data.*;

public class MenuScreen implements Screen {

    private Main parent;
    private Stage stage;

    public MenuScreen(Main main){
        /* Definition du parent */
        parent = main;

        /* Creation du stage */
        stage = new Stage(new ScreenViewport());


        /* Creation de la table pour les items du menu */
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        /* Chargement du skin du menu */
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* Creation des boutons du menu */
        TextButton newGame = new TextButton("Nouvelle Partie", skin);
        TextButton loadGame = new TextButton("Charger une partie", skin);
        TextButton preferences = new TextButton("Options", skin);
        TextButton exit = new TextButton("Quitter", skin);



        /* Remplissage de la table pour l'affichage du menu */
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(newGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(loadGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(preferences).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(exit).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        /* Definition du background du menu */
        Texture background = new Texture("background.jpg");
        table.setBackground(new TextureRegionDrawable(new TextureRegion(background)));


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
            }
        });

        /* Bouton option */
        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(PREFERENCES);
            }
        });
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
