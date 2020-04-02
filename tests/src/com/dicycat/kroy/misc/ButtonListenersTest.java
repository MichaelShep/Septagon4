package com.dicycat.kroy.misc;

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
import static org.junit.Assert.assertNotNull;

/**
 * JUnit tests for the ButtonListeners Class
 * Created By Septagon
 */

@RunWith(GdxTestRunner.class)
public class ButtonListenersTest
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
        Kroy.mainGameScreen.setupWindows(kroy);
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
    }

    /**
     * Tests the constructor of the ButtonListeners Class
     */
    @Test
    public void testConstructor()
    {
        //Create Test Objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        ButtonListeners testButtonListeners = new ButtonListeners(testGameScreen, testSaveManager);

        //Check values have been assigned correctly
        assertEquals(testButtonListeners.getGameScreen(), testGameScreen);
        assertEquals(testButtonListeners.getSaveManager(), testSaveManager);
    }

    /**
     * Tests the pauseListeners() method of the ButtonListeners class
     */
    @Test
    public void testPauseListeners()
    {
        //Create Test Objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        ButtonListeners testButtonListeners = new ButtonListeners(testGameScreen, testSaveManager);

        //Checks that all the buttons on the pauseWindow have had the listener added (is the 3rd element in the list of listeners)
        assertNotNull(testGameScreen.getPauseWindow().resume.getListeners().get(2));
        assertNotNull(testGameScreen.getPauseWindow().exit.getListeners().get(2));
        assertNotNull(testGameScreen.getPauseWindow().menu.getListeners().get(2));
        assertNotNull(testGameScreen.getPauseWindow().save.getListeners().get(2));
    }

    /**
     * Tests the saveScreenListeners() method of the ButtonListeners class
     */
    @Test
    public void testSaveScreenListeners()
    {
        //Create Test Objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        SaveManager testSaveManager = Mockito.mock(SaveManager.class);
        ButtonListeners testButtonListeners = new ButtonListeners(testGameScreen, testSaveManager);

        //Checks that all the buttons on the saveWindow have had the listener added (its the 3rd element in the list of listeners)
        assertNotNull(testGameScreen.getSaveWindow().saveGame1Button.getListeners().get(2));
        assertNotNull(testGameScreen.getSaveWindow().saveGame2Button.getListeners().get(2));
        assertNotNull(testGameScreen.getSaveWindow().saveGame3Button.getListeners().get(2));
        assertNotNull(testGameScreen.getSaveWindow().saveGame4Button.getListeners().get(2));
        assertNotNull(testGameScreen.getSaveWindow().saveGame5Button.getListeners().get(2));
        assertNotNull(testGameScreen.getSaveWindow().backButton.getListeners().get(2));
    }
}
