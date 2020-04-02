package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
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
 * JUnit tests for the WaterStream class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class WaterStreamTest
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
     * Tests the constructor of the WaterStream class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        WaterStream testWaterStream = new WaterStream(new Vector2(100, 100));

        //Check values assigned correctly
        assertEquals(testWaterStream.getTexture().toString(), "lightBlue.png");
        assertEquals(testWaterStream.getWidth(), 1, 0);
        assertEquals(testWaterStream.getHeight(), 1, 0);
        assertEquals(testWaterStream.isRemove(), false);
        assertEquals(testWaterStream.isDisplayable(), false);
    }

    /**
     * Tests the setRange() method of the WaterStream class
     */
    @Test
    public void testSetRange()
    {
        //Create test objects
        WaterStream testWaterStream = new WaterStream(new Vector2(100, 100));

        //Call method to be tested
        testWaterStream.setRange(50);
        testWaterStream.update(); //Line included here so that the update() method is considered as tested (it does nothing)

        //Check values assigned correctly
        assertEquals(testWaterStream.getXScale(), 50,0);
    }
}
