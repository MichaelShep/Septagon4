package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.*;
import com.dicycat.kroy.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the FireTruck class
 * Note there are no explict tests for setup(), setupWithDefaultValues() and setupDirections() as these have all been tested
 * through testing the constructors
 * Also have not tested moveInDirection() and updateDirection() as these methods rely on key presses which can't be simulated
 * in JUnit
 * isOnCollidableTile() is not tested due to the fact that this requires the tile map to be setup - testing of this can be
 * seen in TestTiledGameMap
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class FireTruckTest
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
        Kroy.mainGameScreen.textures.setupTruckAddressArray();
        Kroy.mainGameScreen.textures.initTextures();
        Kroy.mainGameScreen.textures.initFireTruckTexture(0);
    }

    /**
     * Tests the 1st constructor of the FireTruck Class
     */
    @Test
    public void testConstructor1()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {400f, 1f, 400f, 300f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 0);

        //Check that values are assigned correctly - x and y values are not checked as these are hard to test as get modified
        //so that they are positioned with the projection matrix in mind
        assertEquals(testFireTruck.getWidth(), 25, 0);
        assertEquals(testFireTruck.getHeight(), 50, 0);
        assertEquals(testFireTruck.isRemove(), false);
        assertEquals(testFireTruck.isDisplayable(), false);
        assertEquals(testFireTruck.getHealthPoints(), (int)(100 * DifficultyMultiplier.getDifficultyHealth()));
        assertEquals(testFireTruck.getMaxHealthPoints(), (int)(100 * DifficultyMultiplier.getDifficultyHealth()));
        assertEquals(testFireTruck.getRadius(), 500);

        assertEquals(testFireTruck.getSpeed(), 400f, 0);
        assertEquals(testFireTruck.getFlowRate(), 1f, 0);
        assertEquals(testFireTruck.getMaxWater(), 400f, 0);
        assertEquals(testFireTruck.getCurrentWater(), 400f, 0);
        assertEquals(testFireTruck.getRange(), 300f, 0);
        assertEquals(testFireTruck.isFiring(), false);

        //Tests one of the directions to check that they have been added properly
        assertEquals(testFireTruck.getDIRECTIONS().get("e").intValue(), 270);
    }

    /**
     * Tests the 2nd constructor of the FireTruck class
     */
    @Test
    public void testConstructor2()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {700f, 3f, 300f, 200f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 3, 625);

        //Check values have been correctly assigned
        assertEquals(testFireTruck.getWidth(), 25, 0);
        assertEquals(testFireTruck.getHeight(), 50, 0);
        assertEquals(testFireTruck.isRemove(), false);
        assertEquals(testFireTruck.isDisplayable(), false);
        assertEquals(testFireTruck.getHealthPoints(), (int)(625 * DifficultyMultiplier.getDifficultyHealth()));
        assertEquals(testFireTruck.getMaxHealthPoints(), (int)(625 * DifficultyMultiplier.getDifficultyHealth()));
        assertEquals(testFireTruck.getRadius(), 500);

        assertEquals(testFireTruck.getSpeed(), 700f, 0);
        assertEquals(testFireTruck.getFlowRate(), 3f, 0);
        assertEquals(testFireTruck.getMaxWater(), 300f, 0);
        assertEquals(testFireTruck.getCurrentWater(), 300f, 0);
        assertEquals(testFireTruck.getRange(), 200f, 0);
        assertEquals(testFireTruck.isFiring(), false);

        //Tests one of the directions to check that they have been added properly
        assertEquals(testFireTruck.getDIRECTIONS().get("nw").intValue(), 45);
    }

    /**
     * Tests the 3rd constructor of the FireTruck class
     */
    @Test
    public void testConstructor3()
    {
        //Create test variable that will be used to perform tests
        FireTruck testFireTruck = new FireTruck();

        //Check values have been correctly assigned
        assertEquals(testFireTruck.getWidth(), 25, 0);
        assertEquals(testFireTruck.getHeight(), 50, 0);
        assertEquals(testFireTruck.isRemove(), false);
        assertEquals(testFireTruck.isDisplayable(), false);
        assertEquals(testFireTruck.getHealthPoints(), 100);
        assertEquals(testFireTruck.getMaxHealthPoints(), 100);
        assertEquals(testFireTruck.getRadius(), 500);

        assertEquals(testFireTruck.getSpeed(), 300f, 0);
        assertEquals(testFireTruck.getFlowRate(), 1.5f, 0);
        assertEquals(testFireTruck.getMaxWater(), 400f, 0);
        assertEquals(testFireTruck.getCurrentWater(), 300f, 0);
        assertEquals(testFireTruck.isFiring(), false);

        //Tests one of the directions to check that they have been added properly
        assertEquals(testFireTruck.getDIRECTIONS().get("n").intValue(), 0);
    }

    /**
     * Tests the 4th constructor of the FireTruck class
     */
    @Test
    public void testConstructor4()
    {
        //Test variable that will be used for performing tests
        FireTruck testFireTruck = new FireTruck(3);

        //Check values have been correctly assigned
        assertEquals(testFireTruck.getWidth(), 25, 0);
        assertEquals(testFireTruck.getHeight(), 50, 0);
        assertEquals(testFireTruck.isRemove(), false);
        assertEquals(testFireTruck.isDisplayable(), false);
        assertEquals(testFireTruck.getHealthPoints(), 100);
        assertEquals(testFireTruck.getMaxHealthPoints(), 100);
        assertEquals(testFireTruck.getRadius(), 500);

        assertEquals(testFireTruck.getSpeed(), 300f, 0);
        assertEquals(testFireTruck.getFlowRate(), 1.5f, 0);
        assertEquals(testFireTruck.getMaxWater(), 400f, 0);
        assertEquals(testFireTruck.getCurrentWater(), 300f, 0);
        assertEquals(testFireTruck.isFiring(), false);

        //Tests one of the directions to check that they have been added properly
        assertEquals(testFireTruck.getDIRECTIONS().get("sw").intValue(), 135);
    }

    /**
     * Tests the update() method of the FireTruck class
     */
    @Test
    public void testUpdate()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {400f, 1f, 400f, 300f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 0);
        Kroy.mainGameScreen.setPlayer(testFireTruck);
        testFireTruck.setMaxHealthPoints(100);
        testFireTruck.setHealthPoints(100);

        //Call method that is being tested
        testFireTruck.update();

        //Check that values are assigned correctly
        assertEquals(testFireTruck.getHitbox().x + testFireTruck.getHitbox().width / 2, testFireTruck.getCentre().x, 0);
        assertEquals(testFireTruck.getHitbox().y + testFireTruck.getHitbox().height / 2, testFireTruck.getCentre().y, 0);
        assertEquals(testFireTruck.getTank().getX(), testFireTruck.getCentre().x, 0);
        assertEquals(testFireTruck.getTank().getY(), testFireTruck.getCentre().y + 20, 0);
        assertEquals(testFireTruck.getHealthBar().getX(), testFireTruck.getCentre().x, 0);
        assertEquals(testFireTruck.getHealthBar().getY(), testFireTruck.getCentre().y + 25, 0);

        //Checks when no entities are in range values are assigned correctly
        assertEquals(testFireTruck.isFiring(), false);
        assertEquals(testFireTruck.getWater().isRemove(), true);

        //Adds an entity into the range of the truck
        Fortress fortress = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getBullet(), Kroy.mainGameScreen.textures.getBullet(), new Vector2(10, 10));
        fortress.setPosition(testFireTruck.getPosition());
        Kroy.mainGameScreen.getGameObjects().add(fortress);

        //Checks that when entity in range, firing is true
        testFireTruck.update();
        assertEquals(testFireTruck.isFiring(), true);
    }

    /**
     * Tests the playerFire() method of the FireTruck class
     */
    @Test
    public void testPlayerFire()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {400f, 1f, 400f, 300f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 0);
        ArrayList<GameObject> testGameObjects = new ArrayList<GameObject>();
        GameObject testGameObject = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getBullet(), Kroy.mainGameScreen.textures.getBullet(), new Vector2(10, 10));
        GameObject testGameObject2 = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getBullet(), Kroy.mainGameScreen.textures.getBullet(), new Vector2(10, 10));
        testGameObjects.add(testGameObject);
        testGameObjects.add(testGameObject2);
        Vector2 testDirection = new Vector2(testGameObject.getCentre().x,testGameObject.getCentre().y).sub(testFireTruck.getCentre());

        //Call method that is being tested
        testFireTruck.playerFire(testGameObjects);

        //Check that values are assigned correctly
        assertEquals(testFireTruck.getWater().getRotation(), testDirection.angle(), 0);
        assertEquals(testFireTruck.getWater().getXScale(), testDirection.len(), 0);
        assertEquals(testFireTruck.getWater().getPosition(), testFireTruck.getCentre().add(testDirection.scl(0.5f)));
        assertEquals(testFireTruck.getCurrentWater(), testFireTruck.getMaxWater() - testFireTruck.getFlowRate(), 0);
    }

    /**
     * Tests the entitiesInRange() method of the FireTruck class
     */
    @Test
    public void testEntitiesInRange()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {400f, 1f, 400f, 300f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 0);

        //When no entities in range, should return an empty ArrayList
        assertEquals(testFireTruck.entitiesInRange(), new ArrayList<Entity>());

        //Adds a gameObject into the range of the FireTruck
        Entity testGameObject = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getBullet(), Kroy.mainGameScreen.textures.getBullet(), new Vector2(10, 10));
        testGameObject.setPosition(testFireTruck.getPosition());
        Kroy.mainGameScreen.getGameObjects().add(testGameObject);

        //When there are entities in range, should return an array containing those entities
        ArrayList<Entity> testOutputArray = new ArrayList<Entity>();
        testOutputArray.add(testGameObject);
        assertEquals(testFireTruck.entitiesInRange(), testOutputArray);
    }

    /**
     * Tests the objectInRange() method of the FireTruck class
     */
    @Test
    public void testObjectInRange()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {400f, 1f, 400f, 300f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 0);
        GameObject testGameObject = new Fortress(new Vector2(100, 100), Kroy.mainGameScreen.textures.getBullet(), Kroy.mainGameScreen.textures.getBullet(), new Vector2(10, 10));
        testGameObject.setPosition(new Vector2(testFireTruck.getPosition().x + testFireTruck.getRange() + 5, testFireTruck.getPosition().y + testFireTruck.getRange() + 5));

        //When object is not in range, should return false
        assertEquals(testFireTruck.objectInRange(testGameObject), false);

        //Move object into range
        testGameObject.setPosition(testFireTruck.getPosition());

        //When object is in range, should return true
        assertEquals(testFireTruck.objectInRange(testGameObject), true);
    }

    /**
     * Tests the die() method of the FireTruck class
     */
    @Test
    public void testDie()
    {
        //Create test variables that will be used for performing tests
        Float[] testTruckStats = {400f, 1f, 400f, 300f};
        FireTruck testFireTruck = new FireTruck(new Vector2(100, 100), testTruckStats, 0);

        //Call method that is being tested
        testFireTruck.die();

        //Check values are assigned correctly
        assertEquals(testFireTruck.isRemove(), true);
        assertEquals(testFireTruck.isDead(), true);
        assertEquals(testFireTruck.getWater().isRemove(), true);
        assertEquals(testFireTruck.getTank().isRemove(), true);
        assertEquals(testFireTruck.getHealthBar().isRemove(), true);
    }

    /**
     * Tests the replenish() method of the FireTruck class
     */
    @Test
    public void testReplenish()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        testFireTruck.setHealthToFixedValue(0);
        testFireTruck.setCurrentWaterToFixedValue(0);

        //Calls method to be tested
        testFireTruck.replenish();

        //Checks values are assigned correctly
        assertEquals(testFireTruck.getHealthPoints(), 2);
        assertEquals(testFireTruck.getCurrentWater(), 2, 0);

        //Checks that when water and health are at their max values, they do not get increased
        testFireTruck.setCurrentWaterToFixedValue(testFireTruck.getMaxWater());
        testFireTruck.setHealthToFixedValue(testFireTruck.getMaxHealthPoints());

        testFireTruck.replenish();

        assertEquals(testFireTruck.getCurrentWater(), testFireTruck.getMaxWater(), 0);
        assertEquals(testFireTruck.getHealthPoints(), testFireTruck.getMaxHealthPoints());
    }

    /**
     * Tests the refillWater() method of the FireTruck class
     */
    @Test
    public void testRefillWater()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        testFireTruck.setHealthToFixedValue(0);
        testFireTruck.setCurrentWaterToFixedValue(0);

        //Calls method to be tested
        testFireTruck.refillWater();

        //Checks values are assigned correctly
        assertEquals(testFireTruck.getCurrentWater(), testFireTruck.getMaxWater(), 0);
    }

    /**
     * Tests the fullHealth() method of the FireTruck class
     */
    @Test
    public void testFullHealth()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        testFireTruck.setHealthToFixedValue(0);
        testFireTruck.setCurrentWaterToFixedValue(0);

        //Calls method to be tested
        testFireTruck.fullHealth();

        //Checks values are assigned correctly
        assertEquals(testFireTruck.getHealthPoints(), testFireTruck.getMaxHealthPoints());
    }

    /**
     * Tests the fastShooting() method of the FireTruck class
     */
    @Test
    public void testFastShooting()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        float initialFlowRate = testFireTruck.getFlowRate();

        //Calls method to be tested
        testFireTruck.fastShooting();

        //Checks values are assigned correctly
        assertEquals(testFireTruck.getFlowRate(), initialFlowRate + FireTruck.SHOOTING_INCREASE, 0);
    }

    /**
     * Tests the speedUp() method of the FireTruck class
     */
    @Test
    public void testSpeedUp()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        float initialSpeed = testFireTruck.getSpeed();

        //Call method to be tested
        testFireTruck.SpeedUp();

        //Check values are assigned correctly
        assertEquals(testFireTruck.getSpeed(), initialSpeed + FireTruck.SPEED_INCREASE, 0);
    }

    /**
     * Tests the increaseRange() method of the FireTruck class
     */
    @Test
    public void testIncreaseRange()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        float initialRange = testFireTruck.getRange();

        //Call method to be tested
        testFireTruck.increaseRange();

        //Check values are assigned correctly
        assertEquals(testFireTruck.getRange(), initialRange + FireTruck.RANGE_INCREASE, 0);
    }

    /**
     * Tests the resetShooting() method of the FireTruck class
     */
    @Test
    public void testResetShooting()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        float initialShooting = testFireTruck.getFlowRate();

        //Call method to be tested
        testFireTruck.resetShooting();

        //Check values are assigned correctly
        assertEquals(testFireTruck.getFlowRate(), initialShooting - FireTruck.SHOOTING_INCREASE, 0);
    }

    /**
     * Tests the resetSpeedUp() method of the FireTruck class
     */
    @Test
    public void testResetSpeedUp()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        float initialSpeed = testFireTruck.getSpeed();

        //Call method to be tested
        testFireTruck.resetSpeedUp();

        //Check values are assigned correctly
        assertEquals(testFireTruck.getSpeed(), initialSpeed - FireTruck.SPEED_INCREASE, 0);
    }

    /**
     * Tests the resetRange() method of the FireTruck class
     */
    @Test
    public void testResetRange()
    {
        //Create test variables that will be used for performing tests
        FireTruck testFireTruck = new FireTruck();
        float initialRange = testFireTruck.getRange();

        //Call method to be tested
        testFireTruck.resetRange();

        //Check values are assigned correctly
        assertEquals(testFireTruck.getRange(), initialRange - FireTruck.RANGE_INCREASE, 0);
    }
}
