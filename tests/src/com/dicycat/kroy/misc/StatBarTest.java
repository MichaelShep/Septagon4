package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

/**
 * JUnit tests for the StatBar class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class StatBarTest
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
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
        Kroy.mainGameScreen.textures.initFireTruckTexture(0);
    }

    /**
     * Tests the constructor of the StatBar class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        StatBar testStatBar = new StatBar(new Vector2(100, 100), "atom.png", 5);

        //Check values are assigned correctly
        assertEquals(testStatBar.isRemove(), false);
        assertEquals(testStatBar.isDisplayable(), false);
        assertEquals(testStatBar.getTexture().toString(), "atom.png");
        assertEquals(testStatBar.getWidth(), 1, 0);
        assertEquals(testStatBar.getHeight(), 1, 0);
        assertEquals(testStatBar.getBarHeight(), 5);
    }

    /**
     * Tests the barDisplay() method of the StatBar class
     */
    @Test
    public void testBarDisplay()
    {
        //Create test objects
        StatBar testStatBar = new StatBar(new Vector2(100, 100), "atom.png", 5);

        //Calls the method to be tested
        testStatBar.setBarDisplay(10);

        //Checks values are updated as expected
        assertEquals(testStatBar.getXScale(), 10, 0);
    }
}
