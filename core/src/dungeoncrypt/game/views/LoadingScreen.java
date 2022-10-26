package dungeoncrypt.game.views;

import com.badlogic.gdx.Screen;
import static dungeoncrypt.game.data.Data.*;

public class LoadingScreen implements Screen {
    private Main parent;
    public LoadingScreen(Main main){
        parent = main;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.changeScreen(MENU);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
