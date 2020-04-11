package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit testing for the Pipe class
 * Note that debugDraw() method has not been tested as this method just draws to the screen and so cannot be tested using JUnit
 * Created by Septagon
 */
@RunWith(GdxTestRunner.class)
public class PipeTest
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

        kroy = Mockito.mock(Kroy.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.setupEssentialValues();
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
    }

    /**
     * Tests the constructor of the Pipe class
     */
    @Test
    public void testConstructor()
    {
        //Create test object that will be used for performing tests
        Pipe testPipe = new Pipe(new Vector2(100, 100));

        //Tests that values are assigned correctly
        assertEquals(testPipe.getWidth(), 128, 0);
        assertEquals(testPipe.getHeight(), 2048, 0);
        assertEquals(testPipe.isRemove(), false);
        assertEquals(testPipe.isDisplayable(), false);
        assertEquals(testPipe.getHitboxes()[0], new Rectangle(0, 0, 128, 880));
        assertEquals(testPipe.getHitboxes()[1], new Rectangle(1158, 0, 128, 900));
    }

    /**
     * Tests the update() method of the Pipe class
     */
    @Test
    public void testUpdate()
    {
        //Create test object that will be used for performing tests
        Pipe testPipe = new Pipe(new Vector2(100, 100));
        Vector2 originalPosition = testPipe.getPosition();

        //Call method to be tested
        testPipe.update();

        //Test the values are assigned correctly
        assertEquals(testPipe.getPosition(), new Vector2(originalPosition.x - testPipe.getSpeed(), originalPosition.y));
        assertEquals(testPipe.getHitboxes()[0].x, testPipe.getX(), 0);
        assertEquals(testPipe.getHitboxes()[0].y, testPipe.getY(), 0);
        assertEquals(testPipe.getHitboxes()[1].x, testPipe.getX(), 0);
        assertEquals(testPipe.getHitboxes()[1].y, testPipe.getY() + 1158, 0);
    }

    /**
     * Tests the gameEnd() method of the Pipe class
     */
    @Test
    public void testGameEnd()
    {
        //Create test object that will be used for performing tests
        Pipe testPipe = new Pipe(new Vector2(100, 100));
        Goose testGoose = new Goose();

        //Checks that when the goose is not in bounds of the pipe, method will return false
        testGoose.getHitbox().setPosition(new Vector2(10000, 10000));
        assertEquals(testPipe.gameEnd(testGoose), false);

        //Checks that when the goose is in bounds of one of the hitboxes, method will return true
        testGoose.getHitbox().setPosition(new Vector2(testPipe.getHitboxes()[0].getX(), testPipe.getHitboxes()[1].getY()));
        assertEquals(testPipe.gameEnd(testGoose), true);
    }
}
