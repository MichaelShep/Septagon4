package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;

/**
 * JUnit tests for the Goose class
 * Note no testing for the DebugDraw method as this just performs rendering so cannot be tested using JUnit
 * Added by Septagon
 */
@RunWith(GdxTestRunner.class)
public class GooseTest
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
     * Tests the 1st constructor of the Goose class
     */
    @Test
    public void testConstructor1()
    {
        //Create test object that will be used for performing tests
        Texture gooseTexture = new Texture("goose2.png");
        Goose testGoose = new Goose(new Vector2(100, 100), gooseTexture, new Vector2(25, 30), 125);

        //Test values are assigned correctly
        assertEquals(testGoose.getWidth(), 25.0f, 0);
        assertEquals(testGoose.getHeight(), 30.0f, 0);
        assertEquals(testGoose.isRemove(), false);
        assertEquals(testGoose.isDisplayable(), false);
        assertEquals(testGoose.getHealthPoints(), 125);
        assertEquals(testGoose.getMaxHealthPoints(), 125);
        assertEquals(testGoose.getRadius(), 500);
        assertEquals(testGoose.getTexture(), gooseTexture);
    }

    /**
     * Tests the 2nd constructor of the Goose class
     */
    @Test
    public void testConstructor2()
    {
        //Create test object that will be used for performing tests
        Goose testGoose =  new Goose();

        //Test values are assigned correctly - Texture not checked here as texture objects appear different even if they load the same texture
        assertEquals(testGoose.getWidth(), 64.0f * testGoose.getScale(), 0);
        assertEquals(testGoose.getHeight(), 64.0f * testGoose.getScale(), 0);
        assertEquals(testGoose.isRemove(), false);
        assertEquals(testGoose.isDisplayable(), false);
        assertEquals(testGoose.getHealthPoints(), 1);
        assertEquals(testGoose.getMaxHealthPoints(), 1);
        assertEquals(testGoose.getRadius(), 500);
    }

    /**
     * Tests the update() method of the Goose class
     */
    @Test
    public void testUpdate()
    {
        //Creates test object that will be used for performing tests
        Goose testGoose = new Goose();

        //Test that when testGoose.y < -400, the velocity is set to 0
        testGoose.setPosition(new Vector2(testGoose.getX(), -500));
        testGoose.update();
        assertEquals(testGoose.getVelocity(), 0.0, 0);
        testGoose.setPosition(new Vector2(testGoose.getX(), 0));

        //Test that when velocity > -5f, velocity is reduced by the deceleration amount
        float originalX = testGoose.getX();
        float originalY = testGoose.getY();
        testGoose.setVelocity(0);
        testGoose.update();
        assertEquals(testGoose.getVelocity(), -testGoose.getDeceleration());

        //Checks that the y position of the goose is increased by the velocity
        assertEquals(testGoose.getPosition(), new Vector2(originalX, originalY + testGoose.getVelocity()));

        //Checks that the centre of the hitbox is at the centre of the Goose
        assertEquals(new Vector2(testGoose.getHitbox().x + testGoose.getHitbox().width / 2, testGoose.getHitbox().y + testGoose.getHitbox().height / 2), testGoose.getCentre());
    }
}
