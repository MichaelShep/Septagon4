package com.dicycat.kroy.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PowerUps extends Entity {


    private static Vector2 Size = 25,25;
    private static int radius = 36;
    private static int health = 1;
    private Type type = null;



    private enum Type {
        Immunity, Timestop, DoubleDamage, UnlimitedWater, Speed;

        public static Type getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }


    public PowerUp(Vector2 spawnPos, Texture img, Vector2 imSize, int health, int radius) {
        super(spawnPos, img, imSize, health, radius);

    }
    public PowerUp(Vector2 spawnPos, Texture img, Type type) {
        this(spawnPos, img, Size, health, radius);
        this.type = type;
    }
}

