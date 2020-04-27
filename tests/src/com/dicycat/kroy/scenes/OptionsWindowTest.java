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
import static org.junit.Assert.assertNotNull;

/**
 * JUnit tests for the OptionsWindow class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class OptionsWindowTest
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
     * Tests the constructor of the OptionsWindow class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        OptionsWindow testOptionsWindow = new OptionsWindow(kroy);

        //Test values are assigned correctly
        assertEquals(testOptionsWindow.getSb(), kroy.batch);
        assertEquals(testOptionsWindow.getTable().getBackground(), testOptionsWindow.getBackground());
    }

    /**
     * Tests the visibility() method of the OptionsWindow class
     */
    @Test
    public void testVisibility()
    {
        //Create test objects
        OptionsWindow testOptionsWindow = new OptionsWindow(kroy);

        //Call method to be tested
        testOptionsWindow.visibility(true);

        //Check values are assigned correctly
        assertEquals(testOptionsWindow.getTable().isVisible(), true);
    }

    /**
     * Tests the clickCheck() method of the OptionsWindow class
     */
    @Test
    public void testClickCheck()
    {
        //Create test objects
        OptionsWindow testOptionsWindow = new OptionsWindow(kroy);

        //Call method to be tested
        testOptionsWindow.clickCheck(true);

        //Test listeners added to all the buttons
        assertNotNull(testOptionsWindow.getMusic().getListeners().get(2));
        assertNotNull(testOptionsWindow.getDebug().getListeners().get(2));
        assertNotNull(testOptionsWindow.getBack().getListeners().get(2));
        assertNotNull(testOptionsWindow.getPlayMusic().getListeners().get(2));
        assertNotNull(testOptionsWindow.getStopMusic().getListeners().get(2));
        assertNotNull(testOptionsWindow.getVolumeDown().getListeners().get(2));
        assertNotNull(testOptionsWindow.getVolumeUp().getListeners().get(2));
        assertNotNull(testOptionsWindow.getBackFromMusic().getListeners().get(2));
        assertNotNull(testOptionsWindow.getShowDebug().getListeners().get(2));
        assertNotNull(testOptionsWindow.getHideDebug().getListeners().get(2));
        assertNotNull(testOptionsWindow.getBackFromDebug().getListeners().get(2));
    }

    /**
     * Tests the updateDraw() method of the OptionsWindow class
     */
    @Test
    public void testUpdateDraw()
    {
        //Create test objects
        OptionsWindow testOptionsWindow = new OptionsWindow(kroy);

        //Test table is set up correctly when state = PAGE1
        OptionsWindow.state = OptionsWindow.State.PAGE1;
        testOptionsWindow.getTable().clear();
        testOptionsWindow.updateDraw();
        assertEquals(testOptionsWindow.getTable().getCells().get(0).getActor(), testOptionsWindow.getMusic());
        assertEquals(testOptionsWindow.getTable().getCells().get(1).getActor(), testOptionsWindow.getDebug());
        assertEquals(testOptionsWindow.getTable().getCells().get(2).getActor(), testOptionsWindow.getBack());

        //Test table is set up correctly when state = MUSIC
        OptionsWindow.state = OptionsWindow.State.MUSIC;
        testOptionsWindow.getTable().clear();
        testOptionsWindow.updateDraw();
        assertEquals(testOptionsWindow.getTable().getCells().get(0).getActor(), testOptionsWindow.getPlayMusic());
        assertEquals(testOptionsWindow.getTable().getCells().get(1).getActor(), testOptionsWindow.getStopMusic());
        assertEquals(testOptionsWindow.getTable().getCells().get(2).getActor(), testOptionsWindow.getVolumeDown());
        assertEquals(testOptionsWindow.getTable().getCells().get(3).getActor(), testOptionsWindow.getVolumeUp());
        assertEquals(testOptionsWindow.getTable().getCells().get(4).getActor(), testOptionsWindow.getBackFromMusic());

        //Test table is set up correctly when state = DEBUG
        OptionsWindow.state = OptionsWindow.State.DEBUG;
        testOptionsWindow.getTable().clear();
        testOptionsWindow.updateDraw();
        assertEquals(testOptionsWindow.getTable().getCells().get(0).getActor(), testOptionsWindow.getShowDebug());
        assertEquals(testOptionsWindow.getTable().getCells().get(1).getActor(), testOptionsWindow.getHideDebug());
        assertEquals(testOptionsWindow.getTable().getCells().get(2).getActor(), testOptionsWindow.getBackFromDebug());
    }
}
