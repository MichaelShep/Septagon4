package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.PowerType;
import com.dicycat.kroy.entities.PowerUps;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * JUnit tests for the HUD class
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class HUDTest
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

        kroy = Mockito.mock(Kroy.class, Mockito.CALLS_REAL_METHODS);
        kroy.batch = Mockito.mock(SpriteBatch.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
    }

    /**
     * Tests the constructor of the HUD class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        HUD testHud = new HUD(kroy.batch, kroy);

        //Check values are assigned correctly
        assertEquals(testHud.getTableHUD().getCells().get(0).getActor(), testHud.getTimeLabel());
        assertEquals(testHud.getTableHUD().getCells().get(1).getActor(), testHud.getWorldTimerLabel());
        assertEquals(testHud.getTableHUD().getCells().get(2).getActor(), testHud.getScoreLabel());
        assertEquals(testHud.getTableHUD().getCells().get(3).getActor(), testHud.getScoreCountLabel());
        assertEquals(testHud.getTableHUD().getCells().get(4).getActor(), testHud.getTrucksLabel());
        assertEquals(testHud.getTableHUD().getCells().get(5).getActor(), testHud.getTrucksCountLabel());
        assertEquals(testHud.getTableHUD().getCells().get(6).getActor(), testHud.getFortressesLabel());
        assertEquals(testHud.getTableHUD().getCells().get(7).getActor(), testHud.getFortressCountLabel());
    }

    /**
     * Tests the update() method of the HUD class
     */
    @Test
    public void testUpdate()
    {
        //Create test objects
        HUD testHud = new HUD(kroy.batch, kroy);
        Kroy.mainGameScreen.setPlayer(new FireTruck());

        //Checks that if timeCount + parameter is less than 1, timeCount = timeCount + parameter and nothing else happens
        testHud.setTimeCount(0);
        testHud.update(0.5f);
        assertEquals(testHud.getTimeCount(), 0.5f, 0);

        //Checks that if world timer is greater than 0, it is decreased by 1
        testHud.setWorldTimer(3);
        int initialScore = testHud.getScore();

        testHud.update(2);
        assertEquals(testHud.getWorldTimer(), 2, 0);

        //Check the score is updated correctly, and the timer
        assertEquals(testHud.getScore(), initialScore - 220);
        assertEquals(testHud.getTimeCount(), 0, 0);

        //Checks that all the labels are updated with the correct value
        Kroy.mainGameScreen.removeFortress();
        testHud.update(2);
        assertEquals(testHud.getWorldTimerLabel().getText().toString(), String.format("%03d", (int)testHud.getWorldTimer()));
        assertEquals(testHud.getScoreCountLabel().getText().toString(), String.format("%05d", testHud.getScore()));
        assertEquals(testHud.getTrucksCountLabel().getText().toString(), String.format("%01d", Kroy.mainGameScreen.getLives()));
        assertEquals(testHud.getFortressCountLabel().getText().toString(), String.format("%01d", Kroy.mainGameScreen.fortressesLeft()));

        //Check that is displayingPowerUp and the powerUp != null, displays the powerUp message
        PowerUps testPowerUps = new PowerUps(new Vector2(100, 100), testHud);
        testPowerUps.setType(PowerType.FASTSHOOTING);
        testPowerUps.setDuration(20);
        testHud.setCurrentPowerUp(testPowerUps);
        testHud.setDisplayingPowerUp(true);
        testHud.update(2);
        assertEquals(testHud.getPowerUpLabel().getText().toString(), "You have " + testPowerUps.toString() + " for " + testPowerUps.getDuration() + " seconds");

        //Check that if displaying powerUp and powerUp == null, powerUp message is set to null
        testPowerUps.setDuration(0);
        testHud.setCurrentPowerUp(testPowerUps);
        testHud.update(2);
        assertNull(testHud.getPowerUpLabel());
    }

    /**
     * Tests the addPowerUpMessage() method of the HUD class
     */
    @Test
    public void testAddPowerUpMessage()
    {
        //Create test objects
        HUD testHud = new HUD(kroy.batch, kroy);
        PowerUps testPowerUp = new PowerUps(new Vector2(100, 100), testHud);
        testPowerUp.setType(PowerType.FASTSHOOTING);

        //Call method to be tested
        testHud.addPowerUpMessage(testPowerUp);

        //Check values are assigned correctly
        assertEquals(testHud.getCurrentPowerUp(), testPowerUp);
        assertEquals(testHud.isDisplayingPowerUp(), true);
        assertEquals(testHud.getPowerUpLabel().getText().toString(), "You have " + testPowerUp.toString() + " for " + testPowerUp.getDuration() + " seconds");
        assertEquals(testHud.getPowerUpLabel().getX(), Gdx.graphics.getWidth() / 2 - testHud.getPowerUpLabel().getWidth() / 2, 0);
        assertEquals(testHud.getPowerUpLabel().getY(), Gdx.graphics.getHeight() - 50, 0);
    }

    /**
     * Tests the removePowerUpMessage() method of the HUD class
     */
    @Test
    public void testRemovePowerUpMessage()
    {
        //Create test objects
        HUD testHud = new HUD(kroy.batch, kroy);
        Kroy.mainGameScreen.setPlayer(new FireTruck());
        PowerUps testPowerUp = new PowerUps(new Vector2(100, 100), testHud);
        testPowerUp.setType(PowerType.FASTSHOOTING);
        testHud.addPowerUpMessage(testPowerUp);

        //Call method to be tested
        testHud.removePowerUpMessage();

        //Check values are assigned correctly
        assertEquals(testHud.isDisplayingPowerUp(), false);
        assertNull(testHud.getPowerUpLabel());
        assertNull(testHud.getCurrentPowerUp());
    }
}
