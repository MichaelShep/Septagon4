package com.dicycat.kroy;

import com.badlogic.gdx.utils.Array;

import java.util.List;

public class DifficultyMultiplier {

    private static int difficultyValue;

    private static float difficultySpeed;
    private static float[] difficultySpeedList =  {0.5f, 1f, 2.5f};
    private static float difficultyBullet;
    private static float[] difficultyBulletList = {0.7f, 1f, 1.3f};
    private static float difficultyHealth;
    private static float[] difficultyHealthList =  {1.5f, 1f, 0.75f};

    public static float getDifficultySpeed(){
        return difficultySpeed;
    }

    public static float getDifficultyBullet(){
        return difficultyBullet;
    }

    public static float getDifficultyHealth(){
        return difficultyHealth;
    }

    public static void setDifficulty(int d){
        difficultyValue = d;
        difficultySpeed = difficultySpeedList[d];
        difficultyBullet = difficultyBulletList[d];
        difficultyHealth = difficultyHealthList[d];
    }

    public static int getDifficultyValue() { return difficultyValue; }
}
