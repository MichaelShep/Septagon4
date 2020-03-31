package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.bullets.Bullet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the DebugCircle class
 * Only the constructor is tested as the only other method is a draw() method which will just display to the screen so cannot use JUnit tests for this
 * Also, the DebugDraw class does not have its own test class as it is Abstract and by testing it's child classes, we also believe we have ensured that
 * the parent class also works
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class DebugCircleTest
{
    /**
     * Sets up the environment to allow us to test using LibGDX
     */
    @Before
    public void init()
    {
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);
    }

    /**
     * Tests the constructor for the DebugCircle class
     */
    @Test
    public void testConstructor()
    {
        //Create test object that will be used to perform tests on
        DebugCircle testCircle = new DebugCircle(new Vector2(100, 100), 10.0f, 5, Color.RED);

        //Check that values are correctly assigned
        assertEquals(testCircle.getPosition(), new Vector2(100, 100));
        assertEquals(testCircle.getRadius(), 10.0f, 0);
        assertEquals(testCircle.getLineWidth(), 5);
        assertEquals(testCircle.getColor(), Color.RED);
    }
}
