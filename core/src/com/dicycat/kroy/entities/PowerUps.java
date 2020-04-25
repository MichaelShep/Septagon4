package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.scenes.HUD;

/**
 * Class to represent all the PowerUps in the game
 * [ID: POWERUP]
 *
 * NEED TO MAKE SO THAT POWERUPS CAN STACK - ATM THEY CAN'T
 */

public class PowerUps extends Entity {

    private static int radius = 25;
    private static int health = 1;
    private static Vector2 imSize = new Vector2(16, 16);
    private PowerType type;
    private HUD hud;
    private int duration = 30;
    private boolean shouldRemove = false;

    /**
     * Initialises a powerUp with a random type
     * @param spawnPos The position the powerUp will be at
     * @param hud The games HUD
     */
    public PowerUps(Vector2 spawnPos, HUD hud) {
        super(spawnPos, new Texture("thunder.png"), imSize, health, radius);
        this.hud = hud;
        type = PowerType.getRandomType();
        this.setPowerUpTexture();
    }

    /**
     * Intialises a powerUp with a specified type
     * @param spawnPos The position the powerUp will be at
     * @param hud The games HUD
     * @param typeValue The value of the PowerUp type
     */
    public PowerUps(Vector2 spawnPos, HUD hud, int typeValue)
    {
        super(spawnPos, new Texture("thunder.png"), imSize, health, radius);
        this.hud = hud;
        type = PowerType.valueOf(typeValue);
        this.setPowerUpTexture();
    }

    /**
     * Update all the power ups
     */
    @Override
    public void update(){
        //Checks that if player in range of powerUp, apply the powerUp to Player [ID: APPLY]
        if (playerInRadius()) {
            FireTruck player = Kroy.mainGameScreen.getPlayer();
            shouldRemove = true;

            switch (type) {
                case SPEED:
                    player.SpeedUp();
                    //Adds the message displaying the powerUp to the HUD [ID: ADD]
                    hud.addPowerUpMessage(this);
                    break;
                case FULLHEALTH:
                    player.fullHealth();
                    this.setDuration(3);
                    //Adds the message displaying the powerUp to the HUD [ID: ADD]
                    hud.addPowerUpMessage(this);
                    break;
                case FASTSHOOTING:
                    player.fastShooting();
                    //Adds the message displaying the powerUp to the HUD [ID: ADD]
                    hud.addPowerUpMessage(this);
                    break;
                case RANGE:
                    player.increaseRange();
                    //Adds the message displaying the powerUp to the HUD [ID: ADD]
                    hud.addPowerUpMessage(this);
                    break;
                case REFILLWATER:
                    player.refillWater();
                    this.setDuration(3);
                    //Adds the message displaying the powerUp to the HUD [ID: ADD]
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

        //Removes the powerUp affect from the Player [ID: REMOVE]
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

    /***
     * Sets the texture of the powerUp based on its type
     */
    public void setPowerUpTexture()
    {
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

    public int getDuration() { return duration; }
    public PowerType getType() { return type; }
    public HUD getHud() { return hud; }
    public boolean isShouldRemove() { return shouldRemove; }

    public void setDuration(int duration) { this.duration = duration; }
    public void setType(PowerType type) { this.type = type; this.setPowerUpTexture(); }
    public void setShouldRemove(boolean shouldRemove) { this.shouldRemove = shouldRemove; }
}
