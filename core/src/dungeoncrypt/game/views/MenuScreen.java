package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dungeoncrypt.game.data.SoundManager;
import org.w3c.dom.Text;

import java.io.File;

import static dungeoncrypt.game.data.Data.*;

public class MenuScreen implements Screen {



    private ScreenManager parent;
    private Stage stage;
    private final Stage stageHelp;
    private TextButton loadGame;
    private TextButton continuGame;
    private boolean isHelpShown = false;

    public MenuScreen(ScreenManager screenManager){
        /* Definition du parent */
        parent = screenManager;

        /*Music du menu principal*/
        final SoundManager soundManager = SoundManager.getInstance();


        /* Creation du stage */
        stage = new Stage(new ScreenViewport());
        stageHelp = new Stage(new ScreenViewport());


        /* Creation de la table pour les items du menu */
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        /* Chargement du skin du menu */
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        /* Creation des boutons du menu */
        TextButton newGame = new TextButton("Nouvelle Partie", skin);
        continuGame = new TextButton("Continuer la partie", skin);
        loadGame = new TextButton("Charger une partie", skin);
        TextButton preferences = new TextButton("Options", skin);
        TextButton exit = new TextButton("Quitter", skin);
        TextButton help = new TextButton("Instructions", skin);


        /* Remplissage de la table pour l'affichage du menu */
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(newGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(continuGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(loadGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(help).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);;
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(preferences).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
        table.add(exit).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
        /* Definition du background du menu */
        Texture background = new Texture("images/background.jpg");
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

        /* Bouton continuer une partie */
        continuGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(CONTINUE_LOAD_APPLICATION);
            }
        });

        /* Bouton charger une partie */
        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(LOAD_APPLICATION);
            }
        });

        /* Bouton option */
        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(PREFERENCES);
            }
        });

        /* Bouton aide */
        help.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isHelpShown = !isHelpShown;
            }
        });

        /* Creation de la table pour les items du menu */
        Table tableHelp = new Table();
        tableHelp.setFillParent(true);
        //table.setDebug(true);
        stageHelp.addActor(tableHelp);

        TextButton back = new TextButton("Retour", skin);
        /* Bouton aide */
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isHelpShown = !isHelpShown;
            }
        });

        Image image = new Image(new Texture(Gdx.files.internal("images/help.png")));
        tableHelp.add(image).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.7f);
        tableHelp.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);;
        tableHelp.add(back);
    }

    @Override
    public void show() {
        File saveFile = new File(SAVE_NAME+".txt");
        boolean saveExist = (saveFile.exists() && !saveFile.isDirectory());
        loadGame.setDisabled(!saveExist);

        File autoSaveFile = new File(AUTO_SAVE_NAME+".txt");
        boolean autoSaveExist = (autoSaveFile.exists() && !autoSaveFile.isDirectory());
        continuGame.setDisabled(!autoSaveExist);

        Gdx.input.setInputProcessor(stage);
        parent.getSm().playMusicMainMenu();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if(isHelpShown){
            Gdx.input.setInputProcessor(stageHelp);
            stageHelp.draw();
        } else {
            Gdx.input.setInputProcessor(stage);
        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stageHelp.getViewport().update(width, height, true);
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
        stageHelp.dispose();
    }
}
