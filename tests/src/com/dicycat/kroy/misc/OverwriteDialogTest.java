package com.dicycat.kroy.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
 * JUnit tests of the OverwriteDialog class
 * Note no tests for the setup method as this is tested through the constructor test
 * Have not tested the result() method as there is no variables edited and so cannot be tested using JUnit
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class OverwriteDialogTest
{
    private Kroy kroy;
    private Skin skin;

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

        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    /**
     * Tests the constructor of the OverwriteDialog Class
     */
    @Test
    public void testConstructor()
    {
        //Create test objects
        GameScreen testGameScreen = Kroy.mainGameScreen;
        OverwriteDialog testOverwriteDialog = new OverwriteDialog(skin, testGameScreen, 1);

        //Check values have been assigned correctly
        assertEquals(testOverwriteDialog.getTitleLabel().getText().toString(), "Overwrite Previous Save?");
        assertEquals(testOverwriteDialog.getGameScreen(), testGameScreen);
        assertEquals(testOverwriteDialog.getSaveNumber(), 1);
        assertEquals(testOverwriteDialog.getColor(), new Color(1.0f, 0.0f, 0.0f, 1.0f));
        assertEquals(testOverwriteDialog.getTitleLabel().getColor(), Color.BLACK);
    }
}
