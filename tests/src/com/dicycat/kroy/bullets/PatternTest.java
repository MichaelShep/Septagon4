package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the Pattern class
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class PatternTest
{
    private Kroy kroy;

    /**
     * Set up environment for testing so that LibGDX methods can be used
     */
    @Before
    public void init()
    {
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        kroy = Mockito.mock(Kroy.class);
        Kroy.mainGameScreen = Mockito.mock(GameScreen.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures = Mockito.mock(GameTextures.class, Mockito.CALLS_REAL_METHODS);
        Kroy.mainGameScreen.textures.initTextures();
    }

    /**
     * Tests that the 1st Pattern class constructor correctly assigns all its variables
     */
    @Test
    public void testConstructor1()
    {
        //Create a test pattern to perform the tests on
        Pattern testPattern = new Pattern(10, 20, 30, 40, 50, 60, 70);

        //Checks all the values are being set correctly
        assertEquals(testPattern.getAim(), false);
        assertEquals(testPattern.getWaitTime(), 40, 0);
        assertEquals(testPattern.getBullets().length, 50);
        assertEquals(testPattern.getBullets()[0].length, 60);
        assertEquals(testPattern.getCooldown(),70, 0);
        assertEquals(testPattern.getOffset(), (60 - (60 % 2)) / 2);
        assertEquals(testPattern.getXtra(), (1 - (60 % 2)) * 5);
    }

    /**
     * Tests the the 2nd Pattern class constructor correctly assigns all its variables
     */
    @Test
    public void testConstructor2()
    {
        //Creates the test Pattern to perform tests on
        Pattern testPattern = new Pattern(5, 15, 25, 35, 45, 55);

        //Checks all the values are being set correctly
        assertEquals(testPattern.getAim(), true);
        assertEquals(testPattern.getWaitTime(), 25, 0);
        assertEquals(testPattern.getBullets().length, 35);
        assertEquals(testPattern.getBullets()[0].length, 45);
        assertEquals(testPattern.getCooldown(),55, 0);
        assertEquals(testPattern.getOffset(), (45 - (45 % 2)) / 2);
        assertEquals(testPattern.getXtra(), (1 - (45 % 2)) * 5);
    }

    /**
     * Tests that the 3rd constructor correctly assigns all its variables
     */
    @Test
    public void testConstructor3()
    {
        //Creates the test Pattern to perform tests on
        Pattern testPattern = new Pattern(false,2, 4, 6, 8, 10, 12, 14);

        //Checks all the values are being set correctly
        assertEquals(testPattern.getAim(), false);
        assertEquals(testPattern.getWaitTime(), 10, 0);
        assertEquals(testPattern.getBullets().length, 4 * 36);
        assertEquals(testPattern.getBullets()[0].length, 12);
        assertEquals(testPattern.getCooldown(),14, 0);
        assertEquals(testPattern.getOffset(), (12 - (12 % 2)) / 2);
        assertEquals(testPattern.getXtra(), (1 - (12 % 2)) * 5);
    }

    /**
     * Tests the Pattern method bulletSet()
     */
    @Test
    public void testBulletSet()
    {
        //Create a test pattern to perform the tests on and a test array of bullets
        Pattern testPattern = new Pattern(10, 20, 30, 40, 50, 60, 70);

        Bullet[][] bullets = new Bullet[1][1];
        Bullet testBullet = new Bullet(new Vector2(1000, 1000), new Vector2(10, 10), 5, 7.0f);
        bullets[0][0] = testBullet;
        testPattern.setBullets(bullets);

        //Tests the the bulletSet[] method returns the correct value
        assertArrayEquals(testPattern.bulletSet(0), bullets[0]);
    }

    /**
     * Tests the Pattern method aimedSet()
     */
    @Test
    public void testAimedSet()
    {
        //Create a test pattern to perform the tests on, the array of bullets and the direction they should be firing in
        Pattern testPattern = new Pattern(10, 20, 30, 40, 50, 60, 70);

        Bullet[][] bullets = new Bullet[1][1];
        Bullet testBullet = new Bullet(new Vector2(1000, 1000), new Vector2(10, 10), 5, 7.0f);
        bullets[0][0] = testBullet;
        testPattern.setBullets(bullets);

        Vector2 aimDir = new Vector2(5, 5);
        Vector2 checkDirection = new Vector2(1, 1);
        checkDirection.setAngle(aimDir.angle() + (-testPattern.getOffset() * 10) + testPattern.getXtra());

        //Call the aimedSet() method so that the results can be tested
        assertArrayEquals(testPattern.aimedSet(0, aimDir), bullets[0]);
        assertEquals(testBullet.getVelocity(), checkDirection.scl(testBullet.getSpeed()));
    }
}
