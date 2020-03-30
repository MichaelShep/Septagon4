package com.dicycat.kroy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the Bullet class
 * Added by Septagon [NOTE: COVERAGE BEFORE ADDITIONS WAS 10% of Classes, 9% of methods and 6% of lines]
 */

@PrepareForTest( Bullet.class )
@RunWith(GdxTestRunner.class)
public class BulletTest
{
    private Bullet bullet;
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
     * Tells the bullets constructor correctly assigns its values
     */
    @Test
    public void testConstructor()
    {
        Vector2 spawnPos = new Vector2(1000,1000);
        Vector2 direction = new Vector2(10, 10);

        bullet = new Bullet(spawnPos, direction, 5, 7.0f);

        //Check that the speed of the bullet is set up correctly
        assertEquals(bullet.getSpeed(), 5);

        //Check that the range of the bullet is set up correctly
        assertEquals(bullet.getMaxDist(), 7.0f, 0);

        //Checks that the hitbox of the bullet is set up correctly
        assertEquals(bullet.getHitbox(), new Circle(spawnPos.x, spawnPos.y, 10));

        //Checks that the velocity of the bullet is set up correctly
        assertEquals(bullet.getVelocity(), direction.scl(bullet.getSpeed()));
    }

    /**
     * Tests the fire() method of the Bullet class
     */
    @Test
    public void testFire()
    {
        //Creates a new bullet to be used
        Vector2 spawnPos = new Vector2(1000,1000);
        Vector2 direction = new Vector2(10, 10);
        bullet = new Bullet(spawnPos, direction, 5, 7.0f);

        //Calls the bullet.fire() method so that it can be tested
        Vector2 initial = new Vector2(50, 50);
        bullet.fire(initial);

        //Checks that the distance the bullet has to travel is set to the maxDistance
        assertEquals(bullet.getTravelDist(), bullet.getMaxDist(), 0);

        //Checks that the position of the bullet is set correctly
        assertEquals(bullet.getX(), initial.x - bullet.getOriginX(), 0);
        assertEquals(bullet.getY(), initial.y - bullet.getOriginY(), 0);

        //Checks that remove is set to false
        assertEquals(bullet.isRemove(), false);
    }

    /**
     * Tests the changeDirection() method of the Bullet class
     */
    @Test
    public void testChangeDirection()
    {
        //Creates a new bullet to be used
        Vector2 spawnPos = new Vector2(1000,1000);
        Vector2 direction = new Vector2(10, 10);
        bullet = new Bullet(spawnPos, direction, 5, 7.0f);

        //Calls the changeDirection() method so that it can be tested
        Vector2 newDirection = new Vector2(-5, -7);
        bullet.changeDirection(newDirection);

        //Checks that the velocity of the bullet is changed correctly
        assertEquals(bullet.getVelocity(), newDirection.scl(bullet.getSpeed()));
    }

    /**
     * Tests the update() method of the Bullet class
     */
    @Test
    public void testUpdate()
    {
        //Creates a new bullet to be used
        Vector2 spawnPos = new Vector2(1000,1000);
        Vector2 direction = new Vector2(10, 10);
        bullet = new Bullet(spawnPos, direction, 5, 7.0f);

        //Calls the update() method so that it can be tested
        bullet.update();

        //Checks that the hitbox of the Bullet is correctly positioned at the bullets centre
        assertEquals(bullet.getHitbox().x, bullet.getCentre().x, 0);
        assertEquals(bullet.getHitbox().y, bullet.getCentre().y, 0);
    }
}
