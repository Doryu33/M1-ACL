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
import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.room.RoomManager;
import dungeoncrypt.game.tools.Police;
import dungeoncrypt.game.tools.SaveManager;

import java.io.File;

import static dungeoncrypt.game.data.Data.*;

public class GameScreen implements Screen {
	private final Texture logoHPTexture;
	private final Texture emptyHPBarTexture;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer b2dr;
	private RoomManager roomManager;
	private Stage stage;
	private Viewport viewport;
	private Police police;
	private ScreenManager parent;

	public GameScreen(ScreenManager screenManager){
		parent = screenManager;
		batch = new SpriteBatch();
		police = Police.getInstance();

		/**
		 * Camera et Viewport
		 */
		camera = new OrthographicCamera();
		viewport = new FitViewport(ROOM_SIZE*RENDER_SCALE,ROOM_SIZE*RENDER_SCALE,camera);

		logoHPTexture = new Texture("images/healthPoint/LogoHP.png");
		emptyHPBarTexture = new Texture("images/healthPoint/EmptyBar.png");
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
		SoundManager sm = SoundManager.getInstance();
		sm.playMusicInGame();

		/**
		 * Création et gestion du monde monde
		 */
		this.world = new World(new Vector2(0,0), false);
		//Creaion de l'écouteur de collision
		this.world.setContactListener(new BodyContactListenner(this));
		/**
		 * Stage
		 */
		this.stage = new Stage(viewport,batch);

		/**
		 * Debug mode
		 */
		if(DEBUG_MODE){
			b2dr = new Box2DDebugRenderer();
		}

		/**
		 * RoomManager
		 * Si saveManager est null, alors on crée une nouvelle partie.
		 * Sinon on charge la partie si un fichier de sauvegarde existe.
		 */
		if(parent.getSaveManager() == null){
			this.roomManager = new RoomManager(world, stage);
		} else {
			this.roomManager = new RoomManager(world,stage, parent.getSaveManager());
		}
	}

	/**
	 * Getter
	 * @return roomManager
	 */
	public RoomManager getRoomManager() {
		return roomManager;
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
			stage.getViewport().apply();
			stage.draw();
		}

		int score = roomManager.getActualRoom().getPlayerScore();
		int playerHP = roomManager.getActualRoom().getPlayerHP();

		batch.begin();
		police.drawScore(batch, "Score : "+score, SCORE_X,SCORE_Y);

		float ratio = playerHP / (float) PLAYER_INITIAL_HP;
		float widthOfBar = MAX_SIZE_HEALTH_BAR * ratio;

		batch.draw(logoHPTexture, HEALTH_BAR_X-LOGO_HEALTH_BAR_WIDTH,EMPTY_HEALTH_BAR_Y,LOGO_HEALTH_BAR_WIDTH,EMPTY_HEALTH_BAR_HEIGHT);
		batch.draw(emptyHPBarTexture,HEALTH_BAR_X,EMPTY_HEALTH_BAR_Y,MAX_SIZE_HEALTH_BAR,EMPTY_HEALTH_BAR_HEIGHT);

		if(ratio >= 0.5f){
			batch.draw(new Texture("images/healthPoint/GreenBar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,widthOfBar,HEALTH_BAR_HEIGHT);
		}else if(ratio >= 0.3f){
			batch.draw(new Texture("images/healthPoint/OrangeBar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,widthOfBar,HEALTH_BAR_HEIGHT);
		}else{
			batch.draw(new Texture("images/healthPoint/RedBar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,widthOfBar,HEALTH_BAR_HEIGHT);
		}
		police.drawHP(batch, String.valueOf(playerHP), MAX_SIZE_HEALTH_BAR/2f,SCORE_Y-6);

		if(playerHP <= 0){
			parent.changeScreen(ENDGAME);
		}

		batch.end();
	}

	public void resize(int width, int height){
		viewport.update(width,height);
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
		}
		stage.dispose();
	}

	public void update(float delta){
		world.step(1/60f, 6, 2);
		roomManager.updatePositionRoom();
	}

	public World getworld() {
		return this.world;
	}
}
