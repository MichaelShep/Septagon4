package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.debug.DebugDraw;
import com.dicycat.kroy.debug.DebugRect;
import com.dicycat.kroy.entities.Goose;
import com.dicycat.kroy.entities.Pipe;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.scenes.PauseWindow;

/**
 * Contains the minigame logic
 * 
 * @author Sam Hutchings Based on code from GameScreen class
 *
 */
public class MinigameScreen implements Screen {

	public static enum GameScreenState {
		PAUSE, RUN, RESUME, OPTIONS, END
	}

	public Kroy game;
	public GameScreenState state = GameScreenState.RUN;

	private OrthographicCamera gamecam; // follows along what the port displays
	private Viewport gameport;

	private PauseWindow pauseWindow;
	private OptionsWindow optionsWindow;

	private int score = -2; // Starts negative to give time for the pipes to reach the player 
	private String scoreText = ""; // Instantiates the scoretext variable
	BitmapFont font;

	private static Goose player; // Reference to the player

	Texture map;

	private List<Pipe> pipes; // List of pipes
	private List<DebugDraw> debugObjects;

	public MinigameScreen(Kroy _game) {
		game = _game;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
		pauseWindow = new PauseWindow(game);
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow(game);
		optionsWindow.visibility(false);
		map = new Texture("minigameBackground.png");

		// Create a new font for displaying the score
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
	}

	/**
	 * Screen first shown
	 */
	@Override
	public void show() {
		pipes = new ArrayList<Pipe>();
		debugObjects = new ArrayList<DebugDraw>();
		player = new Goose(); // Initialises the goose
		gamecam.translate(0, 0); // sets initial Camera position

		// Creates a task to generate pipes
		Timer.schedule(new Task() {
			@Override
			public void run() {
				createPipe();
			}
		}, 0, 2);// 0 seconds delay, 2 seconds between pipes
	}

	/**
	 * Draw a debug rectangle (outline)
	 * 
	 * @param bottomLeft Bottom left point of the rectangle
	 * @param dimensions Dimensions of the rectangle (Width, Length)
	 * @param lineWidth  Width of the outline
	 * @param colour     Colour of the line
	 */
	public void DrawRect(Vector2 bottomLeft, Vector2 dimensions, int lineWidth, Color colour) {
		debugObjects.add(new DebugRect(bottomLeft, dimensions, lineWidth, colour));
	}

	/**
	 * Called every frame
	 */
	@Override
	public void render(float delta) {
		Gdx.input.setInputProcessor(pauseWindow.stage); // Set input processor
		pauseWindow.stage.act();
		Batch batch = game.batch;
		switch (state) {
		case RUN:
			renderRunState(batch);
			break;
		case PAUSE:
			renderPauseState();
			break;
		case RESUME:
			renderResumeState();
			break;
		case END:
			renderEndState(batch);
		default:
			break;
		}
	}

	//Method added as a result for refactoring by team septagon
	private void renderRunState(Batch batch){
		if (Gdx.input.isKeyPressed(Keys.P) || Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.M)
				|| Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			pauseWindow.visibility(true);
			pause();
		}

		batch.setProjectionMatrix(gamecam.combined);
		batch.begin(); // Game loop Start
		batch.draw(map, -Kroy.width / 2, -Kroy.height / 2, Kroy.width, Kroy.height);

		player.update();
		player.render(batch);

		pipes.forEach(o -> {
			o.update();
			o.render(batch);
			if (o.gameEnd(player)) {
				// If the player hits a pipe, then the game ends
				gameOver();
			}
			;
		});
		pipes.removeIf(o -> o.isRemove());

		// Score starts at -2, so different text is displayed instead
		if (score < 0) {
			scoreText = "Ready? ";
		} else {
			scoreText = "Score: " + score;
		}

		// Score is displayed in the top left with a light blue background
		batch.draw(new Texture("lightBlue.png"), (-Kroy.width / 2), (Kroy.height / 2) - 75, scoreText.length() * 30,
				75);
		font.draw(batch, scoreText, (-Kroy.width / 2) + 10, (Kroy.height / 2) - 10);

		if (Kroy.debug) {
			debugObjects.forEach(o -> o.Draw(gamecam.combined));
			debugObjects.clear();
		}

		batch.end();

		pauseWindow.stage.draw();
	}

	//Method added as a result for refactoring by team septagon
	private void renderPauseState(){
		pauseWindow.stage.draw();
		clickCheck();
	}

	//Method added as a result for refactoring by team septagon
	private void renderResumeState(){
		pauseWindow.visibility(false);
		setGameState(GameScreenState.RUN);
	}

	//Method added as a result for refactoring by team septagon
	private void renderEndState(Batch batch){
		batch.begin();
		batch.draw(new Texture("minigameEnd.png"), 0, 0, Kroy.width, Kroy.height);
		font.draw(batch, scoreText, (Kroy.width / 2) + 20, (Kroy.height / 2) - 300);
		batch.end();
		if(Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			dispose();
			game.backToMenu();
		}
	}

	/**
	 * Create a new pipe at x = 800, and at a random height. Also adds a point to
	 * the score when a pipe is generated.
	 */
	private void createPipe() {
		int height = (int) Math.round(-300 * Math.random()) - 900;
		pipes.add(new Pipe(new Vector2(800, height)));
		score++;
		// System.out.println(score);
	}

	private void gameOver() {
		setGameState(GameScreenState.END);
	}

	/**
	 * Allows external classes to access the player
	 * 
	 * @return player The goose in the minigame
	 */
	public static Goose getPlayer() {
		return player;
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
	public void hide() {
	}

	@Override
	public void dispose() {
		Kroy.mainMinigameScreen = null;
	}

	/**
	 * @param s The state to set to
	 */
	public void setGameState(GameScreenState s) {
		state = s;
	}

	/**
	 * Checks the pause buttons
	 */
	private void clickCheck() {
		// resume button
		pauseWindow.resume.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseWindow.visibility(false);
				resume();
			}
		});

		// exit button
		pauseWindow.exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		// menu button
		pauseWindow.menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pauseWindow.visibility(false);
				gameOver();
			}
		});
	}

}
