package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.DifficultyMultiplier;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the UFO class
 * Note have not tested the moveInDirection() method as this relies on game time which cannot be exactly calculated here
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class UFOTest
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
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
    }

    /**
     * Tests the constructor for the UFO class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        UFO testUfo = new UFO(new Vector2(100, 100));

        //Check values assigned correctly
        assertEquals(testUfo.getWidth(), 80, 0);
        assertEquals(testUfo.getHeight(), 80, 0);
        assertEquals(testUfo.isRemove(), false);
        assertEquals(testUfo.isDisplayable(), false);
        assertEquals(testUfo.getHealthPoints(), 100);
        assertEquals(testUfo.getMaxHealthPoints(), 100);
        assertEquals(testUfo.getRadius(), 500);
        assertEquals(testUfo.getSpawnPos(), new Vector2(100, 100));
    }

    /**
     * Tests the update() method of the UFO class
     */
    @Test
    public void testUpdate()
    {
        //Create test objects
        UFO testUfo = new UFO(new Vector2(100, 100));
        FireTruck testTruck = new FireTruck();
        testTruck.setPosition(testUfo.getPosition());
        Kroy.mainGameScreen.setPlayer(testTruck);

        //Checks that when moveTimer < 2, the direction stays the same
        int initialDirection = testUfo.getDirection();
        testUfo.update();
        assertEquals(testUfo.getDirection(), initialDirection);

        //Checks that when moveTimer >= 2, direction is increased by 90
        testUfo.setMovetimer(2.0f);
        testUfo.update();
        assertEquals(testUfo.getDirection(), initialDirection + 90);

        //Check that when direction > 270 and moveTimer >= 2, direction is reset to 0
        testUfo.setMovetimer(2);
        testUfo.setDirection(280);
        testUfo.update();
        assertEquals(testUfo.getDirection(), 0);

        //Check that if a bullet should be fired and a player is in range, the bullet is fired
        testUfo.getDispenser().setBulletTimer(testUfo.getDispenser().getFiringPattern().getWaitTime());
        testUfo.getDispenser().setPatternTimer(testUfo.getDispenser().getFiringPattern().getCooldown());
        testUfo.update();
        assertEquals(testUfo.getDispenser().getFiringPattern().bulletSet(0)[0].getVelocity(), Kroy.mainGameScreen.getPlayer().getCentre().sub(testUfo.getCentre()).nor().scl(testUfo.getDispenser().getFiringPattern().bulletSet(0)[0].getSpeed()));
        assertEquals(testUfo.getDispenser().getFiringPattern().bulletSet(0)[0].getTravelDist(), testUfo.getDispenser().getFiringPattern().bulletSet(0)[0].getMaxDist(), 0);
        assertEquals(testUfo.getDispenser().getFiringPattern().bulletSet(0)[0].isRemove(), false);
    }
}
