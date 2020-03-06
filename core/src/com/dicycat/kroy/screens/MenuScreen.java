package com.dicycat.kroy.screens;
  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.*;
import com.dicycat.kroy.DifficultyMultiplier;

/**
 * Main Menu screen
 * 
 * @author Michele Imbriani
 *
 */
public class MenuScreen implements Screen{
  
  private Kroy game; 
  private OrthographicCamera gamecam;
  private Viewport gameport;
  private Texture playButton, 
  	playButtonActive, 
  	optionsButton, 
  	optionsButtonActive, 
  	exitButton, 
  	exitButtonActive, 
  	minigameButton, 
  	minigameButtonActive,
	loadGameButton,
	loadGameButtonActive,
  	background;
  
  private Stage stage;
  
  private OptionsWindow optionsWindow;
  
  public static Music music = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
  public static float musicVolume = 0.4f;

  //coordinates for Play and Exit buttons 
  private int buttonWidth = 250;
  private int buttonHeight = 50;
  private int xAxisCentred = (Kroy.width/2) - (buttonWidth/2);
  private int playButtonY = (Kroy.height/2)+150;
  private int loadGameButtonY = (Kroy.height/2)+75;
  private int optionsButtonY = (Kroy.height/2);
  private int minigameButtonY = (Kroy.height/2)-75;
  private int exitButtonY = (Kroy.height/2)-150;
  
  private Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
  private int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
  private int yHotSpot = 0;

  //SEPTAGON
  private DifficultyScene difficultySelector;
  private LoadGameScene loadGameSelector;

  private FireTruckSelectionScene fireTruckSelector;
  private boolean currentlyRunningGame = false;

  //Boolean to keep check of whether the user is loading a game or starting a new game - Septagon
  private boolean loadingGame = false;

  /**
   *  Used to define the current state of the screen, 
   *  MAINMENU is used mostly but then TRUCKSELECT used when the "NewGame" button has been pressed
   */
  public static enum MenuScreenState {
	  MAINMENU,
	  DIFFICULTYSELECT,
	  LOADGAMESELECT,
	  TRUCKSELECT,
	  OPTIONS
  }
  
  public MenuScreenState state = MenuScreenState.MAINMENU;
  
  /**
   * @param game
   */
  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  exitButton = new Texture("EXIT.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  exitButtonActive = new Texture("exitActive.png");
	  optionsButton = new Texture("options.png");
	  optionsButtonActive = new Texture("optionsActive.png");
	  playButton = new Texture("newgame.png");
	  playButtonActive = new Texture("newActive.png");
	  minigameButton = new Texture("minigame.png");
	  minigameButtonActive = new Texture("minigameActive.png");
	  loadGameButton = new Texture("load_game.png");
	  background = new Texture ("fireforce.png");
	  
	  gamecam = new OrthographicCamera();
	  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
	  stage = new Stage(gameport);

	  //SEPTAGON
	  difficultySelector = new DifficultyScene(game);
	  difficultySelector.visibility(false);

	  loadGameSelector = new LoadGameScene(game);
	  loadGameSelector.visibility(false);
	  //

	  fireTruckSelector = new FireTruckSelectionScene(game);
	  fireTruckSelector.visibility(false);
	  
	  music.play();
	  music.setLooping(true);  
	  music.setVolume(musicVolume);  
	  
	  optionsWindow = new OptionsWindow(game);
	  optionsWindow.visibility(false);
	  
  }
  
  @Override 
  public void show() {}
  
  /**
   *	Enum allows to make the MenuScreen behave differently depending 
   *	on whether it's in mainMenu, Options or fireTruckSelection
   */
  @Override 
  public void render(float delta) { 
	  
	  switch(state) {
		  case MAINMENU:	// Display all buttons and the main menu		  
			  stage.act();	//allows the stage to interact with user input
			  
			  game.batch.setProjectionMatrix(gamecam.combined);
			  game.batch.begin();
			  
			  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
			  game.batch.draw(background, 0, 0);
			 
			  game.batch.draw(minigameButton, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
			  game.batch.draw(loadGameButton, xAxisCentred, loadGameButtonY, buttonWidth, buttonHeight);
			
			
			  //for play button: checks if the position of the cursor is inside the coordinates of the button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > playButtonY ) && (Kroy.height - Gdx.input.getY() < (playButtonY + buttonHeight)) ) ){
				  game.batch.draw(playButtonActive, xAxisCentred, playButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  this.dispose();
					  game.batch.end();
					  difficultySelector.visibility(true);// display the difficulty selection window
					  loadingGame = false;
					  setGameState(MenuScreenState.DIFFICULTYSELECT);// set the game state to run and run the selection screen code
					  return;
				  }
			  } else {
				  game.batch.draw(playButton, xAxisCentred, playButtonY, buttonWidth, buttonHeight);
			  }

			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > loadGameButtonY) && (Kroy.height - Gdx.input.getY() < (loadGameButtonY + buttonHeight)) ) ){
			  	if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			  		loadGameSelector.visibility(true);
			  		setGameState(MenuScreenState.LOADGAMESELECT);//set game state to save selector
				}else{
				}
			  }
			  
			//for exit button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > exitButtonY ) && (Kroy.height - Gdx.input.getY() < (exitButtonY + buttonHeight)) ) ){
				  game.batch.draw(exitButtonActive, xAxisCentred, exitButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  Gdx.app.exit();
				  }
			  } else {
				  game.batch.draw(exitButton, xAxisCentred, exitButtonY, buttonWidth, buttonHeight);
			  }
				
			  //for minigame button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > minigameButtonY ) && (Kroy.height - Gdx.input.getY() < (minigameButtonY + buttonHeight)) ) ){
				  game.batch.draw(minigameButtonActive, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  startMinigame();
						  }
					  } else {
						  game.batch.draw(minigameButton, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
					  }
	
						  //for options button
			  if(( (Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred) ) && ( (Kroy.height - Gdx.input.getY() > optionsButtonY ) && (Kroy.height - Gdx.input.getY() < (optionsButtonY + buttonHeight)) ) ){
				  game.batch.draw(optionsButtonActive, xAxisCentred, optionsButtonY, buttonWidth, buttonHeight);
				  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					  optionsWindow.visibility(true);
					  setGameState(MenuScreenState.OPTIONS);
				  }
			  } else {
				  game.batch.draw(optionsButton, xAxisCentred, optionsButtonY, buttonWidth, buttonHeight);
			  }
			  game.batch.end();
				  
			  break;

		  case LOADGAMESELECT:
		  	Gdx.input.setInputProcessor(loadGameSelector.stage);
		  	loadGameSelector.stage.act();
		  	loadGameSelector.stage.draw();
		  	loadGameClickCheck();
		  	break;
		  case DIFFICULTYSELECT:
		  	  Gdx.input.setInputProcessor(difficultySelector.stage);
		  	  difficultySelector.stage.act();
		  	  difficultySelector.stage.draw();
			  difficultyClickCheck();
			  break;
		  case TRUCKSELECT: // Ran when the new game button pressed
			  Gdx.input.setInputProcessor(fireTruckSelector.stage);
			  fireTruckSelector.stage.act();
			  fireTruckSelector.stage.draw();
			  clickCheck();//Checks for any button presses
			  break;
		  case OPTIONS:
			  Gdx.input.setInputProcessor(optionsWindow.stage);
			  optionsWindow.stage.act();
			  optionsWindow.stage.draw();
			  optionsWindow.clickCheck(true);
			  break;
		  }
  	}
  
	/**
	 * @param state
	 */
	public void setGameState(MenuScreenState state){
	    this.state = state;
	}

	public void difficultyClickCheck(){
		difficultySelector.easyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DifficultyMultiplier.setDifficulty(0);
				fireTruckSelector.visibility(true);// display the difficulty selection window
				setGameState(MenuScreenState.TRUCKSELECT);
			}
		});
		difficultySelector.mediumButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DifficultyMultiplier.setDifficulty(1);
				fireTruckSelector.visibility(true);// display the difficulty selection window
				setGameState(MenuScreenState.TRUCKSELECT);
			}
		});
		difficultySelector.hardButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				DifficultyMultiplier.setDifficulty(2);
				fireTruckSelector.visibility(true);// display the difficulty selection window
				setGameState(MenuScreenState.TRUCKSELECT);
			}
		});
	}

	public void loadGameClickCheck(){
		loadGameSelector.loadGame1Button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Load button pressed");
				fireTruckSelector.visibility(true);// display the difficulty selection window
				loadingGame = true;
				startGame(1);
			}
		});
	}
  
	/**
	 * Checks if any of the buttons have been pressed
	 * and the number of the fireTruck type is passed to the new GameScreen
	 */
	public void clickCheck() {
		//Truck 1 Selected
		fireTruckSelector.truckButton1.addListener(new ClickListener() {
			@Override
	    	public void clicked(InputEvent event, float x, float y) {
				startGame(0);//Game begun with 0 (Speed) as the truck selected
				HUD.setScore(100000);
	    	}
	    });
		//Truck 2 Selected
		fireTruckSelector.truckButton2.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		startGame(1);//Game begun with 1 (Speed + Damage) as the truck selected
	    		HUD.setScore(100000);
	    	}
	    });
		//Truck 3 Selected
		fireTruckSelector.truckButton3.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		startGame(2);//Game begun with 2 (Damage) as the truck selected
	    		HUD.setScore(100000);
	    	}
	    });
		//Truck 4 Selected
		fireTruckSelector.truckButton4.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame(3);//Game begun with 3 (Capacity + Range) as the truck selected
				HUD.setScore(100000);
				
			}
	    });
		//Truck 5 Selected
		fireTruckSelector.truckButton5.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame(4);//Game begun with 4 (Capacity) as the truck selected
				HUD.setScore(100000);
				
			}
	    });
		//Truck 6 Selected
		fireTruckSelector.truckButton6.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame(5);//Game begun with 5 (Range) as the truck selected
				HUD.setScore(100000);
				
			}
	    });
	}


	/**
	 * 
	 * @param truckNum Type of truck selected
 	 */
	public void startGame(int truckNum) {
		 if (!currentlyRunningGame) {	// Checks if a new GameScreen is currently running and either makes one or ignores the commands
			 currentlyRunningGame = true; // Makes sure that only one GameScreen is opened at once
			 if(!loadingGame) //If the user does not want to load a game, start a new game, otherwise load a previous save state
			 	game.newGame(truckNum); // Calls the function in Kroy to start a new game
			 else
			 	game.loadGame(truckNum);
		 }
	} 
	
	/**
	 * Run the minigame
 	 */
	public void startMinigame() {
		 if (!currentlyRunningGame) {	// Checks if a new GameScreen is currently running and either makes one or ignores the commands
			 currentlyRunningGame = true; // Makes sure that only one GameScreen is opened at once
			 game.newMinigame(); // Calls the function in Kroy to start a new minigame
		 }
	} 
  
  
  /**
   * @param state
   */
  public void setCurrentlyRunningGame(boolean state) {
	  currentlyRunningGame = state;
  }
  
  /**
   *
   */
  @Override 
  public void resize(int width, int height) {
	  gameport.update(width, height);
  }
  
  @Override 
  public void pause() {}
  
  @Override 
  public void resume() {}
  
  @Override 
  public void hide() {}
  
  @Override 
  public void dispose() {}
 }

