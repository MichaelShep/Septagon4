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

/**
 * HUD window
 * 
 * @author Michele Imbriani
 *
 */
public class HUD {
	public Stage stage;
	private Viewport viewport;	//creating new port so that the HUD stays locked while map can move around independently
	
	private Integer trucks = 6;
	private Integer worldTimer = 300;	//change to float maybe
	private static Integer score = 100000;
	private float timeCount = 0;
	
	private Label scoreLabel;
	private Label timeLabel;
	private Label trucksLabel;
	//Used to display how many fortresses are left that the player needs to destroy [ID: INIT]
	private Label fortressesLabel;
	private Label fortressCountLabel;

	private Label worldTimerLabel;
	private Label scoreCountLabel;
	private Label trucksCountLabel;	//we could put mini images of the trucks instead of using an int for the lives
	private Label powerUpLabel; //Label that will be used to display what powerUp the user currently has [ID: LABEL]

	private boolean displayingPowerUp = false; //Holds whether the player currently has a powerUp or not
	private PowerUps currentPowerUp = null; //Holds the currentPower that the player has
	
	/**
	 * @param sb	SpriteBatch
	 * @param game	Kroy instance
	 */
	public HUD(SpriteBatch sb, Kroy game) {
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Where we are going to put the HUD elements 
		
		Table tableHUD = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		tableHUD.top();	// puts widgets from the top instead of from the centre
		tableHUD.setFillParent(true);	//makes the table the same size of the stage
		
		worldTimerLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksLabel = new Label("TRUCKS:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		//Setup Fortress label [ID: SETUP]
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
	public void update(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			if (worldTimer>0) {
				worldTimer--;
			}
			score = score - 220;
			worldTimerLabel.setText(String.format("%03d", worldTimer));
			timeCount =0;
			scoreCountLabel.setText(String.format("%05d", score));
			trucksCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.getLives()));
			fortressCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.fortressesLeft())); //Updates the text for displaying the fortresses [ID: DISPLAY]

			if(displayingPowerUp && currentPowerUp.getDuration() >= 1)
			{
				currentPowerUp.setDuration(currentPowerUp.getDuration() - 1);
				if(currentPowerUp.getType() != PowerType.REFILLWATER && currentPowerUp.getType() != PowerType.FULLHEALTH)
					powerUpLabel.setText("You have " + currentPowerUp.toString() + " for " + currentPowerUp.getDuration() + " seconds");
			}else if(displayingPowerUp)
			{
				removePowerUpMessage();
			}
		}
	}

	/***
	 * Adds a powerUp message to the screen informing the user of the powerUp that they currently have
	 * @param currentPowerUp The powerUp that the message should be displayed for
	 */
	public void addPowerUpMessage(PowerUps currentPowerUp){
		this.currentPowerUp = currentPowerUp;
		displayingPowerUp = true;
		if(currentPowerUp.getType() != PowerType.REFILLWATER && currentPowerUp.getType() != PowerType.FULLHEALTH)
			powerUpLabel = new Label("You have " + currentPowerUp.toString() + " for " + currentPowerUp.getDuration() + " seconds", new Label.LabelStyle(new BitmapFont(), Color.RED));
		else
			powerUpLabel = new Label("You have been given " + currentPowerUp.toString(), new Label.LabelStyle(new BitmapFont(), Color.RED));
		powerUpLabel.setPosition(Gdx.graphics.getWidth() / 2 - powerUpLabel.getWidth() / 2, Gdx.graphics.getHeight() - 50);
		stage.addActor(powerUpLabel);
	}

	/***
	 * Removes the current powerUp message from the screen
	 */
	public void removePowerUpMessage(){
		if(powerUpLabel != null)
		{
			displayingPowerUp = false;
			powerUpLabel.remove();
			currentPowerUp.remove();
			powerUpLabel = null;
			currentPowerUp = null;
		}
	}


	public Integer getFinalScore() {
		return score;
	}

	public static void setScore(Integer x){
		score = x;
	}

	public Integer getScore(){
		return score;
	}

	/**
	 * @param x		Points to be added to the score
	 */
	public void updateScore(Integer x){
		score += x;
	}
	
}

