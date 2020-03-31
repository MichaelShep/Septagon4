package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.dicycat.kroy.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the DebugRenderer Class
 * Only the constructor is tested as the other methods will just display to the screen so cannot use JUnit tests for this
 * Added by Septagon
 */

@RunWith(GdxTestRunner.class)
public class DebugRendererTest
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
     * Tests the constructor for the DebugRenderer class
     */
    @Test
    public void testConstructor()
    {
        //Create test object that tests will be applied to
        DebugRenderer testDebugRenderer = new DebugRenderer();

        //Test that values are correctly assigned
        assertEquals(testDebugRenderer.getDebugObjects(), new ArrayList<DebugDraw>());
    }
}
