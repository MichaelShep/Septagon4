package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
 * JUnit tests for the PauseWindow class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class PauseWindowTest
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
     * Tests the constructor of the PauseWindow class
     */
    @Test
    public void testConstructor()
    {
        //Create test object
        PauseWindow testPauseWindow = new PauseWindow(kroy);

        //Tests values are assigned correctly
        assertEquals(testPauseWindow.getSb(), kroy.batch);
        assertEquals(testPauseWindow.getTable().getCells().get(0).getActor(), testPauseWindow.resume);
        assertEquals(testPauseWindow.getTable().getCells().get(1).getActor(), testPauseWindow.save);
        assertEquals(testPauseWindow.getTable().getCells().get(2).getActor(), testPauseWindow.menu);
        assertEquals(testPauseWindow.getTable().getCells().get(3).getActor(), testPauseWindow.exit);
    }
}
