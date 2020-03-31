package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the FireStation class
 * Note that no test class has been made for the Entity class and GameObject class as we feel that by testing their child classes, we have sufficiently
 * tested the parent classes as well
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class FireStationTest
{
    private Kroy kroy;

    /**
     * Sets up the environment for allowing us to test with LibGDX
     */
    @Before
    public void init()
    {
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        kroy = Mockito.mock(Kroy.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
    }

    /**
     * Tests the constructor for the FireStation class
     */
    @Test
    public void testConstructor()
    {
        //Create Test object to perform testing on
        FireStation testFireStation = new FireStation();

        //Check values are assigned correctly - x and y positions have not being tested due to this being difficult as
        //they are modified so that can fit the projection matrix correctly
        assertEquals(testFireStation.getTexture(), FireStation.getTextureLiving());
        assertEquals(testFireStation.getWidth(), 298, 0);
        assertEquals(testFireStation.getHeight(), 175, 0);
        assertEquals(testFireStation.getHealthPoints(), 100);
        assertEquals(testFireStation.getMaxHealthPoints(), 100);
        assertEquals(testFireStation.getRadius(), 500);
    }

    /**
     * Tests the die() method from the FireStation class
     */
    @Test
    public void testDie()
    {
        //Create Test object to perform testing on
        FireStation testFireStation = new FireStation();

        //Execute method to be tested
        testFireStation.die();

        //Check values are assigned correctly
        assertEquals(testFireStation.isRemove(), true);
        assertEquals(testFireStation.isDead(), true);
        assertEquals(testFireStation.getTexture(), FireStation.getTexturedead());
        assertEquals(testFireStation.isDisplayable(), true);
    }

    /**
     * Tests the update() method from the FireStation class
     */
    @Test
    public void testUpdate()
    {
        //Create Test object to perform testing on as well as fire truck object that will be used for testing
        FireStation testFireStation = new FireStation();
        FireTruck testFireTruck = new FireTruck();
        testFireTruck.setHealthToFixedValue(0);
        testFireTruck.setCurrentWaterToFixedValue(0);
        //Move the truck outside the range of the station
        testFireTruck.setPosition(new Vector2(testFireStation.getX() + testFireStation.getRadius() + 5, testFireStation.getY() + testFireStation.getRadius() + 5));

        Kroy.mainGameScreen.setPlayer(testFireTruck);
        Kroy.mainGameScreen.setGameTimer(100);

        //Checks that when fire truck out of range, its health and water do not get replenished
        testFireStation.update();
        assertEquals(testFireTruck.getHealthPoints(), 0);
        assertEquals(testFireTruck.getCurrentWater(), 0, 0);

        //Checks that when the gameTimer > 0, stations health is not reduced
        assertEquals(testFireStation.getHealthPoints(), testFireStation.getMaxHealthPoints());

        //Move the truck into the range of the station
        testFireTruck.setPosition(new Vector2(testFireStation.getX(), testFireStation.getY()));
        Kroy.mainGameScreen.setGameTimer(0);

        //Checks that when fire truck in range, its health and water get replenished by 2
        testFireStation.update();
        assertEquals(testFireTruck.getCurrentWater(), 2, 0);
        assertEquals(testFireTruck.getHealthPoints(), 2);

        //Checks that when gameTimer <= 0, stations health is set to 0
        assertEquals(testFireStation.getHealthPoints(), 0);
    }
}
