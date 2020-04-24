package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PowerUpsTest
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

        kroy = Mockito.mock(Kroy.class);
        hud = Mockito.mock(HUD.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
    }

    /**
     * Tests the first constructor of the PowerUps class
     */
    @Test
    public void testConstructor1()
    {
        //Creates test objects to be used to perform tests
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), hud);

        //Test that values are assigned correctly - positions and textures not checked as these are difficult to assert
        assertEquals(testPowerUps.getWidth(), 16, 0);
        assertEquals(testPowerUps.getHeight(), 16, 0);
        assertEquals(testPowerUps.isRemove(), false);
        assertEquals(testPowerUps.isDisplayable(), false);
        assertEquals(testPowerUps.getHealthPoints(), 1);
        assertEquals(testPowerUps.getMaxHealthPoints(), 1);
        assertEquals(testPowerUps.getRadius(), 25);
        assertEquals(testPowerUps.getHud(), hud);
        assertTrue(testPowerUps.getType() == (PowerType.FULLHEALTH) || testPowerUps.getType() == (PowerType.FASTSHOOTING) || testPowerUps.getType() == (PowerType.RANGE) ||
                testPowerUps.getType() == (PowerType.REFILLWATER) || testPowerUps.getType() == (PowerType.SPEED));
    }

    /**
     * Tests the second constructor of the PowerUps class
     */
    @Test
    public void testConstructor2()
    {
        //Creates test objects to be used to perform tests
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), hud, 1);

        //Test that values are assigned correctly - positions and textures not checked as these are difficult to assert
        assertEquals(testPowerUps.getWidth(), 16, 0);
        assertEquals(testPowerUps.getHeight(), 16, 0);
        assertEquals(testPowerUps.isRemove(), false);
        assertEquals(testPowerUps.isDisplayable(), false);
        assertEquals(testPowerUps.getHealthPoints(), 1);
        assertEquals(testPowerUps.getMaxHealthPoints(), 1);
        assertEquals(testPowerUps.getRadius(), 25);
        assertEquals(testPowerUps.getHud(), hud);
        assertTrue(testPowerUps.getType() == PowerType.FASTSHOOTING);
    }

    /**
     * Tests the update() method of the PowerUps class
     */
    @Test
    public void testUpdate()
    {
        //Creates test objects to be used to perform tests
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), hud);
        FireTruck testTruck = new FireTruck();
        Kroy.mainGameScreen.setPlayer(testTruck);

        //Checks that when no truck in the range of the powerUp, powerUp not added to hud
        testPowerUps.update();
        assertEquals(hud.getCurrentPowerUp(), null);

        //Checks that when truck in range, powerUp applied to player and health reduced by 1
        //Checks the increase speed powerup
        testPowerUps.setType(PowerType.SPEED);
        float initialSpeed = testTruck.getSpeed();
        int initialHealth = testPowerUps.getHealthPoints();
        testTruck.setPosition(testPowerUps.getPosition());
        testPowerUps.update();
        assertEquals(testPowerUps.isShouldRemove(), true);
        assertEquals(testTruck.getSpeed(), initialSpeed + FireTruck.SPEED_INCREASE, 0);
        assertEquals(testPowerUps.getHealthPoints(), initialHealth - 1);

        //Checks the full health powerUp
        testPowerUps.setType(PowerType.FULLHEALTH);
        testTruck.setHealthToFixedValue(0);
        initialHealth = testPowerUps.getHealthPoints();
        testPowerUps.update();
        assertEquals(testPowerUps.isShouldRemove(), true);
        assertEquals(testTruck.getHealthPoints(), testTruck.getMaxHealthPoints(), 0);
        assertEquals(testPowerUps.getHealthPoints(), initialHealth - 1);

        //Checks the refill water powerUp
        testPowerUps.setType(PowerType.REFILLWATER);
        testTruck.setCurrentWaterToFixedValue(0);
        initialHealth = testPowerUps.getHealthPoints();
        testPowerUps.update();
        assertEquals(testPowerUps.isShouldRemove(), true);
        assertEquals(testTruck.getCurrentWater(), testTruck.getMaxWater(), 0);
        assertEquals(testPowerUps.getHealthPoints(), initialHealth - 1);

        //Checks the increase range powerUp
        testPowerUps.setType(PowerType.RANGE);
        float initialRange = testTruck.getRange();
        initialHealth = testPowerUps.getHealthPoints();
        testPowerUps.update();
        assertEquals(testPowerUps.isShouldRemove(), true);
        assertEquals(testTruck.getRange(), initialRange + FireTruck.RANGE_INCREASE, 0);
        assertEquals(testPowerUps.getHealthPoints(), initialHealth - 1);

        //Checks the increase shooting powerUp
        testPowerUps.setType(PowerType.FASTSHOOTING);
        float initialFlowRate = testTruck.getFlowRate();
        initialHealth = testPowerUps.getHealthPoints();
        testPowerUps.update();
        assertEquals(testPowerUps.isShouldRemove(), true);
        assertEquals(testTruck.getFlowRate(), initialFlowRate + FireTruck.SHOOTING_INCREASE, 0);
        assertEquals(testPowerUps.getHealthPoints(), initialHealth - 1);
    }

    /**
     * Tests the remove() method of the PowerUps class
     */
    @Test
    public void testRemove()
    {
        //Creates test objects to be used to perform tests
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), hud);
        FireTruck testTruck = new FireTruck();
        Kroy.mainGameScreen.setPlayer(testTruck);

        //Tests that players stats are reset once the powerUp has been removed

        //Checks the flow rate powerUp
        testPowerUps.setType(PowerType.FASTSHOOTING);
        float initialFlowRate = testTruck.getFlowRate();
        testPowerUps.remove();
        assertEquals(testTruck.getFlowRate(), initialFlowRate - FireTruck.SHOOTING_INCREASE, 0);

        //Checks the speed increase powerUp
        testPowerUps.setType(PowerType.SPEED);
        float initialSpeed = testTruck.getSpeed();
        testPowerUps.remove();
        assertEquals(testTruck.getSpeed(), initialSpeed - FireTruck.SPEED_INCREASE, 0);

        //Checks the range powerUp
        testPowerUps.setType(PowerType.RANGE);
        float initialRange = testTruck.getRange();
        testPowerUps.remove();
        assertEquals(testTruck.getRange(), initialRange - FireTruck.RANGE_INCREASE, 0);
    }

    /**
     * Tests the toString() method of the PowerUps class
     */
    @Test
    public void testToString()
    {
        //Creates test objects to be used to perform tests
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), hud);

        //Tests the the String for each PowerUp type is correct
        testPowerUps.setType(PowerType.FULLHEALTH);
        assertEquals(testPowerUps.toString(), "Full Health");
        testPowerUps.setType(PowerType.FASTSHOOTING);
        assertEquals(testPowerUps.toString(), "Fast Shooting");
        testPowerUps.setType(PowerType.RANGE);
        assertEquals(testPowerUps.toString(), "Increased Range");
        testPowerUps.setType(PowerType.REFILLWATER);
        assertEquals(testPowerUps.toString(), "Refilled Water");
        testPowerUps.setType(PowerType.SPEED);
        assertEquals(testPowerUps.toString(), "Speed Up");
    }

    /**
     * Tests the setPowerUpTexture() method of the PowerUps class
     */
    @Test
    public void testSetPowerUpTexture()
    {
        //Creates test objects to be used to perform tests
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), hud);


        //Tests that the texture for each PowerUp type is correct
        testPowerUps.setType(PowerType.FULLHEALTH);
        assertEquals(testPowerUps.getTexture().toString(), "health.png");
        testPowerUps.setType(PowerType.FASTSHOOTING);
        assertEquals(testPowerUps.getTexture().toString(), "flame.png");
        testPowerUps.setType(PowerType.RANGE);
        assertEquals(testPowerUps.getTexture().toString(), "atom.png");
        testPowerUps.setType(PowerType.REFILLWATER);
        assertEquals(testPowerUps.getTexture().toString(), "raindrop.png");
        testPowerUps.setType(PowerType.SPEED);
        assertEquals(testPowerUps.getTexture().toString(), "thunder.png");
    }
}
