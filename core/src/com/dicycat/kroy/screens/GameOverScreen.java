package com.dicycat.kroy.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.HUD;

/**
 * 
 * @author
 *
 */
public class GameOverScreen implements Screen {

	private Kroy game;
	private OrthographicCamera gamecam; // m
	private Viewport gameport; // m

	private Stage stage;

	public boolean result; // to change based on whether game is won or lost

	private Texture gameOverImage = new Texture("gameover.png");
	private Texture youWonImage = new Texture("youwon.png");
	private Texture youLostImage = new Texture("youlost.png");
	private Texture playButton = new Texture("newgame.png");
	private Texture playButtonActive = new Texture("newActive.png");
	private Texture menuButton = new Texture("menu.png");
	private Texture menuButtonActive = new Texture("menuActive.png");

	private int score;
	private int highScore = 0;
	private Label scoreLabel;
	private Label scoreNumberLabel;
	private Label highScoreLabel;
	private Label highScoreNumberLabel;
	private Integer scaleScore = 2;
	private float padScore;
	private float padTop;
	private int truckNum;

	// coordinates for gameoverIMG, Play and Exit buttons
	private int gameOverImageWidth = 400;
	private int gameOverImageHeight = 200;
	private int gameOverImageY = ((Kroy.height / 2) + 75);
	private int gameOverImageXAxisCentred = (Kroy.width / 2) - (gameOverImageWidth / 2);

	private int resultingImageWidth = 300;
	private int resultingImageHeight = 100;
	private int resultImageY = ((Kroy.height / 2) - 20);
	private int resultImageXAxisCentred = (Kroy.width / 2) - (resultingImageWidth / 2);

	private int buttonWidth = 250;
	private int buttonHeight = 50;
	private int xAxisCentred = (Kroy.width / 2) - (buttonWidth / 2);
	private int playButtonX = ((Kroy.height / 2) - 150);
	private int minigameButtonY = (Kroy.height / 2) - 225;

	private Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); // cursor
	private int xHotSpot = pm.getWidth() / 3; // where the cursor's aim is
	private int yHotSpot = 0;
	private Table table;

	/**
	 * @param game
	 * @param truckNum
	 * @param result
	 */
	public GameOverScreen(Kroy game, int truckNum, Boolean result) {
		this.game = game;
		this.result = result;
		gamecam = new OrthographicCamera(); // m
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
		stage = new Stage(gameport, game.batch);
		this.truckNum = truckNum;

		table = new Table(); // this allows to put widgets in the scene in a clean and ordered way
		table.setFillParent(true);
		table.top();

		if (result) {
			score = Kroy.mainGameScreen.getHud().getFinalScore();
			highScore = game.getHighScore();
			if (score > highScore) {
				highScore = score;
				game.setHighScore(highScore);
				HUD.setScore(100000);
			}
		} else {
			score = 0;
			HUD.setScore(100000);
		}
		try {
			updateHighScore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scoreLabel = new Label("YOUR SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreNumberLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		highScoreLabel = new Label("HIGH SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		highScoreNumberLabel = new Label(String.format("%05d", highScore),
				new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		padScore = (Kroy.width / 2) - scoreLabel.getWidth() - 10;
		padTop = (Kroy.height / 2);

		scoreLabel.setFontScale(scaleScore);
		scoreNumberLabel.setFontScale(scaleScore);
		highScoreLabel.setFontScale(scaleScore);
		highScoreNumberLabel.setFontScale(scaleScore);

		table.add(scoreLabel).padLeft(padScore).padTop(padTop);
		table.add(scoreNumberLabel).padRight(padScore).padTop(padTop);
		table.row();
		table.add(highScoreLabel).padLeft(padScore);
		table.add(highScoreNumberLabel).padRight(padScore);

		stage.addActor(table);
	}

	@Override
	public void show() {
	}

	/**
	 *
	 */
	@Override
	public void render(float delta) {
		stage.act(); // allows the stage to interact with user input

		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();

		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));

		game.batch.draw(gameOverImage, gameOverImageXAxisCentred, gameOverImageY, gameOverImageWidth,
				gameOverImageHeight);

		if (result) {
			game.batch.draw(youWonImage, resultImageXAxisCentred, resultImageY, resultingImageWidth,
					resultingImageHeight);
		} else {
			game.batch.draw(youLostImage, resultImageXAxisCentred, resultImageY, resultingImageWidth,
					resultingImageHeight);
		}

		// for play button: checks if the position of the cursor is inside the
		// coordinates of the button
		if (((Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred))
				&& ((Kroy.height - Gdx.input.getY() > playButtonX)
						&& (Kroy.height - Gdx.input.getY() < (playButtonX + buttonHeight)))) {
			game.batch.draw(playButtonActive, xAxisCentred, playButtonX, buttonWidth, buttonHeight);
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				this.dispose();
				game.batch.end();
				game.newGame(truckNum);
				return;
			}
		} else {
			game.batch.draw(playButton, xAxisCentred, playButtonX, buttonWidth, buttonHeight);
		}

		// for minigame button
		if (((Gdx.input.getX() < (xAxisCentred + buttonWidth)) && (Gdx.input.getX() > xAxisCentred))
				&& ((Kroy.height - Gdx.input.getY() > minigameButtonY)
						&& (Kroy.height - Gdx.input.getY() < (minigameButtonY + buttonHeight)))) {
			game.batch.draw(menuButtonActive, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				dispose();
				game.backToMenu();
			}
		} else {
			game.batch.draw(menuButton, xAxisCentred, minigameButtonY, buttonWidth, buttonHeight);
		}
		game.batch.end();

		stage.draw();

	}

	/**
	 * Saves the high score each time the game is run
	 * 
	 * @throws IOException
	 */
	public void updateHighScore() throws IOException {
		File highScoreFile = new File("highScore.txt");
		if(!highScoreFile.exists()) highScoreFile.createNewFile();
		
		try {
			Scanner fReader = new Scanner(highScoreFile);
			while (fReader.hasNextLine()) {
		        String data = fReader.nextLine();
		        highScore = Integer.parseInt(data);
		      }
			if(highScore == 0) {
				highScore = 1;
			}
			fReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		FileWriter fWriter = new FileWriter("highScore.txt");
		if (score > highScore) {
			try {
				highScoreFile.createNewFile();
				fWriter.write(score);
				fWriter.close();
				System.out.println("Successfully updated the score.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

	}

	/**
	 *
	 */
	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
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

	public Kroy getGame() { return game; }
	public boolean isResult() { return result; }
	public int getTruckNum() { return truckNum; }
	public int getScore() { return score; }
	public int getHighScore() { return highScore; }
	public Label getScoreLabel() { return scoreLabel; }
	public Label getScoreNumberLabel() { return scoreNumberLabel; }
	public Label getHighScoreLabel() { return highScoreLabel; }
	public Label getHighScoreNumberLabel() { return highScoreNumberLabel; }
	public Table getTable() { return table; }
	public Viewport getGameport() { return gameport; }

	public void setScore(int score) { this.score = score; }
}
