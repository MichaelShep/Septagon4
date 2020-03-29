package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.debug.*;
import com.dicycat.kroy.entities.*;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.misc.ButtonListeners;
import com.dicycat.kroy.misc.OverwriteDialog;
import com.dicycat.kroy.misc.SaveManager;
import com.dicycat.kroy.misc.Updater;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.scenes.PauseWindow;
import com.dicycat.kroy.scenes.SaveGameScene;
import sun.security.util.Debug;


/**
 * Contains the main game logic
 * 
 * @author Riju De
 * @author lnt20
 *
 */
public class GameScreen implements Screen{  

	public static enum GameScreenState{ PAUSE, RUN, RESUME, SAVE, OPTIONS }
	
	public Kroy game;
	public GameTextures textures;
	public static float gameTimer; //Timer to destroy station
	public GameScreenState state = GameScreenState.RUN;
	
	public static TiledGameMap gameMap;
	
	private OrthographicCamera gamecam;	//follows along what the port displays
	private Viewport gameport;
	
	private HUD hud; 
	private PauseWindow pauseWindow;
	private OptionsWindow optionsWindow;
	private SaveGameScene saveWindow;

	private Float[][] truckStats = {	//extended
										//Each list is a configuration of a specific truck. {speed, speed + damage , damage , capacity+range, capacity, range}
			{400f, 1f, 400f, 300f},		//Speed
			{350f, 1.25f, 400f, 300f},	//Speed + Flow rate
			{300f, 1.5f, 400f, 300f},	//Flow rate
			{300f, 1f, 450f, 400f},  	//Capacity + Range
			{300f, 1f, 500f, 300f},		//Capacity
			{300f, 1f, 400f, 450f},		//Range
		};
	
	
	private int truckNum; // Identifies the truck thats selected in the menu screen
	private FireTruck currentTruck;
	private int lives = 6;
	private float zoom = 1;

	private int fortressesCount;
	private Vector2 spawnPosition;	//Coordinates the player spawns at
	
	private List<GameObject> gameObjects, deadObjects;	//List of active game objects
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering
	private List<GameObject> objectsToAdd;

	private List<Vector2> fortressPositions, fortressSizes; //where our fortresses spawn

	private ArrayList<FireTruck> firetrucks=new ArrayList<FireTruck>();
	private List<Fortress> fortresses = new ArrayList<Fortress>(); //Added by Septagon - stores all the fortresses in the game
	private List<UFO> ufos = new ArrayList<UFO>(); //Added by Septagon - stores all the UFO's in the game
	private List<PowerUps> powerUps = new ArrayList<>(); //Added by Septagon - stores all the powerUps in the game

	//Used to handle saving and loading states of the game (creates SaveManager) [ID: DECLARE SAVE]
	private SaveManager saveManager;
	private boolean loadingGame = false;
	private DebugRenderer debugRenderer;
	private Updater updater;

	/**
	 * extended
	 * @param _game
	 * @param truckNum
	 */
	public GameScreen(Kroy _game, int truckNum, boolean loadingGame) {
		this.loadingGame = loadingGame;
		game = _game;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);	//Mic:could also use StretchViewPort to make the screen stretch instead of adapt
		hud = new HUD(game.batch, this.game);
		gameMap = new TiledGameMap();										//or FitPort to make it fit into a specific width/height ratio
		pauseWindow = new PauseWindow(game);
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow(game);
		optionsWindow.visibility(false);
		saveWindow = new SaveGameScene(game);
		saveWindow.visibility(false);
		textures = new GameTextures(truckNum);
		spawnPosition = new Vector2(3750, 4000);
		gameTimer = 60 * 5; //new    //Set timer to 5 minutes  
		this.truckNum = truckNum;

		//Setup all the fortress positions and sizes
		fortressPositions = new ArrayList<>();
		fortressPositions.add(new Vector2(2860, 3211));
		fortressPositions.add(new Vector2(3130, 5530));
		fortressPositions.add(new Vector2(2010, 1900));
		fortressPositions.add(new Vector2(4270, 870));
		fortressPositions.add(new Vector2(5940, 1150));
		fortressPositions.add(new Vector2(520, 3500));
		fortressSizes = new ArrayList<>();
		fortressSizes.add(new Vector2(256, 218));
		fortressSizes.add(new Vector2(256, 320));
		fortressSizes.add(new Vector2(400, 256));
		fortressSizes.add(new Vector2(450, 256));
		fortressSizes.add(new Vector2(400, 256));
		fortressSizes.add(new Vector2(450, 256));

		//Initialises the save manager and passes it all the lists that will be needed for saving [ID: INIT SAVE]
		saveManager = new SaveManager(firetrucks, ufos, fortresses, powerUps);
		new ButtonListeners(this, saveManager);
		debugRenderer = new DebugRenderer();
		updater = new Updater(this, saveManager);
	}

	public GameScreen(Kroy _game, int truckNum, boolean loadingGame, Integer saveNum){
	    this(_game, truckNum, loadingGame);
	    saveManager.setSave(saveNum);

    }

	/**
	 * Screen first shown
	 */
	@Override
	public void show() {
		objectsToAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();

		//Checks if we are loading a game from a previous save or starting a completley new game [ID: CHECK LOADING]
		if(!loadingGame) {
			// Initialises the FireTrucks
			for (int i = 0; i < 6; i++) {
				firetruckInit(spawnPosition.x - 135 + (i * 50), spawnPosition.y, i);
				fortressInit(i);
			}
			gameObjects.add(new FireStation());
			powerUps.add(new PowerUps(new Vector2(4344,3729), hud));
			powerUps.add(new PowerUps(new Vector2(4144,3729), hud));
			powerUps.add(new PowerUps(new Vector2(4544,3729), hud));
			powerUps.add(new PowerUps(new Vector2(5055,1415), hud));
			powerUps.add(new PowerUps(new Vector2(1608,585), hud));
			powerUps.add(new PowerUps(new Vector2(1919,3871), hud));

			for(PowerUps p: powerUps)
			{
				gameObjects.add(p);
			}

		}else {
			saveManager.loadAttributes(gameObjects, textures, fortressPositions, fortressSizes, hud);
			gameObjects.add(new FireStation());
		}

		switchTrucks(truckNum);
		gamecam.translate(new Vector2(currentTruck.getX(), currentTruck.getY())); // sets initial Camera position
	}

	/**
	 * new
	 *
	 * Initialises each fortress
	 *
	 * @param num the fortress number
	 */
	private void fortressInit(int num) {
		Fortress fortressToAdd = new Fortress(fortressPositions.get(num), textures.getFortress(num), textures.getDeadFortress(num),
				fortressSizes.get(num));

		Kroy.mainGameScreen.addFortress();
		fortresses.add(fortressToAdd);
		gameObjects.add(fortressToAdd);
	}

	/**
	 * new
	 *
	 * Initialises each truck
	 *
	 * @param num the truck number
	 */
	private void firetruckInit(float x, float y, int num) {
		firetrucks.add(new FireTruck(new Vector2(x, y), truckStats[num], num));
	}

	/**
	 * Called every frame
	 */
	@Override
	public void render(float delta) {
		
		Gdx.input.setInputProcessor(pauseWindow.stage);  //Set input processor
		pauseWindow.stage.act();

		switch (state) {
			case RUN:
				handleRunState(delta);
				break;
			case PAUSE:
				pauseWindow.stage.draw();
				break;
			case RESUME:
				pauseWindow.visibility(false);
				setGameState(GameScreenState.RUN);
				break;
			//Render and update the save screen [ID: DISPLAY SAVE]
            case SAVE:
                Gdx.input.setInputProcessor(saveWindow.stage);
                saveWindow.stage.act();
                saveWindow.stage.draw();
                break;
			default:
				break;
		}
	}

	/**
	 * Used to handle the rendering loop for when the game is in the run state - Refractored by Septagon
	 * @param delta
	 */
	private void handleRunState(float delta)
	{
		if (Gdx.input.isKeyPressed(Keys.P) || Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.M)|| Gdx.input.isKeyPressed(Keys.ESCAPE)){
			pauseWindow.visibility(true);
			pause();
		}

		gameTimer -= delta;		//Decrement timer
		updater.updateLoop(powerUps); //Update all game objects positions but does not render them as to be able to render everything as quickly as possible

		gameMap.renderRoads(gamecam); // Render the background roads, fields and rivers
		gameMap.renderBuildings(gamecam); // Renders the buildings and the foreground items which are not entities

		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		game.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
		game.batch.begin(); // Game loop Start

		hud.update(delta);
		renderObjects(); // Renders objects specified in the UpdateLoop() called previously
		game.batch.end();
		hud.stage.draw();
		pauseWindow.stage.draw();

		if (Kroy.debug) {
			debugRenderer.DrawDebug(gamecam); //Draw all debug items as they have to be drawn outside the batch
		}
	}
	
	/**
	 * new
	 * Can zoom in the game by pressing EQUALS key
	 * Can zoom out by pressing MINUS key
	 */
	public void checkZoom() {
		if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
			if (zoom > 0.5f) {
				zoom = zoom - 0.5f;
			}
			gamecam.setToOrtho(false, Kroy.width * zoom, Kroy.height * zoom);
			gamecam.translate(new Vector2(currentTruck.getX() - ((Kroy.width * zoom) / 2),
					currentTruck.getY() - ((Kroy.height * zoom) / 2)));
		}
		if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
			if (zoom < 4f) {
				zoom = zoom + 0.5f;
			}
			gamecam.setToOrtho(false, Kroy.width * zoom, Kroy.height * zoom);
			gamecam.translate(new Vector2(currentTruck.getX() - ((Kroy.width * zoom) / 2),
					currentTruck.getY() - ((Kroy.height * zoom) / 2)));
		}
	}

	/**
	 * Renders the objects in "objectsToRender" then clears the list
	 */
	private void renderObjects() {
		for (GameObject object : objectsToRender) {
			object.render(game.batch);
		}
		for (FireTruck truck : firetrucks) {
			if(truck.isAlive()) {
			truck.render(game.batch);
			}
		}

		objectsToRender.clear();
	}

	/**
	 * Add a game object next frame
	 * @param gameObject gameObject to be added
	 */
	public void addGameObject(GameObject gameObject) {
		objectsToAdd.add(gameObject);
	}

	/**
	 * Allows external classes to access the player
	 * @return player
	 */
	public FireTruck getPlayer() {
		return currentTruck;
	}

	/**
	 * Updates the position of the camera to have the truck centre
	 * Ensures it never goes out of bounds, including when zoomed
	 * It does this by limiting the bounds of the camera
	 */ 
	public void updateCamera() {
		//currentTruck;
		float cameraX = Math.max(0.5f*Kroy.width*zoom, Math.min(currentTruck.getX(), 6884-(0.5f*Kroy.width*zoom)));
		float cameraY = Math.max(0.5f*Kroy.height*zoom, Math.min(currentTruck.getY(), 6043-(0.5f*Kroy.height*zoom)));
		gamecam.position.lerp(new Vector3(cameraX, cameraY,gamecam.position.z),0.1f);// sets the new camera position based on the current position of the FireTruck
		gamecam.update();
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
	}

	@Override
	public void pause() {
		setGameState(GameScreenState.PAUSE);
	}

	@Override
	public void resume() {
		setGameState(GameScreenState.RESUME);
	}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		Kroy.mainGameScreen = null;
	}

	/**
	 * @param s
	 */
	public void setGameState(GameScreenState s){
	    state = s;
	}

	/**
	 * @param index
	 * @return
	 */
	public GameObject getGameObject(int index) {
		if (index <= (gameObjects.size()-1)) {
			return gameObjects.get(index);
		}else {
			return null;
		}
	}
	
	/**
	 * @return the number of alive trucks
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Method that will set up the save manager for the current save and save all neccessary values [ID: SAVE PERFORM]
	 * @param saveNumber The save slot that is being used for the save
	 */
	public void performSave(int saveNumber)
	{
		saveManager.setSave(saveNumber);
		saveManager.updateSavedEntities(firetrucks, ufos, fortresses, powerUps);
		saveManager.saveAttributes();
		this.setGameState(GameScreen.GameScreenState.PAUSE);
	}

	/**
	 * Add one fortress to the count
	 */
	public void addFortress() {
		fortressesCount++;
	}

	/**
	 * Remove one fortress to the count
	 */
	public void removeFortress() {
		fortressesCount--;
		System.out.println("Fortresses Left: " + fortressesCount);

	}

	/**
	 * How many fortresses are left?
	 * @return Number of fortresses remaining
	 */
	public int fortressesLeft() {
		return fortressesCount;
	}

	/**
	 * Switch to the game over screen
	 * @param won Did the player reach the win state?
	 */
	public void gameOver(boolean won) {
		game.setScreen(new GameOverScreen(game, truckNum, won));
	}

	/**
	 * extended
	 * switch to next truck if currentTruck dies
	 */
	public void updateLives() {
		if (lives>1) {
			lives -= 1;
			if(firetrucks.get(0).isAlive()) {
				switchTrucks(0);
			}else if(firetrucks.get(1).isAlive()) {
				switchTrucks(1);
			}else if(firetrucks.get(2).isAlive()) {
				switchTrucks(2);
			}else if(firetrucks.get(3).isAlive()) {
				switchTrucks(3);
			}else if(firetrucks.get(4).isAlive()) {
				switchTrucks(4);
			}else if(firetrucks.get(5).isAlive()) {
				switchTrucks(5);
			}
		} else {
			gameOver(false);
		}
	}
	
	/**
	 * new
	 * switch to FireTruck number n by calling changeToTruck function
	 * @param n
	 */
	public void switchTrucks(int n) {
		changeToTruck(firetrucks.get(n));
	}

	/**
	 * new
	 * Check for inputs to switch between trucks.
	 * It only works if the truck that has chosen is alive
	 */
	public void switchTrucks() {
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			if(firetrucks.get(0).isAlive())
				changeToTruck(firetrucks.get(0));
		} 
		else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			if(firetrucks.get(1).isAlive())
				changeToTruck(firetrucks.get(1));
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			if(firetrucks.get(2).isAlive())
				changeToTruck(firetrucks.get(2));
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			if(firetrucks.get(3).isAlive())
				changeToTruck(firetrucks.get(3));
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
			if(firetrucks.get(4).isAlive())
				changeToTruck(firetrucks.get(4));
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
			if(firetrucks.get(5).isAlive())
				changeToTruck(firetrucks.get(5));
		}

	}

	/**
	 * new
	 * Switches the camera to the specified truck.
	 *
	 * @param t The truck to switch to
	 */
	private void changeToTruck(FireTruck t) {
		currentTruck = t;

	}  

	/**
	 * @return hud
	 */
	public HUD getHud(){
		return hud;
	}

	/** 
	 * @return spawnPosition
	 */
	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}

	public PauseWindow getPauseWindow() { return pauseWindow; }
	public SaveGameScene getSaveWindow() { return saveWindow; }
	public Kroy getGame() { return game; }
	public DebugRenderer getDebugRenderer() { return debugRenderer; }
	public List<GameObject> getGameObjects() { return gameObjects; }
	public List<GameObject> getObjectsToRender() { return objectsToRender; }
	public List<GameObject> getDeadObjects() { return deadObjects; }
	public List<GameObject> getObjectsToAdd() { return objectsToAdd; }
	public List<UFO> getUfos() { return ufos; }
	public FireTruck getCurrentTruck() { return currentTruck; }
}
