package com.dicycat.kroy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.HUD;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Junit tests for the GameOverScreen class
 * Have not tested the render() method as this just draws to the screen so cannot be tested with JUnit
 * Also not tested updateHighScore() as this involves file manipulation which is hard to test with JUnit
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class GameOverScreenTest
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
        Gdx.graphics = new MockGraphics();

        kroy = Mockito.mock(Kroy.class, Mockito.CALLS_REAL_METHODS);
        kroy.batch = Mockito.mock(SpriteBatch.class);
        hud = new HUD(kroy.batch, kroy);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.setHud(hud);
        kroy.setHighScore(10000);
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
    }

    /**
     * Tests the constructor of the GameOverScreen class
     */
    @Test
    public void testConstructor()
    {
        //Creates the test objects
        GameOverScreen testGameOverScreen = new GameOverScreen(kroy, 0, false);

        //Test values are assigned correctly with result =  false
        assertEquals(testGameOverScreen.getGame(), kroy);
        assertEquals(testGameOverScreen.isResult(), false);
        assertEquals(testGameOverScreen.getTruckNum(), 0);
        assertEquals(testGameOverScreen.getScore(), 0);
        assertEquals(testGameOverScreen.getScoreLabel().getText().toString(), "YOUR SCORE:");
        assertEquals(testGameOverScreen.getScoreNumberLabel().getText().toString(), String.format("%05d", testGameOverScreen.getScore()));
        assertEquals(testGameOverScreen.getHighScoreLabel().getText().toString(), "HIGH SCORE:");
        assertEquals(testGameOverScreen.getHighScoreNumberLabel().getText().toString(), String.format("%05d", testGameOverScreen.getHighScore()));
        assertEquals(testGameOverScreen.getTable().getCells().get(0).getActor(), testGameOverScreen.getScoreLabel());
        assertEquals(testGameOverScreen.getTable().getCells().get(1).getActor(), testGameOverScreen.getScoreNumberLabel());
        assertEquals(testGameOverScreen.getTable().getCells().get(2).getActor(), testGameOverScreen.getHighScoreLabel());
        assertEquals(testGameOverScreen.getTable().getCells().get(3).getActor(), testGameOverScreen.getHighScoreNumberLabel());

        //Tests that values are assigned correctly when result = true
        HUD.setScore(20000);
        testGameOverScreen = new GameOverScreen(kroy, 0, true);
        assertEquals(testGameOverScreen.getGame().getHighScore().intValue(), 20000);

        //Line added so line would be tested - method does nothing
        testGameOverScreen.show();
        testGameOverScreen.pause();
        testGameOverScreen.resume();
        testGameOverScreen.hide();
        testGameOverScreen.dispose();
    }

    /**
     * Tests the resize() method of the GameOverScreen class
     */
    @Test
    public void testResize()
    {
        //Creates the test objects
        GameOverScreen testGameOverScreen = new GameOverScreen(kroy, 0, false);

        //Call method to be tested
        testGameOverScreen.resize(500, 500);

        //Check values assigned correctly
        assertEquals(testGameOverScreen.getGameport().getScreenWidth(), 500);
    }
}
