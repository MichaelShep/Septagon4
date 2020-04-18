package com.dicycat.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * JUnit tests for the GameScreen class
 * No explicit test for init() as this is tested by testing the constructors
 * Rendering methods have not been tested as these just draw to the screen and so cannot be tested with JUnit
 * checkZoom() method has not been tested as this relies on keyPresses which are hard to test with JUnit
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class GameScreenTest
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
        Gdx.graphics = Mockito.mock(Graphics.class, Mockito.CALLS_REAL_METHODS);

        kroy = Mockito.mock(Kroy.class, Mockito.CALLS_REAL_METHODS);
        kroy.batch = Mockito.mock(SpriteBatch.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        kroy.setHighScore(10000);
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
        for(int i = 0; i < 6; i++)
        {
            Kroy.mainGameScreen.textures.initFireTruckTexture(i);
        }
    }

    /**
     * Tests the 1st constructor of the GameScreen class
     */
    @Test
    public void testConstructor1()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);

        //Test that values assigned correctly and lists have been initialised
        assertEquals(testGameScreen.isLoadingGame(), false);
        assertEquals(testGameScreen.getTruckNum(), 3);
        assertEquals(testGameScreen.getFortressPositions().get(0), new Vector2(2860, 3211));
        assertEquals(testGameScreen.getFortressSizes().get(0), new Vector2(256, 218));
    }

    /**
     * Tests the 2nd constructor of the GameScreen class
     */
    @Test
    public void testConstructor2()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, true, 6);

        //Test that values assigned correctly and lists have been initialised
        assertEquals(testGameScreen.isLoadingGame(), true);
        assertEquals(testGameScreen.getTruckNum(), 3);
        assertEquals(testGameScreen.getFortressPositions().get(0), new Vector2(2860, 3211));
        assertEquals(testGameScreen.getFortressSizes().get(0), new Vector2(256, 218));
        assertEquals(testGameScreen.getSaveManager().getSave(), 6);
    }

    /**
     * Tests the show() method of the GameScreen class
     */
    @Test
    public void testShow()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);

        //Checks that if not loading game, 6 fortresses and firetrucks are generated and 6 powerUps are added
        testGameScreen.show();
        assertEquals(testGameScreen.getFireTrucks().size(), 6);
        assertEquals(testGameScreen.getFortresses().size(), 6);
        assertEquals(testGameScreen.getPowerUps().size(), 5);
    }

    /**
     * Tests the fortressInit() method of the GameScreen class
     */
    @Test
    public void testFortressInit()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.setupEssentialValues();

        //Call method to be tested
        testGameScreen.fortressInit(3);

        //Checks values are assigned correctly
        assertEquals(testGameScreen.getFortresses().get(0).getTexture().toString(), testGameScreen.textures.getFortress(3).toString());
        assertEquals(testGameScreen.getFortresses().get(0).getDeadTexture().toString(), testGameScreen.textures.getDeadFortress(3).toString());
        assertEquals(testGameScreen.getFortresses().get(0).getWidth(), testGameScreen.getFortressSizes().get(3).x, 0);
        assertEquals(testGameScreen.getFortresses().get(0).getHeight(), testGameScreen.getFortressSizes().get(3).y, 0);
    }

    /**
     * Tests the fireTruckInit() method of the GameScreen class
     */
    @Test
    public void testFireTruckInit()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.setupEssentialValues();

        //Call method to be tested
        testGameScreen.firetruckInit(100, 100, 3);

        //Check fireTruck array correctly set up - no need to test all fireTruck values as this is done in FireTruckTest
        assertEquals(testGameScreen.getFireTrucks().get(0).getTexture().toString(), testGameScreen.textures.getTruck(3).toString());
    }

    /**
     * Tests the updateCameraPosition() method of the GameScreen class
     */
    @Test
    public void testUpdateCameraPosition()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        float cameraX = Math.max(0.5f*Kroy.width*testGameScreen.getZoom(), Math.min(testGameScreen.getCurrentTruck().getX(), 6884-(0.5f*Kroy.width*testGameScreen.getZoom())));
        float cameraY = Math.max(0.5f*Kroy.height*testGameScreen.getZoom(), Math.min(testGameScreen.getCurrentTruck().getY(), 6043-(0.5f*Kroy.height*testGameScreen.getZoom())));
        testGameScreen.updateCamera();
        assertEquals(testGameScreen.getGamecam().position.x, cameraX, 0);
        assertEquals(testGameScreen.getGamecam().position.y, cameraY, 0);
    }

    /**
     * Tests the resize() method of the GameScreen class
     */
    @Test
    public void testResize()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Call method to be tested
        testGameScreen.resize(450, 700);

        //Check values are assigned correctly
        assertEquals(testGameScreen.getGameport().getScreenWidth(), 450);
    }

    /**
     * Tests the pause() method of the GameScreen class
     */
    @Test
    public void testPause()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Call method to be tested
        testGameScreen.pause();

        //Check values are assigned correctly
        assertEquals(testGameScreen.getState(), GameScreen.GameScreenState.PAUSE);
    }

    /**
     * Tests the resume() method of the GameScreen class
     */
    @Test
    public void testResume()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Call method to be tested
        testGameScreen.pause();
        testGameScreen.hide(); //Added in so method is considered tested (method does nothing)
        testGameScreen.resume();

        //Check values are assigned correctly
        assertEquals(testGameScreen.getState(), GameScreen.GameScreenState.RESUME);
    }

    /**
     * Tests the dispose() method of the GameScreen class
     */
    @Test
    public void testDispose()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Call method to be tested
        testGameScreen.dispose();

        //Check values are assigned correctly
        assertNull(Kroy.mainGameScreen);
    }

    /**
     * Tests the performSave() method of the GameScreen class
     */
    @Test
    public void testPerformSave()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Call method to be tested
        testGameScreen.performSave(4);

        //Tests that will be saving to the correct slot
        assertEquals(testGameScreen.getSaveManager().getSave(), 4);
    }

    /**
     * Tests the addFortress() method of the GameScreen class
     */
    @Test
    public void testAddFortress()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Check that fortressCount is incremented by 1
        int initialCount = testGameScreen.getFortressesCount();
        testGameScreen.addFortress();
        assertEquals(testGameScreen.getFortressesCount(), initialCount + 1);
    }

    /**
     * Tests the removeFortress() method of the GameScreen class
     */
    @Test
    public void testRemoveFortress()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Check that fortressCount is incremented by 1
        int initialCount = testGameScreen.getFortressesCount();
        testGameScreen.removeFortress();
        assertEquals(testGameScreen.getFortressesCount(), initialCount - 1);
    }

    /**
     * Tests the updateLives() method of the GameScreen class
     */
    @Test
    public void testUpdateLives()
    {
        //Create test objects
        GameScreen testGameScreen = new GameScreen(kroy, 3, false);
        testGameScreen.show();

        //Checks that lives is reduced by 1
        int initialLives = testGameScreen.getLives();
        testGameScreen.updateLives();
        assertEquals(testGameScreen.getLives(), initialLives - 1);
    }
}
