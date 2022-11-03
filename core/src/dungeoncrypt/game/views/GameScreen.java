package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;
import dungeoncrypt.game.collisions.BodyContactListenner;
import dungeoncrypt.game.entities.Player;
import dungeoncrypt.game.room.RoomManager;

import static dungeoncrypt.game.data.Data.*;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private World world;
	private Box2DDebugRenderer b2dr;

	private RoomManager roomManager;

	private Stage stage;
	private Viewport viewport;
	private Main parent;
	public GameScreen(Main main){
		parent = main;
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
	}

	@Override
	public void show () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera.position.set(RENDER_SCALE*12,RENDER_SCALE*12,0);
		this.viewport = new FitViewport(width,height,camera);
		viewport.apply();

		/**
		 * Création et gestion du monde monde
		 */
		this.world = new World(new Vector2(0,0), false);
		//Creaion de l'écouteur de collision
		this.world.setContactListener(new BodyContactListenner(this));


		/**
		 * Stage
		 */
		this.stage = new Stage();

		/**
		 * Debug mode
		 */
		b2dr = new Box2DDebugRenderer();

		/**
		 * RoomManager
		 */
		this.roomManager = new RoomManager(world, stage);


	}

	@Override
	public void render (float delta) {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		int live = roomManager.getActualRoom().getPlayer().getHealthPoint();

		batch.begin();
		if(live == 50) {
			batch.draw(new Texture("images/greenbar.png"),400,435,400,350);
		}
		if(live == 45) {
			batch.draw(new Texture("images/greenbar90.png"),400,435,400,350);
		}
		if(live == 40) {
			batch.draw(new Texture("images/greenbar80.png"),400,435,400,350);
		}
		if(live == 35) {
			batch.draw(new Texture("images/greenbar70.png"),400,435,400,350);
		}
		if(live == 30) {
			batch.draw(new Texture("images/greenbar60.png"),400,435,400,350);
		}
		if(live == 25) {
			batch.draw(new Texture("images/orangebar50.png"),400,435,400,350);
		}
		if(live == 20) {
			batch.draw(new Texture("images/orangebar40.png"),400,435,400,350);
		}
		if(live == 15) {
			batch.draw(new Texture("images/orangebar30.png"),400,435,400,350);
		}
		if(live == 10) {
			batch.draw(new Texture("images/redbar20.png"),400,435,400,350);
		}
		if(live == 5) {
			batch.draw(new Texture("images/redbar10.png"),400,435,400,350);
		}
		if(live <= 0) {
			batch.draw(new Texture("images/deadbar.png"),400,435,400,350);
		}
		batch.end();



		//b2dr.render(world, camera.combined);
	}

	public void resize(int width, int height){
		viewport.update(width,height);
		camera.position.set(RENDER_SCALE*12,RENDER_SCALE*12,0);
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
