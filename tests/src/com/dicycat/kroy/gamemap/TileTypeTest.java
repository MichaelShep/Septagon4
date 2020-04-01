package com.dicycat.kroy.gamemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
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
 * JUnit tests for the TileType enum class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class TileTypeTest
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
    }

    /**
     * Test that the HashMap for the TileType class is set up correctly
     */
    @Test
    public void testMap()
    {
        //Test that the keys in the map refer to the correct TileTypes
        assertEquals(TileType.getTileMap().get(422), TileType.NONCOLLIDABLE);
        assertEquals(TileType.getTileMap().get(421), TileType.COLLIDABLE);
    }

    /**
     * Tests the getID() method of the TileType class
     */
    @Test
    public void testGetID()
    {
        //Tests that the getId() returns the relevant ID for each TileType
        assertEquals(TileType.NONCOLLIDABLE.getId(), 422);
        assertEquals(TileType.COLLIDABLE.getId(), 421);
    }

    /**
     * Tests the getTileTypeByID() method of the TileType class
     */
    @Test
    public void testGetTileTypeByID()
    {
        //Tests the that getTileTypeByID() returns the relevant TileType for each ID
        assertEquals(TileType.getTileTypeByID(422), TileType.NONCOLLIDABLE);
        assertEquals(TileType.getTileTypeByID(421), TileType.COLLIDABLE);
    }
}
