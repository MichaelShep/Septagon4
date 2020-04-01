package com.dicycat.kroy.entities;

import com.dicycat.kroy.GdxTestRunner;
import javafx.stage.PopupWindow;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the PowerTypeTest class
 * Created by Septagon
 */

@RunWith(GdxTestRunner.class)
public class PowerTypeTest
{
    /**
     * Tests that the map in the PowerType class gets assigned all the correct values and all the values correspond with each other
     */
    @Test
    public void testMap()
    {
        assertEquals(PowerType.getMap().get(0), PowerType.FULLHEALTH);
        assertEquals(PowerType.getMap().get(1), PowerType.FASTSHOOTING);
        assertEquals(PowerType.getMap().get(2), PowerType.RANGE);
        assertEquals(PowerType.getMap().get(3), PowerType.REFILLWATER);
        assertEquals(PowerType.getMap().get(4), PowerType.SPEED);
    }

    /**
     * Tests the getRandomType() method from the PowerType class
     */
    @Test
    public void testGetRandomType()
    {
        //Calls the method to be tested
        PowerType testPowerType = PowerType.getRandomType();

        //Test that the method assigns a random value of one of the PowerUps
        assertTrue(testPowerType == (PowerType.FULLHEALTH) || testPowerType == (PowerType.FASTSHOOTING) || testPowerType == (PowerType.RANGE) ||
                testPowerType == (PowerType.REFILLWATER) || testPowerType == (PowerType.SPEED));
    }

    /**
     * Tests the valueOf() method from the PowerType class
     */
    @Test
    public void testValueOf()
    {
        //Checks that the correct PowerType is returned for each corresponding value
        assertEquals(PowerType.valueOf(0), PowerType.FULLHEALTH);
        assertEquals(PowerType.valueOf(1), PowerType.FASTSHOOTING);
        assertEquals(PowerType.valueOf(2), PowerType.RANGE);
        assertEquals(PowerType.valueOf(3), PowerType.REFILLWATER);
        assertEquals(PowerType.valueOf(4), PowerType.SPEED);
    }

    /**
     * Tests the getValue() method from the PowerType class
     */
    @Test
    public void testGetValue()
    {
        //Checks that the correct value is assigned to each PowerType
        assertEquals(PowerType.FULLHEALTH.getValue(), 0);
        assertEquals(PowerType.FASTSHOOTING.getValue(), 1);
        assertEquals(PowerType.RANGE.getValue(), 2);
        assertEquals(PowerType.REFILLWATER.getValue(), 3);
        assertEquals(PowerType.SPEED.getValue(), 4);
    }
}
