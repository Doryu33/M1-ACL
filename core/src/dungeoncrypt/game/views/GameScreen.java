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
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		police = Police.getInstance();
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
		SoundManager sm = SoundManager.getInstance();
		sm.playMusicInGame();
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera.position.set(RENDER_SCALE*12,RENDER_SCALE*12,0);
		this.viewport = new FitViewport(width-RENDER_SCALE,height-RENDER_SCALE,camera);
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
		int live = roomManager.getActualRoom().getPlayerHP();
		int shield = roomManager.getActualRoom().getPlayerShield();

		batch.begin();
		police.draw(batch, "Score : "+score, SCORE_X,SCORE_Y);
		if(live == 50) {
			batch.draw(new Texture("images/health_bar/greenbar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 45) {
			batch.draw(new Texture("images/health_bar/greenbar90.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 40) {
			batch.draw(new Texture("images/health_bar/greenbar80.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 35) {
			batch.draw(new Texture("images/health_bar/greenbar70.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 30) {
			batch.draw(new Texture("images/health_bar/greenbar60.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 25) {
			batch.draw(new Texture("images/health_bar/orangebar50.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 20) {
			batch.draw(new Texture("images/health_bar/orangebar40.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 15) {
			batch.draw(new Texture("images/health_bar/orangebar30.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 10) {
			batch.draw(new Texture("images/health_bar/redbar20.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live == 5) {
			batch.draw(new Texture("images/health_bar/redbar10.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
		}
		if(live <= 0) {
			batch.draw(new Texture("images/health_bar/deadbar.png"),HEALTH_BAR_X,HEALTH_BAR_Y,400,350);
			parent.changeScreen(ENDGAME);
		}

		if(shield == MAX_SHIELD) {
			batch.draw(new Texture("images/shield_bar/ShieldBar.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD && shield >= MAX_SHIELD*0.9) {
			batch.draw(new Texture("images/shield_bar/ShieldBar90.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.9 && shield >= MAX_SHIELD*0.8) {
			batch.draw(new Texture("images/shield_bar/ShieldBar80.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.8 && shield >= MAX_SHIELD*0.7) {
			batch.draw(new Texture("images/shield_bar/ShieldBar70.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.7 && shield >= MAX_SHIELD*0.6) {
			batch.draw(new Texture("images/shield_bar/ShieldBar60.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.6 && shield >= MAX_SHIELD*0.5) {
			batch.draw(new Texture("images/shield_bar/ShieldBar50.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.5 && shield >= MAX_SHIELD*0.4) {
			batch.draw(new Texture("images/shield_bar/ShieldBar40.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.4 && shield >= MAX_SHIELD*0.3) {
			batch.draw(new Texture("images/shield_bar/ShieldBar30.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.3 && shield >= MAX_SHIELD*0.2) {
			batch.draw(new Texture("images/shield_bar/ShieldBar20.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield < MAX_SHIELD*0.2 && shield >= MAX_SHIELD*0.1) {
			batch.draw(new Texture("images/shield_bar/ShieldBar10.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}
		if(shield == 0) {
			batch.draw(new Texture("images/shield_bar/ShieldBar0.png"),SHIELD_BAR_X,SHIELD_BAR_Y,SHIELD_BAR_WIDTH,SHIELD_BAR_HEIGHT);
		}

		batch.draw(new Texture("images/sword_cooldown/Sword_cooldown_"+roomManager.getActualRoom().getPlayerSwordCooldownStatut()+".png"),roomManager.getActualRoom().getPlayerPosX(),roomManager.getActualRoom().getPlayerPosY(),SWORD_COOLDOWN_WIDTH,SWORD_COOLDOWN_HEIGHT);

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
