package com.dicycat.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for the MenuScreen class
 */

@RunWith(GdxTestRunner.class)
public class MenuScreenTest
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
     * Tests the constructor of the MenuScreen class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        MenuScreen testMenuScreen = new MenuScreen(kroy);

        //Checks values are assigned correctly
        assertEquals(testMenuScreen.getExitButton().toString(), "EXIT.png");
        assertEquals(testMenuScreen.getExitButtonActive().toString(), "exitActive.png");
        assertEquals(testMenuScreen.getOptionsButton().toString(), "options.png");
        assertEquals(testMenuScreen.getOptionsButtonActive().toString(), "optionsActive.png");
        assertEquals(testMenuScreen.getPlayButton().toString(), "newgame.png");
        assertEquals(testMenuScreen.getPlayButtonActive().toString(), "newActive.png");
        assertEquals(testMenuScreen.getMinigameButton().toString(), "minigame.png");
        assertEquals(testMenuScreen.getMinigameButtonActive().toString(), "minigameActive.png");
        assertEquals(testMenuScreen.getLoadGameButton().toString(), "load_game.png");
        assertEquals(testMenuScreen.getBackground().toString(), "fireforce.png");

        //Added so that methods are tested - method that dont do anything
        testMenuScreen.show();
        testMenuScreen.pause();
        testMenuScreen.resume();
        testMenuScreen.hide();
        testMenuScreen.dispose();
    }

    /**
     * Tests the difficultyClickCheck() method of the MenuScreen class
     */
    @Test
    public void testDifficultyClickCheck()
    {
        //Create test objects
        MenuScreen testMenuScreen = new MenuScreen(kroy);

        //Call method to be tested
        testMenuScreen.difficultyClickCheck();

        //Checks values are assigned correctly
        assertNotNull(testMenuScreen.getDifficultySelector().easyButton.getListeners().get(2));
        assertNotNull(testMenuScreen.getDifficultySelector().mediumButton.getListeners().get(2));
        assertNotNull(testMenuScreen.getDifficultySelector().hardButton.getListeners().get(2));
    }

    /**
     * Test the loadClickCheck() method of the MenuScreen class
     */
    @Test
    public void testLoadClickCheck()
    {
        //Create test objects
        MenuScreen testMenuScreen = new MenuScreen(kroy);

        //Call method to be tested
        testMenuScreen.loadGameClickCheck();

        //Checks values are assigned correctly - can only check back button as the rest of the buttons rely on saved games
        assertNotNull(testMenuScreen.getLoadGameSelector().backButton.getListeners().get(2));
    }

    /**
     * Test the clickCheck() method of the MenuScreen class
     */
    @Test
    public void testClickCheck()
    {
        //Create test objects
        MenuScreen testMenuScreen = new MenuScreen(kroy);

        //Call method to be tested
        testMenuScreen.clickCheck();

        //Check values are assigned correctly
        assertNotNull(testMenuScreen.getFireTruckSelector().truckButton1.getListeners().get(2));
        assertNotNull(testMenuScreen.getFireTruckSelector().truckButton2.getListeners().get(2));
        assertNotNull(testMenuScreen.getFireTruckSelector().truckButton3.getListeners().get(2));
        assertNotNull(testMenuScreen.getFireTruckSelector().truckButton4.getListeners().get(2));
        assertNotNull(testMenuScreen.getFireTruckSelector().truckButton5.getListeners().get(2));
        assertNotNull(testMenuScreen.getFireTruckSelector().truckButton6.getListeners().get(2));
    }

    /**
     * Tests the startGame() method of the MenuScreen class
     */
    @Test
    public void testStartGame()
    {
        //Create test objects
        MenuScreen testMenuScreen = new MenuScreen(kroy);

        //Call method to be tested
        testMenuScreen.setCurrentlyRunningGame(false);
        testMenuScreen.startGame(0, 5);

        //Checks values are assigned correctly
        assertEquals(testMenuScreen.isCurrentlyRunningGame(), true);
    }

    /**
     * Tests the startMinigame() method of the MenuScreen class
     */
    @Test
    public void testStartMinigame()
    {
        //Create test objects
        MenuScreen testMenuScreen = new MenuScreen(kroy);

        //Call method to be tested
        testMenuScreen.setCurrentlyRunningGame(false);
        testMenuScreen.startMinigame();

        //Checks values are assigned correctly
        assertEquals(testMenuScreen.isCurrentlyRunningGame(), true);
    }
}
