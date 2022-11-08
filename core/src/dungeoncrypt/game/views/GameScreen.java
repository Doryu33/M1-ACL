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
import dungeoncrypt.game.tools.Police;

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
	private Police police;
	public GameScreen(Main main){
		parent = main;
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		police = Police.getInstance();
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
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
		if(DEBUG_MODE){
			b2dr = new Box2DDebugRenderer();
		}

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



		if(DEBUG_MODE){
			b2dr.render(world, camera.combined);
		}else{
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}

		int live = roomManager.getActualRoom().getPlayer().getHealthPoint();
		int score = roomManager.getActualRoom().getPlayer().getScore();

		batch.begin();
		police.draw(batch, "Score : "+score, SCORE_X,SCORE_Y);
		if(live == 50) {
			batch.draw(new Texture("images/greenbar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 45) {
			batch.draw(new Texture("images/greenbar90.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 40) {
			batch.draw(new Texture("images/greenbar80.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 35) {
			batch.draw(new Texture("images/greenbar70.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 30) {
			batch.draw(new Texture("images/greenbar60.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 25) {
			batch.draw(new Texture("images/orangebar50.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 20) {
			batch.draw(new Texture("images/orangebar40.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 15) {
			batch.draw(new Texture("images/orangebar30.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 10) {
			batch.draw(new Texture("images/redbar20.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 5) {
			batch.draw(new Texture("images/redbar10.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live <= 0) {
			batch.draw(new Texture("images/deadbar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
			parent.changeScreen(ENDGAME);
		}
		batch.end();



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
		if(DEBUG_MODE){
			b2dr.dispose();
		}else{
			stage.dispose();
		}
	}

	public void update(float delta){
		world.step(1/60f, 6, 2);
		roomManager.updatePositionRoom();
	}
}
