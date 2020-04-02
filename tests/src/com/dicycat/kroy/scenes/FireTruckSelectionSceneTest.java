package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
 * JUnit tests for the FireTruckSelectionScene class
 */

@RunWith(GdxTestRunner.class)
public class FireTruckSelectionSceneTest
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
     * Tests the constructor of the FireTruckSelectionScene class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        FireTruckSelectionScene testFireTruckSelectionScene = new FireTruckSelectionScene(kroy);

        //Test values are assigned correctly
        assertEquals(testFireTruckSelectionScene.getStageBatch(), kroy.batch);
        assertEquals(testFireTruckSelectionScene.getTable().getBackground(), testFireTruckSelectionScene.getBackground());
        assertEquals(testFireTruckSelectionScene.getTable().getCells().get(3).getActor(), testFireTruckSelectionScene.truckButton1);
        assertEquals(testFireTruckSelectionScene.getTable().getCells().get(4).getActor(), testFireTruckSelectionScene.truckButton2);
        assertEquals(testFireTruckSelectionScene.getTable().getCells().get(5).getActor(), testFireTruckSelectionScene.truckButton3);
        assertEquals(testFireTruckSelectionScene.getTable().getCells().get(9).getActor(), testFireTruckSelectionScene.truckButton4);
        assertEquals(testFireTruckSelectionScene.getTable().getCells().get(10).getActor(), testFireTruckSelectionScene.truckButton5);
        assertEquals(testFireTruckSelectionScene.getTable().getCells().get(11).getActor(), testFireTruckSelectionScene.truckButton6);
    }
}
