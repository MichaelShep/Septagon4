package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.HUD;

import java.util.Random;

public class PowerUps extends Entity {

    private static int radius = 25;
    private static int health = 1;
    private static Vector2 imSize = new Vector2(16, 16);
    private PowerType type = null;
    private HUD hud;
    private int duration = 30;

    public static enum PowerType {
        FULLHEALTH, FASTSHOOTING, RANGE, REFILLWATER, SPEED;

        public static PowerType getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }


    public PowerUps(Vector2 spawnPos, Texture img, Vector2 imSize, int health, HUD hud) {
        super(spawnPos, img, imSize, health, radius);
        this.hud = hud;

    }
    public PowerUps(Vector2 spawnPos, Texture img, PowerType type, HUD hud) {
        super(spawnPos, img, imSize, health, radius);
        this.type = type;
        this.hud = hud;
    }
    public PowerUps(Vector2 spawnPos, HUD hud) {
        super(spawnPos, new Texture("thunder.png"), imSize, health, radius);
        this.hud = hud;
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
                    hud.removePowerUpMessage();
                    hud.addPowerUpMessage(this);
                    System.out.println("Player has sped up");
                    break;
                case FULLHEALTH:
                    player.fullHealth();
                    hud.removePowerUpMessage();
                    hud.addPowerUpMessage(this);
                    break;
                case FASTSHOOTING:
                    player.fastShooting();
                    hud.removePowerUpMessage();
                    hud.addPowerUpMessage(this);
                    break;
                case RANGE:
                    player.increaseRange();
                    hud.removePowerUpMessage();
                    hud.addPowerUpMessage(this);
                    break;
                case REFILLWATER:
                    player.refillWater();
                    hud.removePowerUpMessage();
                    hud.addPowerUpMessage(this);
                    break;
            }
            applyDamage(1);
        }
    }

    /**
     * Removes the powerUp from the truck and resets its stats
     */
    public void remove()
    {
        FireTruck player = Kroy.mainGameScreen.getPlayer();

        switch(type)
        {
            case SPEED:
                player.resetSpeedUp();
                break;
            case FASTSHOOTING:
                player.resetShooting();
                break;
            case RANGE:
                player.resetRange();
                break;
            default:
                break;
        }
    }

    /***
     * Converts the powerUp to a string so it can be output in a message
     * @return
     */
    public String toString()
    {
        switch(type)
        {
            case SPEED:
                return "Speed Up";
            case FULLHEALTH:
                return "Full Health";
            case FASTSHOOTING:
                return "Fast Shooting";
            case RANGE:
                return "Increased Range";
            case REFILLWATER:
                return "Refilled Water";
            default:
                return "Default";
        }
    }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }
}
