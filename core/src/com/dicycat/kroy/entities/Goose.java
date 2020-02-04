package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;


/**
 * Goose class for minigame
 * 
 * @author Sam Hutchings
 *
 */
public class Goose extends Entity {
	private float deceleration = 0.5f;
	private float velocity = 0;
	private Rectangle hitbox = new Rectangle(0, 0, 48*scale, 56*scale);
	private static float scale = 1.5f;

	public Goose(Vector2 spawnPos, Texture img, Vector2 imSize, int health) {
		super(spawnPos, img, imSize, health);
	}

	public Goose() {
		this(new Vector2(-32*scale, -32*scale), new Texture("goose2.png"), new Vector2(64*scale, 64*scale), 1);
		System.out.println("goose created");
	}

	@Override
	public void update() {
		if (getY() < -400) {
			velocity = 0;
		} else if (velocity > -5f) {
			velocity -= deceleration;
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			velocity = 10;
		}
		setPosition(new Vector2(getX(), getY() + velocity));
		
		hitbox.setCenter(getCentre().x, getCentre().y);
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitbox.x, hitbox.y), new Vector2(hitbox.width, hitbox.height), 2, Color.GREEN);
	}
	
	public Rectangle getHitbox(){
		return this.hitbox;
	}


}
