package com.dicycat.kroy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.entities.Entity;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Goose;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class used for testing the BulletDispenser class
 * Added by Septagon
 */

@PrepareForTest( BulletDispenser.class )
@RunWith(GdxTestRunner.class)
public class BulletDispenserTest
{
    private BulletDispenser bulletDispenser;
    private Entity testEntity;
    private Kroy kroy;

    /**
     * Performs the necessary setup before the tests are run so that they can correctly access all the variables they need
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

        testEntity = new Goose();
    }

    /**
     * Tests the constructor of the BulletDispenser class
     */
    @Test
    public void testConstructor()
    {
        //Create a new instance of BulletDispenser so the constructor can be tested
        bulletDispenser = new BulletDispenser(testEntity);

        //Check that the initial values for the BulletDispenser instance as correctly assigned
        assertEquals(bulletDispenser.getOwner(), testEntity);
        assertEquals(bulletDispenser.getPatterns(), new ArrayList<Pattern>());
        assertEquals(bulletDispenser.getCurrentPattern(), 0);
        assertEquals(bulletDispenser.getBulletTimer(), 0, 0);
        assertEquals(bulletDispenser.getPatternTimer(), 0, 0);
    }

    /**
     * Tests the addPattern() method of the BulletDispenser class
     */
    @Test
    public void testAddPattern()
    {
        //Create a new instance of BulletDispenser
        bulletDispenser = new BulletDispenser(testEntity);

        //Create 2 new patterns that will be used for testing
        Pattern testPattern1 = new Pattern(0, 0, 0, 0, 0, 0);
        Pattern testPattern2 = new Pattern(0, 0, 0, 0, 0, 0);

        //Test that adding the first pattern to the array works correctly
        bulletDispenser.addPattern(testPattern1);
        assertEquals(bulletDispenser.getPatterns().get(0), testPattern1);
        assertEquals(bulletDispenser.getFiringPattern(), testPattern1);

        //Test that adding the second pattern to the array works correctly
        bulletDispenser.addPattern(testPattern2);
        assertEquals(bulletDispenser.getPatterns().get(1), testPattern2);
        assertNotEquals(bulletDispenser.getFiringPattern(), testPattern2);
    }

    /**
     * Tests the update() method of the BulletDispenser class
     */
    @Test
    public void testUpdate()
    {
        //Create a new instance of BulletDispenser
        bulletDispenser = new BulletDispenser(testEntity);

        //Create pattern that will be used for testing
        Pattern testPattern1 = new Pattern(0, 0, 0, 10, 2, 1, 10);


        //Test that when the list of patterns is empty, method will just return null
        assertNull(bulletDispenser.update(true));

        bulletDispenser.addPattern(testPattern1);

        //Tests for when fire = false
        float initialBulletTimer = bulletDispenser.getBulletTimer();
        float initialPatternTimer = bulletDispenser.getPatternTimer();
        Bullet[] bullets = bulletDispenser.update(false);
        //Check that the method returns null
        assertNull(bullets);
        //Checks that the timers have been incremented
        assertTrue(initialBulletTimer < bulletDispenser.getBulletTimer());
        assertTrue(initialPatternTimer < bulletDispenser.getPatternTimer());

        //Tests for when fire = true

        //Tests that if patternTimer < patternTime, method will return null
        assertNull(bulletDispenser.update(true));
        bulletDispenser.setPatternTimer(testPattern1.getCooldown());

        //Test that if bulletTimer < testPattern1.waitTime, method will return null
        assertNull(bulletDispenser.update(true));
        bulletDispenser.setBulletTimer(testPattern1.getWaitTime());

        //Test when currentBullet < testPattern1.getBullets().length, the first set of bullets is returned
        assertArrayEquals(bulletDispenser.update(true), testPattern1.getBullets()[0]);

        //Needs to reset currentBullet value before running update again
        bulletDispenser.setBulletTimer(testPattern1.getWaitTime());

        //Test when currentBullet >= testPattern1.getBullets().length, all values are assigned correctly
        assertArrayEquals(bulletDispenser.update(true), testPattern1.getBullets()[1]);
        assertEquals(bulletDispenser.getCurrentPattern(), 0);
        assertEquals(bulletDispenser.getCurrentBullet(), 0);
        assertEquals(bulletDispenser.getPatternTime(), testPattern1.getCooldown(), 0);
        assertEquals(bulletDispenser.getPatternTimer(), 0, 0);
        assertEquals(bulletDispenser.getFiringPattern(), testPattern1);
    }

}
