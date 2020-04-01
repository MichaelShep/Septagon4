package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.entities.PowerUps;
import com.dicycat.kroy.entities.UFO;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the SaveManager class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class SaveManagerTest
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

        hud = Mockito.mock(HUD.class);
        kroy = Mockito.mock(Kroy.class, Mockito.CALLS_REAL_METHODS);
        kroy.batch = Mockito.mock(SpriteBatch.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.setupWindows(kroy);
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
        Kroy.mainGameScreen.textures.initFireTruckTexture(0);
    }

    /**
     * Tests the constructor for the SaveManager class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        List<FireTruck> testFireTrucks = new ArrayList<FireTruck>();
        List<Fortress> testFortresses = new ArrayList<Fortress>();
        List<UFO> testUfos = new ArrayList<UFO>();
        List<PowerUps> testPowerUps = new ArrayList<PowerUps>();
        testFireTrucks.add(new FireTruck());
        testFortresses.add(new Fortress());
        testUfos.add(new UFO(new Vector2(100, 100)));
        testPowerUps.add(new PowerUps(new Vector2(100, 100), hud));
        SaveManager testSaveManager = new SaveManager(testFireTrucks, testUfos, testFortresses, testPowerUps);

        //Test values have been assigned correctly
        assertEquals(testSaveManager.getFireTrucks(), testFireTrucks);
        assertEquals(testSaveManager.getFortresses(), testFortresses);
        assertEquals(testSaveManager.getUfos(), testUfos);
        assertEquals(testSaveManager.getPowerUps(), testPowerUps);
    }

    /**
     * Tests the saveAttributes() method from the the SaveAttributes class
     */
    @Test
    public void testSaveAttributes()
    {
        //Create test objects
        List<FireTruck> testFireTrucks = new ArrayList<FireTruck>();
        List<Fortress> testFortresses = new ArrayList<Fortress>();
        List<UFO> testUfos = new ArrayList<UFO>();
        List<PowerUps> testPowerUps = new ArrayList<PowerUps>();
        testFireTrucks.add(new FireTruck());
        testFortresses.add(new Fortress());
        testUfos.add(new UFO(new Vector2(100, 100)));
        testPowerUps.add(new PowerUps(new Vector2(100, 100), hud));
        SaveManager testSaveManager = new SaveManager(testFireTrucks, testUfos, testFortresses, testPowerUps);

        //Call method to be tested
        testSaveManager.setSave(5);
        testSaveManager.saveAttributes();

        //Tests FireTruck attributes are saved
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0x"), testFireTrucks.get(0).getX(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0y"), testFireTrucks.get(0).getY(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("fireTruck0maxHealth"), testFireTrucks.get(0).getMaxHealthPoints());
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("fireTruck0health"), testFireTrucks.get(0).getHealthPoints());
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0flowRate"), testFireTrucks.get(0).getFlowRate(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0water"), testFireTrucks.get(0).getCurrentWater(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0maxWater"), testFireTrucks.get(0).getMaxWater(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0rotation"), testFireTrucks.get(0).getRotation(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0range"), testFireTrucks.get(0).getRange(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("fireTruck0speed"), testFireTrucks.get(0).getSpeed(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("numFireTrucks"), 1, 0);

        //Test Fortress attributes are saved
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("fortress0health"), testFortresses.get(0).getHealthPoints());
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("fortress0maxHealth"), testFortresses.get(0).getMaxHealthPoints());
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("numFortresses"), 1);

        //Test UFO attributes are saved
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("ufo0x"), testUfos.get(0).getX(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("ufo0y"), testUfos.get(0).getY(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("ufo0health"), testUfos.get(0).getHealthPoints());
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("ufo0maxHealth"), testUfos.get(0).getMaxHealthPoints());
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("numUfos"), 1);

        //Tests PowerUp attributes are saved
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("powerUp0type"), testPowerUps.get(0).getType().getValue());
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("powerUp0x"), testPowerUps.get(0).getX(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getFloat("powerUp0y"), testPowerUps.get(0).getY(), 0);
        assertEquals(testSaveManager.getPreferences().get(5).getInteger("numPowerUps"), 1);

        //Test remaining attributes are saved
        assertEquals(testSaveManager.getPreferences().get(5).getBoolean("hasUsedSave"), true);
    }

    /**
     * Tests for the loadAttributes() method of the SaveManager class
     */
    @Test
    public void testLoadAttributes()
    {
        //Create test objects
        List<FireTruck> testFireTrucks = new ArrayList<FireTruck>();
        List<Fortress> testFortresses = new ArrayList<Fortress>();
        List<UFO> testUfos = new ArrayList<UFO>();
        List<PowerUps> testPowerUps = new ArrayList<PowerUps>();
        FireTruck testFireTruck = new FireTruck();
        testFireTruck.setHealthToFixedValue(50);
        testFireTrucks.add(testFireTruck);
        Fortress testFortress = new Fortress();
        testFortresses.add(testFortress);
        UFO testUfo = new UFO(new Vector2(100, 100));
        testUfos.add(testUfo);
        PowerUps testPowerUp = new PowerUps(new Vector2(100, 100), hud);
        testPowerUps.add(testPowerUp);
        SaveManager testSaveManager = new SaveManager(testFireTrucks, testUfos, testFortresses, testPowerUps);

        //Setup all the data we will need for testing
        //Setup all the fortress positions and sizes
        List<Vector2> fortressPositions = new ArrayList<>();
        fortressPositions.add(new Vector2(2860, 3211));
        fortressPositions.add(new Vector2(3130, 5530));
        fortressPositions.add(new Vector2(2010, 1900));
        fortressPositions.add(new Vector2(4270, 870));
        fortressPositions.add(new Vector2(5940, 1150));
        fortressPositions.add(new Vector2(520, 3500));
        List<Vector2> fortressSizes = new ArrayList<>();
        fortressSizes.add(new Vector2(256, 218));
        fortressSizes.add(new Vector2(256, 320));
        fortressSizes.add(new Vector2(400, 256));
        fortressSizes.add(new Vector2(450, 256));
        fortressSizes.add(new Vector2(400, 256));
        fortressSizes.add(new Vector2(450, 256));

        //Save attributes first, clear all the lists and then load back in
        testSaveManager.saveAttributes();
        testFireTrucks.clear();
        testFortresses.clear();
        testPowerUps.clear();
        testUfos.clear();
        testSaveManager.loadAttributes(new ArrayList<GameObject>(), Kroy.mainGameScreen.textures, fortressPositions, fortressSizes, hud);

        //Tests FireTruck attributes are loaded to what they were saved as
        assertEquals(testFireTrucks.get(0).getX(), testFireTruck.getX(), 0);
        assertEquals(testFireTrucks.get(0).getY(), testFireTruck.getY(), 0);
        assertEquals(testFireTrucks.get(0).getMaxHealthPoints(), testFireTruck.getMaxHealthPoints());
        assertEquals(testFireTrucks.get(0).getHealthPoints(), testFireTruck.getHealthPoints());
        assertEquals(testFireTrucks.get(0).getFlowRate(), testFireTruck.getFlowRate(), 0);
        assertEquals(testFireTrucks.get(0).getCurrentWater(), testFireTruck.getCurrentWater(), 0);
        assertEquals(testFireTrucks.get(0).getMaxWater(), testFireTruck.getMaxWater(), 0);
        assertEquals(testFireTrucks.get(0).getRotation(), testFireTruck.getRotation(), 0);
        assertEquals(testFireTrucks.get(0).getRange(), testFireTruck.getRange(), 0);
        assertEquals(testFireTrucks.get(0).getSpeed(), testFireTruck.getSpeed(), 0);

        //Test Fortress attributes are loaded to what they were saved as
        assertEquals(testFortresses.get(0).getHealthPoints(), testFortress.getHealthPoints());
        assertEquals(testFortresses.get(0).getMaxHealthPoints(), testFortress.getMaxHealthPoints());

        //Test UFO attributes are saved
        assertEquals(testUfos.get(0).getX(), testUfo.getX(), 0);
        assertEquals(testUfos.get(0).getY(), testUfo.getY(), 0);
        assertEquals(testUfos.get(0).getHealthPoints(), testUfo.getHealthPoints());
        assertEquals(testUfos.get(0).getMaxHealthPoints(), testUfo.getMaxHealthPoints());

        //Tests PowerUp attributes are saved
        assertEquals(testPowerUps.get(0).getType().getValue(), testPowerUp.getType().getValue());
        assertEquals(testPowerUps.get(0).getX(), testPowerUp.getX(), 0);
        assertEquals(testPowerUps.get(0).getY(), testPowerUp.getY(), 0);
    }
}
