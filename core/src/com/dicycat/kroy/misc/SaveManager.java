package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.entities.UFO;

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
 */

public class SaveManager {

    //Preferences instance that is used to store/load values from our game
    private Preferences preferences = Gdx.app.getPreferences("myprefs");

    //Lists containing data on all attributes in the game
    private List<FireTruck> fireTrucks;
    private List<UFO> ufos;
    private List<Fortress> fortreses;

    public SaveManager(List<FireTruck> fireTrucks, List<UFO> ufos, List<Fortress> fortresses){
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
        this.fortreses = fortresses;
    }

    /**
     * Saves all the attributes when a player finishes the game so that they can be loaded for next time
     */
    public void saveAttributes() {
        //Test code - saving an integer  with value 50
        int valueToSave = 50;

        //Stores all the required attributes into the preferences
        preferences.putInteger("myValue", valueToSave);

        //Saves all the changes to the preferences
        preferences.flush();
    }

    /**
     * Loads all the attributes from the previous save state
     */
    public void loadAttributes(){
        //Test code - loading the integer that we gave saved
        if(preferences.contains("myValue")) {
            int myValue = preferences.getInteger("myValue");
            System.out.println("VALUE HAS BEEN LOADED, THE VALUE STORED IS: " + myValue);
        }
    }
}
