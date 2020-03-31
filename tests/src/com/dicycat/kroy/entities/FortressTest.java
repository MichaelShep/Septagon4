package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Testing for the Fortress Class
 * Note that their is no explict test for setup() as this is tested through testing the constructors
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class FortressTest
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
     * Test the 1st constructor of the Fortress class
     */
    @Test
    public void testConstructor1()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10));

        //Check that values are correctly assigned
        assertEquals(testFortress.getTexture(), Kroy.mainGameScreen.textures.getFortress(0));
        assertEquals(testFortress.getHealthPoints(), 500);
        assertEquals(testFortress.getMaxHealthPoints(), 500);
        assertEquals(testFortress.getRadius(), 500);
        assertEquals(testFortress.isRemove(), false);
        assertEquals(testFortress.isDisplayable(), false);
        assertEquals(testFortress.getWidth(), 10, 0);
        assertEquals(testFortress.getHeight(), 10, 0);
        assertEquals(testFortress.getDeadTexture(), Kroy.mainGameScreen.textures.getDeadFortress(0));
    }

    /**
     * Tests the 2nd constructor of the Fortress class
     */
    @Test
    public void testConstructor2()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10), 1000);

        //Check that values are correctly assigned
        assertEquals(testFortress.getTexture(), Kroy.mainGameScreen.textures.getFortress(0));
        assertEquals(testFortress.getHealthPoints(), 1000);
        assertEquals(testFortress.getMaxHealthPoints(), 1000);
        assertEquals(testFortress.getRadius(), 500);
        assertEquals(testFortress.isRemove(), false);
        assertEquals(testFortress.isDisplayable(), false);
        assertEquals(testFortress.getWidth(), 10, 0);
        assertEquals(testFortress.getHeight(), 10, 0);
        assertEquals(testFortress.getDeadTexture(), Kroy.mainGameScreen.textures.getDeadFortress(0));
    }

    /**
     * Tests the 3rd constructor of the Fortress class
     */
    @Test
    public void testConstructor3()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress();

        //Checks that values are assigned correctly
        assertEquals(testFortress.getTexture(), Kroy.mainGameScreen.textures.getFortress(0));
        assertEquals(testFortress.getHealthPoints(), 500);
        assertEquals(testFortress.getMaxHealthPoints(), 500);
        assertEquals(testFortress.getRadius(), 500);
        assertEquals(testFortress.isRemove(), false);
        assertEquals(testFortress.isDisplayable(), false);
        assertEquals(testFortress.getWidth(), 256, 0);
        assertEquals(testFortress.getHeight(), 218, 0);
        assertEquals(testFortress.getDeadTexture(), Kroy.mainGameScreen.textures.getDeadFortress(0));
    }

    /**
     * Tests the die() method of the Fortress class
     */
    @Test
    public void testDie()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10));

        //Call method that is being tested
        testFortress.die();

        //Check values are assigned correctly
        assertEquals(testFortress.isRemove(), true);
        assertEquals(testFortress.isDead(), true);
        assertEquals(testFortress.getTexture(), Kroy.mainGameScreen.textures.getDeadFortress(0));
        assertEquals(testFortress.getHealthBar().isRemove(), true);
        assertEquals(testFortress.isDisplayable(), true);
    }

    /**
     * Tests the death() method of the Fortress class
     */
    @Test
    public void testDeath()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10));

        //Call method that is being tested
        testFortress.death();

        //Check values are assigned correctly
        assertEquals(testFortress.isRemove(), true);
        assertEquals(testFortress.isDead(), true);
        assertEquals(testFortress.getTexture(), Kroy.mainGameScreen.textures.getDeadFortress(0));
        assertEquals(testFortress.isDisplayable(), true);
    }

    /**
     * Tests the applyDamage() method of the Fortress class
     */
    @Test
    public void testApplyDamage()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10));
        float damageToApply = 10.0f;
        int initialHealthPoints = testFortress.getHealthPoints();

        //Call method that is being tested
        testFortress.applyDamage(damageToApply);

        //Check values are assigned correctly
        assertEquals(testFortress.getHealthPoints(), initialHealthPoints - damageToApply, 0);
        assertEquals(testFortress.getHealthBar().getPosition().x, testFortress.getCentre().x, 0);
        assertEquals(testFortress.getHealthBar().getPosition().y, testFortress.getCentre().y + (testFortress.getHeight() / 2) + 25, 0);
    }

    /**
     * Tests the Damage() method in the Fortress class
     */
    @Test
    public void testDamage()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10));
        float damageToApply = 10.0f;
        int initialHealthPoints = testFortress.getHealthPoints();

        //Call method that is being tested
        testFortress.Damage(damageToApply);

        //Check values are assigned correctly
        assertEquals(testFortress.getHealthPoints(), initialHealthPoints - damageToApply, 0);
    }

    /**
     * Tests the update() method of the Fortress class
     */
    @Test
    public void testUpdate()
    {
        //Create test variable that will be used for performing tests
        Fortress testFortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getFortress(0), Kroy.mainGameScreen.textures.getDeadFortress(0), new Vector2(10, 10));
        FireTruck testFireTuck = new FireTruck();
        testFireTuck.setPosition(testFortress.getPosition());
        Kroy.mainGameScreen.setPlayer(testFireTuck);

        testFortress.getDispenser().setPatternTimer(testFortress.getDispenser().getFiringPattern().getCooldown());
        testFortress.getDispenser().setBulletTimer(testFortress.getDispenser().getFiringPattern().getWaitTime());

        //Calls method to be tested
        testFortress.update();

        //Checks values are assigned correctly
        assertEquals(testFortress.getDispenser().getFiringPattern().bulletSet(0)[0].getTravelDist(), testFortress.getDispenser().getFiringPattern().bulletSet(0)[0].getMaxDist(), 0);
        assertEquals(testFortress.getDispenser().getFiringPattern().bulletSet(0)[0].isRemove(), false);
    }
}
