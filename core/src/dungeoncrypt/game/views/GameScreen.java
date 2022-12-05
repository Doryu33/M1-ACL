package dungeoncrypt.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.*;
import dungeoncrypt.game.collisions.BodyContactListenner;
import dungeoncrypt.game.data.SoundManager;
import dungeoncrypt.game.entities.monsters.Boss;
import dungeoncrypt.game.room.RoomManager;
import dungeoncrypt.game.tools.Police;

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
	private Stage pauseStage;
	private Viewport viewport;
	private Police police;
	private ScreenManager parent;
	private boolean gameIsPaused;
	private TextButton saveGame;

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
		this.gameIsPaused = false;
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
		this.parent.getSm().playMusicInGame();

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
		this.pauseStage = new Stage(viewport,batch);



		/**
		 * RoomManager
		 * Si saveManager est null, alors on crée une nouvelle partie.
		 * Sinon on charge la partie si un fichier de sauvegarde existe.
		 */
		if(parent.getSaveManager() == null){
			this.parent.initSaveManager();
			this.roomManager = new RoomManager(world, stage);
		} else {
			this.roomManager = new RoomManager(world,stage, parent.getSaveManager());
		}


		/**
		 * Pause menu
		 */
		/* Creation de la table pour les items du menu */
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(DEBUG_MODE);
		pauseStage.addActor(table);
		/* Chargement du skin du menu */
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		/* Creation des boutons du menu */
		TextButton continuGame = new TextButton("Continuer la partie", skin);
		saveGame = new TextButton("Sauvegarder la partie", skin);
		TextButton exit = new TextButton("Retour au Menu Principale", skin);

		/* Remplissage de la table pour l'affichage du menu */
		table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
		table.add(continuGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
		table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
		table.add(saveGame).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);
		table.row().pad(PADDING_TOP, PADDING_LEFT, PADDING_DOWN, PADDING_RIGHT);
		table.add(exit).fillX().uniformX().growX().minWidth(BUTTON_MINWIDTH).maxWidth(BUTTON_MAXWIDTH);

		/* Bouton retour menu */
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(MENU);
				gameIsPaused = false;
			}
		});
		/* Bouton continuer une partie */
		continuGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gameIsPaused = false;
			}
		});

		/* Bouton sauvegarder une partie */
		saveGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(roomManager.getActualRoom().isEmpty()){
					parent.getSaveManager().saveProgression("DungeonCrypt-Save",roomManager.getActualRoom());
					SoundManager.getInstance().playSound("sounds/GameSaved.mp3");
				}
			}
		});

		/**
		 * Debug mode
		 */
		if(DEBUG_MODE){
			b2dr = new Box2DDebugRenderer();
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

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			gameIsPaused = !gameIsPaused;
		}

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		stage.act(Gdx.graphics.getDeltaTime());
		stage.getViewport().apply();
		stage.draw();

		if(gameIsPaused){
			pause();
		} else {
			resume();
		}

		int score = roomManager.getActualRoom().getPlayerScore();
		int playerHP = roomManager.getActualRoom().getPlayerHP();
		Boss boss = roomManager.getActualRoom().getBoss();

		batch.begin();
		police.drawScore(batch, "Score : "+score, SCORE_X,SCORE_Y);

		if(boss != null){
			int bossHP = boss.getHealthPoint();
			float posXBoss = boss.getPosX()-RENDER_SCALE_BOSS/2f+RENDER_SCALE;
			float posYBoss = boss.getPosY()+RENDER_SCALE_BOSS/2f-RENDER_SCALE;
			float ratioBossHP = bossHP / (float) BOSS_INITIAL_HP;
			float widthOfBarBoss = MAX_SIZE_HEALTH_BAR * ratioBossHP;
			if(ratioBossHP > 0.5f){
				batch.draw(new Texture("images/healthPoint/GreenBar.png"),posXBoss,posYBoss,widthOfBarBoss,HEALTH_BAR_HEIGHT);
			}else{
				batch.draw(new Texture("images/healthPoint/RedBar.png"),posXBoss,posYBoss,widthOfBarBoss,HEALTH_BAR_HEIGHT);
			}
		}

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
		if(DEBUG_MODE){
			b2dr.render(world, camera.combined);
		}
	}

	public void resize(int width, int height){
		viewport.update(width,height);
	}

	@Override
	public void pause() {


		if(roomManager.getActualRoom().isEmpty()){
			this.saveGame.setDisabled(false);
		} else {
			this.saveGame.setDisabled(true);
		}
		Gdx.input.setInputProcessor(pauseStage);
		stage.getViewport().apply();
		pauseStage.draw();
	}

	@Override
	public void resume() {
		Gdx.input.setInputProcessor(stage);
		update(Gdx.graphics.getDeltaTime());
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
		pauseStage.dispose();
	}

	public void update(float delta){
		world.step(1/60f, 6, 2);
		roomManager.updatePositionRoom();
	}

	public World getworld() {
		return this.world;
	}
}
