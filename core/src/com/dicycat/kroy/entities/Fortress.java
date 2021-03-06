package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.misc.StatBar;
import com.dicycat.kroy.DifficultyMultiplier;

/**
 * Static hostile Entity.
 * Fires at the player when within its radius.
 * 
 * @author 
 *
 */
public class Fortress extends Entity {

	private BulletDispenser dispenser;
	private Texture deadTexture;
	private StatBar healthBar;
	/**
	 * @param spawnPos
	 * @param fortressTexture
	 * @param deadTexture
	 * @param size
	 */
	public Fortress(Vector2 spawnPos, Texture fortressTexture, Texture deadTexture, Vector2 size) {
		super(spawnPos, fortressTexture, size, 500);
		this.setup(deadTexture);
	}

	/**
	 * @param spawnPos
	 * @param fortressTexture
	 * @param deadTexture
	 * @param size
	 */
	public Fortress(Vector2 spawnPos, Texture fortressTexture, Texture deadTexture, Vector2 size, int health) {
		super(spawnPos, fortressTexture, size, health);
		this.setup(deadTexture);
	}

	/***
	 * Used to setup info for the fortresses - moved from constructor so that we can have two different constructors - Septagon
	 * @param deadTexture
	 */
	private void setup(Texture deadTexture){
		dispenser = new BulletDispenser(this);
		//Changes the speed which the fortress fires bullets based on the difficulty [ID: SPEED]
		dispenser.addPattern(new Pattern(180, (int)(300 * DifficultyMultiplier.getDifficultySpeed()), 800, 0.1f, 20, 1, 0.5f));
		dispenser.addPattern(new Pattern((int)(100 * DifficultyMultiplier.getDifficultySpeed()), 500, 0.5f, 8, 5, 0.5f));
		dispenser.addPattern(new Pattern(0, (int)(50 * DifficultyMultiplier.getDifficultySpeed()), 800, 2f, 3, 36, 4));
		dispenser.addPattern(new Pattern((int)(200 * DifficultyMultiplier.getDifficultySpeed()), 600, 0.3f, 12, 2, 0.3f));
		dispenser.addPattern(new Pattern(false, 0, 3, (int)(100 * DifficultyMultiplier.getDifficultySpeed()), 900, 0.02f, 1, 0.2f));
		dispenser.addPattern(new Pattern(true, 0, 1, (int)(100 * DifficultyMultiplier.getDifficultySpeed()), 900, 0.02f, 1, 1.2f));

		this.deadTexture = deadTexture;
		healthBar = new StatBar(new Vector2(getCentre().x, getCentre().y + 100), "Red.png", 10);
		Kroy.mainGameScreen.addGameObject(healthBar);
	}
	
	/** 
	 * new
	 */
	public Fortress() {
		super(new Vector2(2903, 3211),  Kroy.mainGameScreen.textures.getFortress(0),  new Vector2(256, 218), 500);
		this.deadTexture = Kroy.mainGameScreen.textures.getDeadFortress(0);
	}
 
	/**
	 * Removes from active pool and displays destroyed state
	 */ 
	@Override
	public void die() {
		super.die();
		sprite.setTexture(deadTexture);
		healthBar.setRemove(true);
		displayable = true;
		Kroy.mainGameScreen.removeFortress();
		if (Kroy.mainGameScreen.fortressesLeft() == 0) {	//If last fortress
			Kroy.mainGameScreen.gameOver(true); 					//End game WIN
		}
	}
	
	/**
	 * new
	 * Removes from active pool and displays destroyed state
	 */ 
	public void death() {
		super.die();
		sprite.setTexture(deadTexture);
		displayable = true;
	}

	/**
	 * Apply x amount of damage to the entity
	 * Updates the health bar
	 * @param damage Amount of damage to inflict on the Entity
	 */
	@Override
	public void applyDamage(float damage) {
		super.applyDamage(damage);
		if(!dead)
		{
			healthBar.setPosition(getCentre().add(0, (getHeight() / 2) + 25));
			healthBar.setBarDisplay((getHealthPoints() * 500) / maxHealthPoints);
		}
	}
	
	/**
	 * new
	 * Apply x amount of damage to the entity
	 */
	public void Damage(float damage) {
		super.applyDamage(damage);
	}
	

	/**
	 *
	 */
	@Override
	public void update() {
		//weapons
		Bullet[] toShoot = dispenser.update(playerInRadius());
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}

	}

	public Texture getDeadTexture() { return deadTexture; }
	public StatBar getHealthBar() { return healthBar; }
	public BulletDispenser getDispenser() { return dispenser; }
}
