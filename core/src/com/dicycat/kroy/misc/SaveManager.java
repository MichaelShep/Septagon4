package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.DifficultyMultiplier;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.entities.UFO;

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
 * NOTE TO SELF: NEED TO LOOK AT AND MAKE SURE THAT ENTITIES ARE REMOVED FROM THE THREE LISTS THAT I USE HERE
 * WHEN THEY ARE REMOVED FROM THE GAMEOJECT LIST IN THE GAMESCREEN, DON'T THINK THEY ARE AT THE MOMENT AND THIS
 * COULD CAUSE MEMORY LEAKS
 *
 * [ID: SAVE MANAGER]
 */

public class SaveManager {
    private static final int NUM_MAX_SAVES = 5;

    private List<Preferences> preferences;
    private Integer preferencesIndex = 0;

    //Lists containing data on all attributes in the game
    private List<FireTruck> fireTrucks;
    private List<UFO> ufos;
    private List<Fortress> fortresses;

    private boolean savedMostRecentState = true;

    public SaveManager(List<FireTruck> fireTrucks, List<UFO> ufos, List<Fortress> fortresses){
        preferences = new ArrayList<Preferences>();

        for(int i = 0; i < NUM_MAX_SAVES; i++)
        {
            preferences.add(Gdx.app.getPreferences("Save" + i));
        }

        this.updateSavedEntities(fireTrucks, ufos, fortresses);
    }

    /***
     * Stores all the entities from the game at the point where the save has been called
     * @param fireTrucks List containing data on all the fireTrucks currently in game
     * @param ufos List containing data on all the UFO's currently in the game
     * @param fortresses List containing data on all the fortresses currently in the game
     */
    public void updateSavedEntities(List<FireTruck> fireTrucks, List<UFO> ufos, List<Fortress> fortresses){
        this.fireTrucks = fireTrucks;
        this.ufos = ufos;
        this.fortresses = fortresses;
    }

    /**
     * Saves all the attributes needed for the game into the save slot that is currently being used [ID: SAVE]
     */
    public void saveAttributes() {

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
            preferences.get(preferencesIndex).putFloat("fireTruck" + i + "flowRate", fireTrucks.get(i).getFlowRate());
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
        }
        preferences.get(preferencesIndex).putInteger("numUfos", ufos.size());

        //Saves the difficulty value of the game
        preferences.get(preferencesIndex).putInteger("difficulty", DifficultyMultiplier.getDifficultyValue());

        //Adds variable that will signify that the save has been used so that we know the user can load from this save
        preferences.get(preferencesIndex).putBoolean("hasUsedSave", true);

        //Saves all the changes to the preferences
        preferences.get(preferencesIndex).flush();

        System.out.println("VALUES HAVE BEEN SUCCESSFULLY SAVED");
    }

    /**
     * Loads all the attributes from the save slot that is currently being used [ID: LOAD]
     */
    public void loadAttributes(List<GameObject> gameObjects, GameTextures textures, List<Vector2> fortressPositions, List<Vector2> fortressSizes){

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

            truck.setMaxHealthPoints(maxHealth);
            truck.setRotation(preferences.get(preferencesIndex).getFloat("fireTruck" + i + "rotation"));
            fireTrucks.add(truck);

            System.out.println("LOADED HEALTH: " + truck.getHealthPoints());
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
            Kroy.mainGameScreen.addFortress();
            System.out.println("FORTRESS " + i + "HEALTH " + fortress.getHealthPoints());
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
            gameObjects.add(ufo);
            ufos.add(ufo);
            System.out.println("UFO " + i + " loaded");
        }
    }

    public boolean isSavedMostRecentState() { return savedMostRecentState; }
    public List<Preferences> getPreferences() { return preferences; }

    public void setSavedMostRecentState(boolean savedMostRecentState) { this.savedMostRecentState = savedMostRecentState; }

    public void setSave(Integer index){this.preferencesIndex = index;}
}
