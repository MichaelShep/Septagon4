package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.DifficultyMultiplier;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.*;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.screens.GameScreen;

import javax.swing.plaf.FontUIResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used to handle saving and loading the game state so a player can restart the game at any time
 *
 * Features that need to be saved so that a game can be resumed:
 * Each of the engines positions, health and water amounts
 * Each of the fortresses health
 * Each of the patrols positions and health
 * Time elapsed in the game
 *
 * NOTE: NEED TO ADD IN SAVING THE FIRESTATION DATA COS DOESN'T DO THIS YET
 *
 * [ID: SAVE MANAGER]
 */

public class SaveManager {
    public static final int NUM_MAX_SAVES = 6; //Final save slot used by the JUnit tests

    private List<Preferences> preferences;
    private int preferencesIndex = 0;

    //Lists containing data on all attributes in the game
    private List<FireTruck> fireTrucks;
    private List<UFO> ufos;
    private List<Fortress> fortresses;
    private List<PowerUps>  powerUps;

    private boolean savedMostRecentState = true;

    public SaveManager(List<FireTruck> fireTrucks, List<UFO> ufos, List<Fortress> fortresses, List<PowerUps> powerUps){
        preferences = new ArrayList<Preferences>();

        for(int i = 0; i < NUM_MAX_SAVES; i++)
        {
            preferences.add(Gdx.app.getPreferences("Save" + i));
            //Gdx.app.getPreferences("Save" + i).clear();
            //Gdx.app.getPreferences("Save" + i).flush();
            //System.out.println("Save games should have been removed");
        }

        this.updateSavedEntities(fireTrucks, ufos, fortresses, powerUps);
    }

    /***
     * Stores all the entities from the game at the point where the save has been called
     * @param fireTrucks List containing data on all the fireTrucks currently in game
     * @param ufos List containing data on all the UFO's currently in the game
     * @param fortresses List containing data on all the fortresses currently in the game
     */
    public void updateSavedEntities(List<FireTruck> fireTrucks, List<UFO> ufos, List<Fortress> fortresses, List<PowerUps> powerUps){
        this.fireTrucks = fireTrucks;
        this.ufos = ufos;
        this.fortresses = fortresses;
        this.powerUps = powerUps;
    }

    /**
     * Saves all the attributes needed for the game into the save slot that is currently being used [ID: SAVE]
     */
    public void saveAttributes() {

        //Clear all previous saves
        System.out.println("Saving to save slot: " + preferencesIndex);
        preferences.get(preferencesIndex).clear();
        //Stores all the required attributes about the engines into preferences
        for(int i = 0; i < fireTrucks.size(); i++){
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "x", fireTrucks.get(i).getPosition().x);
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "y", fireTrucks.get(i).getPosition().y);
            preferences.get(preferencesIndex).putInteger("fireTruck" + i + "maxHealth", fireTrucks.get(i).getMaxHealthPoints());
            preferences.get(preferencesIndex).putInteger("fireTruck" + i + "health", fireTrucks.get(i).getHealthPoints());
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "flowRate", fireTrucks.get(i).getFlowRate());
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "water", fireTrucks.get(i).getCurrentWater());
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "maxWater", fireTrucks.get(i).getMaxWater());
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "rotation", fireTrucks.get(i).getRotation());
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "range", fireTrucks.get(i).getRange());
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "speed", fireTrucks.get(i).getSpeed());
        }
        preferences.get(preferencesIndex).putInteger("numFireTrucks", fireTrucks.size());

        //Stores all the required attributes about the fortress into preferences
        for(int i = 0; i < fortresses.size(); i++){
            preferences.get(preferencesIndex).putInteger("fortress" + i + "health", fortresses.get(i).getHealthPoints());
            preferences.get(preferencesIndex).putInteger("fortress" + i + "maxHealth", fortresses.get(i).getMaxHealthPoints());
        }
        preferences.get(preferencesIndex).putInteger("numFortresses", fortresses.size());

        //Stores all the required attributes about the ufos into preferences
        for(int i = 0; i < ufos.size(); i++){
            preferences.get(preferencesIndex).putFloat("ufo" + i + "x", ufos.get(i).getPosition().x);
            preferences.get(preferencesIndex).putFloat("ufo" + i + "y", ufos.get(i).getPosition().y);
            preferences.get(preferencesIndex).putInteger("ufo" + i + "health", ufos.get(i).getHealthPoints());
            preferences.get(preferencesIndex).putInteger("ufo" + i + "maxHealth", ufos.get(i).getMaxHealthPoints());
        }
        preferences.get(preferencesIndex).putInteger("numUfos", ufos.size());

        //Stores all the required attributes about the powerUps into preferences
        for(int i = 0; i < powerUps.size(); i++)
        {
            preferences.get(preferencesIndex).putInteger("powerUp" + i + "type", powerUps.get(i).getType().getValue());
            preferences.get(preferencesIndex).putFloat("powerUp" + i + "x", powerUps.get(i).getX());
            preferences.get(preferencesIndex).putFloat("powerUp" + i + "y", powerUps.get(i).getY());
        }
        preferences.get(preferencesIndex).putInteger("numPowerUps", powerUps.size());

        //Saves the difficulty value of the game
        preferences.get(preferencesIndex).putInteger("difficulty", DifficultyMultiplier.getDifficultyValue());

        //Save the game timer variable
        preferences.get(preferencesIndex).putFloat("timer", GameScreen.gameTimer);

        //Save the score variable
        preferences.get(preferencesIndex).putInteger("score", HUD.getScore());

        //Saves the number of fortresses and fire trucks left
        preferences.get(preferencesIndex).putInteger("fortressCount", Kroy.mainGameScreen.getFortressesCount());
        preferences.get(preferencesIndex).putInteger("truckCount", Kroy.mainGameScreen.getLives());

        //Adds variable that will signify that the save has been used so that we know the user can load from this save
        preferences.get(preferencesIndex).putBoolean("hasUsedSave", true);

        //Saves all the changes to the preferences
        preferences.get(preferencesIndex).flush();

        System.out.println("VALUES HAVE BEEN SUCCESSFULLY SAVED");
    }

    /**
     * Loads all the attributes from the save slot that is currently being used [ID: LOAD]
     */
    public void loadAttributes(List<GameObject> gameObjects, GameTextures textures, List<Vector2> fortressPositions, List<Vector2> fortressSizes, HUD hud){

        //Sets up the difficulty of the game
        DifficultyMultiplier.setDifficulty(preferences.get(preferencesIndex).getInteger("difficulty"));

        //Loads all the fireTrucks back into the game
        int numFireTrucks = preferences.get(preferencesIndex).getInteger("numFireTrucks");
        for(int i = 0; i < numFireTrucks; i++){
            float speed = preferences.get(preferencesIndex).getFloat("fireTruck" + i + "speed");
            float flowRate = preferences.get(preferencesIndex).getFloat("fireTruck" + i + "flowRate");
            float maxWater = preferences.get(preferencesIndex).getFloat("fireTruck" + i + "maxWater");
            float water = preferences.get(preferencesIndex).getFloat("fireTruck" + i + "water");
            float range = preferences.get(preferencesIndex).getFloat("fireTruck" + i + "range");
            int health = preferences.get(preferencesIndex).getInteger("fireTruck" + i + "health");
            int maxHealth = preferences.get(preferencesIndex).getInteger("fireTruck" + i + "maxHealth");
            Float[] truckStats = {speed, flowRate, maxWater, water, range};

            FireTruck truck = new FireTruck(new Vector2(preferences.get(preferencesIndex).getFloat("fireTruck" + i + "x"), preferences.get(preferencesIndex).getFloat("fireTruck" + i + "y")), truckStats, i, health);

            truck.setCurrentWaterToFixedValue(water);
            truck.setMaxHealthPoints(maxHealth);
            truck.setHealthToFixedValue(health);
            truck.setRange(range);
            truck.setRotation(preferences.get(preferencesIndex).getFloat("fireTruck" + i + "rotation"));
            fireTrucks.add(truck);

            if(truck.getHealthPoints() <= 0)
            {
                truck.die();
                gameObjects.remove(truck.getHealthBar());
                gameObjects.remove(truck.getTank());
            }
            System.out.println("Fire Truck " + i + " loaded");
        }

        //Loads all the fortresses back into the game
        int numFortresses = preferences.get(preferencesIndex).getInteger("numFortresses");
        for(int i = 0; i < numFortresses; i++){
            int health = preferences.get(preferencesIndex).getInteger("fortress" + i + "health");
            Fortress fortress = new Fortress(fortressPositions.get(i), textures.getFortress(i), textures.getDeadFortress(i), fortressSizes.get(i), health);
            gameObjects.add(fortress);
            fortresses.add(fortress);
            if(fortress.getHealthPoints() == 0)
            {
                fortress.death();
            }
            System.out.println("Fortress " + i + " loaded");
        }

        //Loads all the ufos back into the game
        int numUfos = preferences.get(preferencesIndex).getInteger("numUfos");
        for(int i = 0; i < numUfos; i++){
            UFO ufo = new UFO(new Vector2(preferences.get(preferencesIndex).getFloat("ufo" + i + "x"), preferences.get(preferencesIndex).getFloat("ufo" + i + "y")));
            ufo.setHealthPoints(preferences.get(preferencesIndex).getInteger("ufo" + i + "health"));
            ufo.setMaxHealthPoints(preferences.get(preferencesIndex).getInteger("ufo" + i + "maxHealth"));
            gameObjects.add(ufo);
            ufos.add(ufo);
            System.out.println("UFO " + i + " loaded");
        }

        //Loads all the powerUps back into the game
        int numPowerUps = preferences.get(preferencesIndex).getInteger("numPowerUps");
        for(int i = 0; i < numPowerUps; i++){
            PowerUps powerUp = new PowerUps(new Vector2(preferences.get(preferencesIndex).getFloat("powerUp" + i + "x"), preferences.get(preferencesIndex).getFloat("powerUp" + i + "y")), hud);
            powerUp.setType(PowerType.valueOf(preferences.get(preferencesIndex).getInteger("powerUp" + i + "type")));
            gameObjects.add(powerUp);
            powerUps.add(powerUp);
            System.out.println("PowerUp " + i + " loaded");
        }

        //Load the gameTimer back to the value it should be
        GameScreen.gameTimer = preferences.get(preferencesIndex).getFloat("timer");

        //Load the score back to the value it should be
        HUD.setScore(preferences.get(preferencesIndex).getInteger("score"));

        //Load the fortress and truck count back to what it should be
        Kroy.mainGameScreen.setFortressesCount(preferences.get(preferencesIndex).getInteger("fortressCount"));
        Kroy.mainGameScreen.setLives(preferences.get(preferencesIndex).getInteger("truckCount"));
    }

    public boolean isSavedMostRecentState() { return savedMostRecentState; }
    public List<Preferences> getPreferences() { return preferences; }

    public void setSavedMostRecentState(boolean savedMostRecentState) { this.savedMostRecentState = savedMostRecentState; }

    public void setSave(Integer index){this.preferencesIndex = index;}

    public List<FireTruck> getFireTrucks() { return fireTrucks; }
    public List<Fortress> getFortresses() { return fortresses; }
    public List<UFO> getUfos() { return ufos; }
    public List<PowerUps> getPowerUps() { return powerUps; }
    public int getSave() { return preferencesIndex; }
}
