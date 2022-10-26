package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import dungeoncrypt.game.room.RoomManager;

import static dungeoncrypt.game.data.Data.*;

public class GameScreen implements Screen {
	private OrthographicCamera camera;

	private World world;
	private Box2DDebugRenderer b2dr;

	private RoomManager roomManager;

	private Stage stage;

	private Main parent;
	public GameScreen(Main main){
		parent = main;
	}

	@Override
	public void show () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		this.world = new World(new Vector2(0,0), false);

		b2dr = new Box2DDebugRenderer();

		this.stage = new Stage();

		this.roomManager = new RoomManager(world, stage);


	}

	@Override
	public void render (float delta) {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		b2dr.render(world, camera.combined);
	}

	public void resize(int width, int height){
		camera.setToOrtho(false, width, height);
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

	public void dispose () {
		world.dispose();
		stage.dispose();
		b2dr.dispose();
	}

	public void update(float delta){
		world.step(1/60f, 6, 2);
		roomManager.updatePositionRoom();
	}
}
