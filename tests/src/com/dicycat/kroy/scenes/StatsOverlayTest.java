package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the StatsOverlay class
 * Only tested the constructor as checkMousePosition() relies on mouse input which is hard to test
 * And render() just draws to the screen so cannot be tested with JUnit
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class StatsOverlayTest
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
     * Tests for the constructor of the StatOverlay class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        OrthographicCamera testCamera = new OrthographicCamera();
        List<FireTruck> testFireTrucks = new ArrayList<FireTruck>();
        StatsOverlay testStatsOverlay = new StatsOverlay(testFireTrucks, testCamera);

        //Test values are assigned correctly
        assertEquals(testStatsOverlay.getFireTrucks(), testFireTrucks);
        assertEquals(testStatsOverlay.getCamera(), testCamera);
        assertEquals(testStatsOverlay.getBackgroundTexture().toString(), "overlayRect.png");
    }

}
