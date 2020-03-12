package com.dicycat.kroy.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

import java.util.Random;

public class PowerUps extends Entity {


    private static int radius = 25;
    private static int health = 1;
    private static Vector2 imSize = new Vector2(16, 16);
    private PowerType type = null;



    private enum PowerType {
        Immunity, TimeStop, DoubleDamage, UnlimitedWater, Speed;

        public static PowerType getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }


    public PowerUps(Vector2 spawnPos, Texture img, Vector2 imSize, int health, int radius) {
        super(spawnPos, img, imSize, health, radius);

    }
    public PowerUps(Vector2 spawnPos, Texture img, PowerType type) {
        this(spawnPos, img, imSize, health, radius);
        this.type = type;
    }
    /**
     * Refills the trucks water; This will be delegated to its own subclass in the
     * future, currently just here to test the class in development.
     */
    @Override
    public void update() {
        if (playerInRadius()) {
            Kroy.mainGameScreen.getPlayer().refillWater();
            applyDamage(1);
        }
    }
}

