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



    public static enum PowerType {
        FULLHEALTH, FASTSHOOTING, RANGE, REFILLWATER, SPEED;

        public static PowerType getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }


    public PowerUps(Vector2 spawnPos, Texture img, Vector2 imSize, int health) {
        super(spawnPos, img, imSize, health, radius);

    }
    public PowerUps(Vector2 spawnPos, Texture img, PowerType type) {
        super(spawnPos, img, imSize, health, radius);
        this.type = type;
    }
    public PowerUps(Vector2 spawnPos) {
        super(spawnPos, new Texture("thunder.png"), imSize, health, radius);
        type = PowerType.getRandomType();
        switch (type){
            case RANGE:
                setTexture(new Texture ("atom.png"));
                break;
            case SPEED:
                setTexture(new Texture("thunder.png"));
                break;
            case FULLHEALTH:
                setTexture(new Texture("health.png"));
                break;
            case REFILLWATER:
                setTexture(new Texture("raindrop.png"));
                break;
            case FASTSHOOTING:
                setTexture(new Texture("flame.png"));
                break;
        }
    }
    /**
     * Update all the power ups
     */
    @Override
    public void update(){
        if (playerInRadius()) {
            FireTruck player = Kroy.mainGameScreen.getPlayer();

            switch (type) {
                case SPEED:
                    player.SpeedUp();
                    System.out.println("Player has sped up");
                    break;
                case FULLHEALTH:
                    player.fullHealth();
                    System.out.println("Player now has full health");
                    break;
                case FASTSHOOTING:
                    player.fastShooting();
                    System.out.println("Player now has fasy shooting");
                    break;
                case RANGE:
                    player.increaseRange();
                    System.out.println("Player now has increased range");
                    break;
                case REFILLWATER:
                    player.refillWater();
                    System.out.println("Player now has refilled water");
                    break;
            }
            applyDamage(1);
        }
    }
}
