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

@RunWith(GdxTestRunner.class)
public class SaveGameSceneTest
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
     * Tests the constructor of the SaveGameScene class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        SaveGameScene testSaveGameScene = new SaveGameScene(kroy);

        //Check values are assigned correctly
        assertEquals(testSaveGameScene.getStageBatch(), kroy.batch);
        assertEquals(testSaveGameScene.getTable().getBackground(), testSaveGameScene.getBackground());
        assertEquals(testSaveGameScene.getTable().getCells().get(0).getActor(), testSaveGameScene.saveGame1Button);
        assertEquals(testSaveGameScene.getTable().getCells().get(1).getActor(), testSaveGameScene.saveGame2Button);
        assertEquals(testSaveGameScene.getTable().getCells().get(2).getActor(), testSaveGameScene.saveGame3Button);
        assertEquals(testSaveGameScene.getTable().getCells().get(3).getActor(), testSaveGameScene.saveGame4Button);
        assertEquals(testSaveGameScene.getTable().getCells().get(4).getActor(), testSaveGameScene.saveGame5Button);
        assertEquals(testSaveGameScene.getTable().getCells().get(5).getActor(), testSaveGameScene.backButton);

    }
}
