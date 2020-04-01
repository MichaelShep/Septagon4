package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.entities.PowerUps;
import com.dicycat.kroy.entities.UFO;
import com.dicycat.kroy.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Class added as a result of refractoring the GameScreen - used to handle the updateLoop of the game
 */

public class Updater
{
    private GameScreen gameScreen;
    private SaveManager saveManager;

    private int patrolUpdateRate; //How many seconds should pass before we respawn patrols;
    private float lastPatrol; //time passed since we last spawned patrols

    public Updater(GameScreen gameScreen, SaveManager saveManager)
    {
        this.gameScreen = gameScreen;
        this.saveManager = saveManager;
        lastPatrol = Gdx.graphics.getDeltaTime();
        patrolUpdateRate = 30;
    }

    /**
     * Updates all the active gameobjects and adds them to the render queue.
     * Removes gameobjects from the active pool if they are marked for removal.
     * Adds new gameobjects.
     * Adds dead objects to render queue.
     * Respawns the player if necessary.
     */
    public void updateLoop(List<PowerUps> powerUps) {
        gameScreen.checkZoom();

        //Flag to say that when the game is being updated, the game needs to be saved again
        if(saveManager.isSavedMostRecentState())
            saveManager.setSavedMostRecentState(false);

        List<GameObject> toRemove = new ArrayList<GameObject>();
        List<Vector2> patrolPositions = new ArrayList<>();

        patrolPositions = updateObjects(toRemove, patrolPositions, powerUps);
        addAndRemoveObjects(toRemove);
        gameScreen.switchTrucks();
        spawnPatrols(patrolPositions);
    }

    /**
     * Update all the objects currently in the game
     * @param toRemove List of objects that need to be removed from the game
     * @param patrolPositions All of positions of where the patrols should spawn
     * @return Returns the list of generated partolPositions
     */
    public List<Vector2> updateObjects(List<GameObject> toRemove, List<Vector2> patrolPositions, List<PowerUps> powerUps)
    {
        //Checks whether a powerUp should be removed from the list of powerUps
        for(int i = 0; i < powerUps.size(); i++)
        {
            if(powerUps.get(i).isShouldRemove())
                powerUps.remove(powerUps.get(i));
        }

        for (GameObject gObject : gameScreen.getGameObjects()) {	//Go through every game object
            gObject.update();						//Update the game object
            if (gObject.isRemove()) {				//Check if game object is to be removed
                toRemove.add(gObject);					//Set it to be removed
            }else {
                gameScreen.getObjectsToRender().add(gObject);
                //it doesn't need to be removed; check if it is a fortress
                if (gObject instanceof Fortress) {
                    //it is. mark down its position so we can spawn an entity there later
                    patrolPositions.add(gObject.getCentre());
                }
            }
        }
        gameScreen.getCurrentTruck().update();
        return patrolPositions;
    }

    /***
     * Adds and removes objects from the game as necessary
     * @param toRemove The list of objects that should be removed
     */
    public void addAndRemoveObjects(List<GameObject> toRemove)
    {
        for (GameObject rObject : toRemove) {	//Remove game objects set for removal
            gameScreen.getGameObjects().remove(rObject);
            if (rObject.isDisplayable()) {
                gameScreen.getDeadObjects().add(rObject);
            }
        }
        for (GameObject aObject : gameScreen.getObjectsToAdd()) {		//Add game objects to be added
            gameScreen.getGameObjects().add(aObject);
        }
        gameScreen.getObjectsToAdd().clear();	// Clears list as not to add new objects twice

        for (GameObject dObject : gameScreen.getDeadObjects()) { // loops through the destroyed but displayed items (such as destroyed bases)
            gameScreen.getObjectsToRender().add(dObject);
        }
        if (gameScreen.getCurrentTruck().isRemove()) {	//If the player is set for removal, respawn
            gameScreen.updateLives();
        }
    }

    /**
     * Spawns patrols into the game
     * @param patrolPositions The positions where the patrols should be created
     */
    public void spawnPatrols(List<Vector2> patrolPositions)
    {
        lastPatrol += Gdx.graphics.getDeltaTime();
        if (lastPatrol >= patrolUpdateRate) {
            lastPatrol = 0;

            //we should spawn a patrol near every fortress if it given it's been 10 secs.
            for (Vector2 position: patrolPositions) {

                //Randomize the positions a little bit
                float oldX = position.x;
                float oldY = position.y;
                float randX = (float) (oldX - 400 + Math.random() * 400);
                float randY = (float) (oldY - 400 + Math.random() * 400);

                UFO ufoToAdd = new UFO(new Vector2(randX, randY));
                gameScreen.getUfos().add(ufoToAdd);
                gameScreen.getGameObjects().add(ufoToAdd);
            }
        }
    }

    public int getPatrolUpdateRate() { return patrolUpdateRate; }
    public float getLastPatrol() { return lastPatrol; }
    public GameScreen getGameScreen() { return gameScreen; }
    public SaveManager getSaveManager() { return saveManager; }

    public void setPatrolUpdateRate(int patrolUpdateRate) { this.patrolUpdateRate = patrolUpdateRate; }
    public void setLastPatrol(float lastPatrol) { this.lastPatrol = lastPatrol; }
}
