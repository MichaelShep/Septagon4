package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Testing for the DebugLine Class
 * Only the constructor is tested as the only other method is a draw() method which will just display to the screen so cannot use JUnit tests for this
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class DebugLineTest
{
    /**
     * Sets up the environment for allowing us to test with LibGDX
     */
    @Before
    public void init()
    {
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);
    }

    /**
     * Tests the constructor for the DebugLine class
     */
    @Test
    public void testConstructor()
    {
        //Create test object that will be used to perform tests on
        DebugLine testDebugLine = new DebugLine(new Vector2(100, 100), new Vector2(200, 200), 10, Color.BLACK);

        //Check that values are correctly assigned
        assertEquals(testDebugLine.getStart(), new Vector2(100, 100));
        assertEquals(testDebugLine.getEnd(), new Vector2(200, 200));
        assertEquals(testDebugLine.getLineWidth(), 10);
        assertEquals(testDebugLine.getColor(), Color.BLACK);
    }
}
