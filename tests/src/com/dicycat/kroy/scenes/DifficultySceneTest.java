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
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the DifficultyScene class
 * Note no tests in this package for the Scene class as we feel by testing its child class, we have tested the parent class sufficiently
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class DifficultySceneTest
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
     * Tests the constructor of the DifficultyScene class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        DifficultyScene testDifficultyScene = new DifficultyScene(kroy);

        //Check values are assigned correctly
        assertEquals(testDifficultyScene.getStageBatch(), kroy.batch);
        assertEquals(testDifficultyScene.getTable().getBackground(), testDifficultyScene.getBackground());
        assertTrue(testDifficultyScene.getTable().getCells().get(0).getActor() == testDifficultyScene.easyButton);
        assertTrue(testDifficultyScene.getTable().getCells().get(1).getActor() == testDifficultyScene.mediumButton);
        assertTrue(testDifficultyScene.getTable().getCells().get(2).getActor() == testDifficultyScene.hardButton);
    }
}
