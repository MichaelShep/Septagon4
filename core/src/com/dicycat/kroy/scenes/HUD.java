package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.PowerType;
import com.dicycat.kroy.entities.PowerUps;
import com.dicycat.kroy.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * HUD window
 * 
 * @author Michele Imbriani
 *
 */
public class HUD {
	public Stage stage;
	private Viewport viewport;	//creating new port so that the HUD stays locked while map can move around independently
	
	private int trucks = 6;
	private static int score = 100000;
	private float timeCount = 0;
	
	private Label scoreLabel;
	private Label timeLabel;
	private Label trucksLabel;
	//Used to display how many fortresses are left that the player needs to destroy [ID: FORTRESS INIT]
	private Label fortressesLabel;
	private Label fortressCountLabel;

	private Label worldTimerLabel;
	private Label scoreCountLabel;
	private Label trucksCountLabel;	//we could put mini images of the trucks instead of using an int for the lives

	//Will store all the powerUp info and message for displaying PowerUp [ID: DEFINE POWER]
	private boolean displayingPowerUp = false; //Holds whether the player currently has a powerUp or not

	private List<PowerUps> currentPowerUps;
	private List<Label> powerUpsLabels;
	int numPowerUps = 0;
	private Table tableHUD;
	
	/**
	 * @param sb	SpriteBatch
	 * @param game	Kroy instance
	 */
	public HUD(SpriteBatch sb, Kroy game) {
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Where we are going to put the HUD elements 

		tableHUD = new Table();
		tableHUD.top();	// puts widgets from the top instead of from the centre
		tableHUD.setFillParent(true);	//makes the table the same size of the stage

		currentPowerUps = new ArrayList<PowerUps>();
		powerUpsLabels = new ArrayList<Label>();

		//Initialuses the powerUp labels [ID: SETUP POWER]
		for(int i = 0; i < GameScreen.NUM_POWER_UPS; i++)
		{
			powerUpsLabels.add(new Label("NEW POWERUP", new Label.LabelStyle(new BitmapFont(), Color.RED)));
		}

		worldTimerLabel = new Label(String.format("%03d", (int)GameScreen.gameTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksLabel = new Label("TRUCKS:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		//Setup Fortress label [ID: FORTRESS SETUP]
		fortressesLabel = new Label("FORTRESSES LEFT:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		fortressCountLabel = new Label(String.format("%01d", 0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		trucksCountLabel = new Label(String.format("%01d", trucks), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		tableHUD.add(timeLabel).expandX().padTop(10);
		tableHUD.add(worldTimerLabel).expandX().padTop(10);
		tableHUD.add(scoreLabel).expandX().padTop(10);			// expandX so that all elements take up the same amount of space
		tableHUD.add(scoreCountLabel).expandX().padTop(10);
		tableHUD.add(trucksLabel).expandX().padTop(10);
		tableHUD.add(trucksCountLabel).expandX().padTop(10);
		tableHUD.add(fortressesLabel).expandX().padTop(10);
		tableHUD.add(fortressCountLabel).expandX().padTop(10);
		
		stage.addActor(tableHUD);
		
	}
	
	/**
	 * Using delta time allows to operate with the real-world time (seconds)
	 * rather than the in-game time (which is computed using frames)
	 * 
	 * @param dt	Delta Time 
	 */
	public void update(float dt, GameScreen gameScreen) {
		timeCount += dt;
		System.out.println("CODE BEING RUN 1");
		if (timeCount >= 1 && gameScreen.getState() == GameScreen.GameScreenState.RUN) {
			if (GameScreen.gameTimer>0) {
				GameScreen.gameTimer--;
			}
			score = score - 220;
			worldTimerLabel.setText(String.format("%03d", (int)GameScreen.gameTimer));
			timeCount =0;
			scoreCountLabel.setText(String.format("%05d", score));
			trucksCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.getLives()));
			fortressCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.fortressesLeft())); //Updates the text for displaying the fortresses [ID: FORTRESS DISPLAY]

			//Update the powerUp label when displaying a powerUp, remove the powerUp when expired [ID: UPDATE POWER]

			for(int i = 0; i < currentPowerUps.size(); i++)
			{
				if(currentPowerUps.get(i).getDuration() >= 1)
				{
					currentPowerUps.get(i).setDuration(currentPowerUps.get(i).getDuration() - 1);
					if(currentPowerUps.get(i).getType() != PowerType.REFILLWATER && currentPowerUps.get(i).getType() != PowerType.FULLHEALTH)
						powerUpsLabels.get(i).setText("You have " + currentPowerUps.get(i).toString() + " for " + currentPowerUps.get(i).getDuration() + " seconds");
				}else
				{
					removePowerUpMessage(i);
				}
			}
		}
	}

	/***
	 * Adds a powerUp message to the screen informing the user of the powerUp that they currently have [ID: ADD POWER]
	 * @param currentPowerUp The powerUp that the message should be displayed for
	 */
	public void addPowerUpMessage(PowerUps currentPowerUp){
		currentPowerUps.add(currentPowerUp);
		if(!displayingPowerUp)
			displayingPowerUp = true;
		if(currentPowerUp.getType() != PowerType.REFILLWATER && currentPowerUp.getType() != PowerType.FULLHEALTH)
			powerUpsLabels.set(numPowerUps, new Label("You have " + currentPowerUp.toString() + " for " + currentPowerUp.getDuration() + " seconds", new Label.LabelStyle(new BitmapFont(), Color.RED)));
		else
			powerUpsLabels.set(numPowerUps, new Label("You have been given " + currentPowerUp.toString(), new Label.LabelStyle(new BitmapFont(), Color.RED)));
		powerUpsLabels.get(numPowerUps).setPosition(Gdx.graphics.getWidth() / 2 - powerUpsLabels.get(numPowerUps).getWidth() / 2, Gdx.graphics.getHeight() - 50 - (numPowerUps * 30));
		stage.addActor(powerUpsLabels.get(numPowerUps));
		numPowerUps++;
	}

	/***
	 * Removes one of the the current powerUp messages from the screen [ID: REMOVE POWER]
	 */
	public void removePowerUpMessage(int index){
		if(numPowerUps >= 0)
		{
			powerUpsLabels.get(index).remove();
			currentPowerUps.get(index).remove();
			powerUpsLabels.remove(index);
			currentPowerUps.remove(index);
			updatePowerUpPositions();
			numPowerUps--;
			if(numPowerUps == 0)
			{
				displayingPowerUp = false;
			}
		}
	}

	/**
	 * Used for moving the powerUp labels to the correct y position once one of the labels has been removed
	 */
	public void updatePowerUpPositions()
	{
		for(int i = 0; i < currentPowerUps.size(); i++)
		{
			powerUpsLabels.get(i).setPosition(Gdx.graphics.getWidth() / 2 - powerUpsLabels.get(i).getWidth() / 2, Gdx.graphics.getHeight() - 50 - (i * 30));
		}
	}

	/**
	 * Sets up values that are need for JUnit testing
	 * Only used for testing purposes
	 */
	public void setupEssentialValues(SpriteBatch batch)
	{
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, batch);	//Where we are going to put the HUD elements
	}

	public Integer getFinalScore() {
		return score;
	}

	public static void setScore(Integer x){
		score = x;
	}

	public static int getScore(){
		return score;
	}

	public PowerUps getCurrentPowerUps(int index) { return currentPowerUps.get(index); }
	public List<PowerUps> getCurrentPowerUps() { return currentPowerUps; }

	public Label getScoreLabel() { return scoreLabel; }
	public Label getTimeLabel() { return timeLabel; }
	public Label getTrucksLabel() { return trucksLabel; }
	public Label getFortressesLabel() { return fortressesLabel; }
	public Label getFortressCountLabel() { return fortressCountLabel; }
	public Label getWorldTimerLabel() { return worldTimerLabel; }
	public Label getScoreCountLabel() { return scoreCountLabel; }
	public Label getTrucksCountLabel() { return trucksCountLabel; }
	public List<Label> getPowerUpsLabels() { return powerUpsLabels; }
	public Table getTableHUD() { return tableHUD; }
	public float getTimeCount() { return timeCount; }
	public void setTimeCount(float timeCount) { this.timeCount = timeCount; }
	public float getWorldTimer() { return GameScreen.gameTimer; }
	public void setWorldTimer(int worldTimer) { GameScreen.gameTimer = worldTimer; }
	public boolean isDisplayingPowerUp() { return displayingPowerUp; }

	public void setDisplayingPowerUp(boolean displayingPowerUp) { this.displayingPowerUp = displayingPowerUp; }
	
}

