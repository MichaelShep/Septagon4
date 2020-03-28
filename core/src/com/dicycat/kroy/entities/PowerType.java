package com.dicycat.kroy.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Enum storing all the different types of power up and their associated value
 * [ID: TYPE]
 */

public enum PowerType {
    FULLHEALTH(0),
    FASTSHOOTING(1),
    RANGE(2),
    REFILLWATER(3),
    SPEED(4);

    private int value;
    private static Map map = new HashMap<>();

    private PowerType(int value)
    {
        this.value = value;
    }

    //Adds all the power up values to a map so that the type of power Up can be retrieved from an integer
    static {
        for(PowerType powerType: PowerType.values()){
            map.put(powerType.value, powerType);
        }
    }

    public static PowerType getRandomType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public static PowerType valueOf(int powerType)
    {
        return (PowerType) map.get(powerType);
    }

    public int getValue()
    {
        return value;
    }
}

