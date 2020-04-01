package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.entities.Goose;
import com.dicycat.kroy.entities.PowerUps;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the Updater class
 * updateLoop() has not been tested as this just calls all the methods that have been tested
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class UpdaterTest
{
    private Kroy kroy;
    private HUD hud;

    /**
     * Sets up the environment for allowing us to test with LibGDX
     */
    @Before
    public void init()
    {
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        kroy = Mockito.mock(Kroy.class, Mockito.CALLS_REAL_METHODS);
        kroy.batch = Mockito.mock(SpriteBatch.class);
        hud = Mockito.mock(HUD.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
        Kroy.mainGameScreen.textures.initFireTruckTexture(0);
    }

    /**
     * Tests the constructor of the Updater class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        Updater testUpdater = new Updater(testGameScreen, testSaveManager);

        //Check values are assigned correctly
        assertEquals(testUpdater.getGameScreen(), testGameScreen);
        assertEquals(testUpdater.getSaveManager(), testSaveManager);
        assertEquals(testUpdater.getPatrolUpdateRate(), 30);
        assertTrue(testUpdater.getLastPatrol() <= Gdx.graphics.getDeltaTime());
    }

    /**
     * Tests the updateObjects() method of the Updater class
     */
    @Test
    public void testUpdateObjects()
    {
        //Create test objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        Updater testUpdater = new Updater(testGameScreen, testSaveManager);
        List<PowerUps> powerUps = new ArrayList<PowerUps>();
        PowerUps testPowerUp = new PowerUps(new Vector2(100, 100), hud);
        powerUps.add(testPowerUp);
        List<GameObject> toRemove = new ArrayList<GameObject>();
        List<Vector2> patrolPositions = new ArrayList<Vector2>();
        FireTruck testFireTruck = new FireTruck();
        Kroy.mainGameScreen.setPlayer(testFireTruck);

        //Tests that if no powerUps are set to be removed, the powerUps list does not change
        testPowerUp.setShouldRemove(false);
        int powerUpsSize = powerUps.size();
        testUpdater.updateObjects(toRemove, patrolPositions, powerUps);
        assertEquals(powerUps.size(), powerUpsSize);

        //Tests that if a powerUp is set to be removed, it is removed from the powerUps list
        testPowerUp.setShouldRemove(true);
        testUpdater.updateObjects(toRemove, patrolPositions, powerUps);
        assertEquals(powerUps.size(), powerUpsSize - 1);

        //Tests that if the object in gameObjects does not need to be removed, will be added to objectsToRender
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
        GameObject testGameObject = new Goose();
        gameObjects.add(testGameObject);
        Kroy.mainGameScreen.setGameObjects(gameObjects);
        testUpdater.updateObjects(toRemove, patrolPositions, powerUps);
        assertEquals(testGameScreen.getObjectsToRender().get(0), testGameObject);

        //Tests that if the object in gameObjects does need to be removed, will be added to toRemove
        testGameObject.setRemove(true);
        testUpdater.updateObjects(toRemove, patrolPositions, powerUps);
        assertEquals(toRemove.get(0), testGameObject);

        //Tests that if a fortress in gameObject list, will add a patrolPosition at the centre of the centre of the fortress
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(100, 100));
        gameObjects.add(testFortress);
        Kroy.mainGameScreen.setGameObjects(gameObjects);
        testUpdater.updateObjects(toRemove, patrolPositions, powerUps);
        assertEquals(patrolPositions.get(0).x, testFortress.getCentre().x, 0);
        assertEquals(patrolPositions.get(0).y, testFortress.getCentre().y, 0);
    }

    /**
     * Tests for the addAndRemoveObjects() method of the Updater class
     */
    @Test
    public void testAddAndRemoveObjects()
    {
        //Create test objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        Updater testUpdater = new Updater(testGameScreen, testSaveManager);
        List<GameObject> toRemove = new ArrayList<GameObject>();
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
        GameObject testObject = new Goose();
        gameObjects.add(testObject);
        Kroy.mainGameScreen.setGameObjects(gameObjects);
        FireTruck testTruck = new FireTruck();
        Kroy.mainGameScreen.setPlayer(testTruck);

        //Tests that if there is a game object in the toRemove list, will be removed from gameObjects
        toRemove.add(testObject);
        testUpdater.addAndRemoveObjects(toRemove);
        assertTrue(!gameObjects.contains(testObject));

        //Tests that if there is a gameObject that isDisplayable in the toRemove list, will be removed from GameObjects and added to deadObjects
        gameObjects.add(testObject);
        Kroy.mainGameScreen.setGameObjects(gameObjects);
        testObject.setDisplayable(true);
        testUpdater.addAndRemoveObjects(toRemove);
        assertTrue(!gameObjects.contains(testObject));
        assertTrue(Kroy.mainGameScreen.getDeadObjects().contains(testObject));

        //Tests that if there is a gameObject in objectsToAdd, will be added to gameObjects
        Kroy.mainGameScreen.getObjectsToAdd().add(testObject);
        testUpdater.addAndRemoveObjects(toRemove);
        assertTrue(Kroy.mainGameScreen.getGameObjects().contains(testObject));

        //Tests that if there is a gameObject in deadObjects, will be added to objectsToRender
        Kroy.mainGameScreen.getObjectsToRender().clear();
        Kroy.mainGameScreen.getDeadObjects().add(testObject);
        testUpdater.addAndRemoveObjects(toRemove);
        assertTrue(Kroy.mainGameScreen.getObjectsToRender().contains(testObject));
    }

    /**
     * Tests for the spawnPatrols() method of the Updater class
     */
    @Test
    public void testSpawnPatrols()
    {
        //Create test objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        Updater testUpdater = new Updater(testGameScreen, testSaveManager);
        List<Vector2> patrolPositions = new ArrayList<Vector2>();
        patrolPositions.add(new Vector2(100, 100));

        //Tests that if lastPatrol < patrolUpdateRate no patrols are added
        Kroy.mainGameScreen.getGameObjects().clear();
        testUpdater.setLastPatrol(0);
        testUpdater.setPatrolUpdateRate(1);
        testUpdater.spawnPatrols(patrolPositions);
        assertEquals(Kroy.mainGameScreen.getGameObjects().size(), 0);

        //Tests that if lastPatrol >= patrolUpdateRate, patrol is added to gameObjects and ufos
        testUpdater.setLastPatrol(1);
        testUpdater.setPatrolUpdateRate(0);
        testUpdater.spawnPatrols(patrolPositions);
        assertEquals(Kroy.mainGameScreen.getGameObjects().size(), 1);
        assertEquals(Kroy.mainGameScreen.getUfos().size(), 1);
    }
}
