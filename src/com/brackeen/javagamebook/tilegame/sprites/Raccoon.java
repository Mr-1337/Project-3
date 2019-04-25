package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.tilegame.GameManager;

import our.stuff.graphics.Projectile;

public class Raccoon extends Creature {
	
	private int time = 0;
	private final int SHOOT_SPEED = 500;
	
	public Raccoon(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
	}
	
	public float getMaxSpeed() {
		return .25f * enemySpeedMultiplier;
	}
	
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		time += elapsedTime;
		if (time > SHOOT_SPEED && Math.abs(GameManager.getGameManagerInstance().getMap().getPlayer().getX() - this.getX()) < 400)
		{
			Animation a = new Animation();
			a.addFrame(GameManager.getGameManagerInstance().getResourceManagerInstance().loadImage("balloon.png"), 15);
			Projectile shot = new Projectile(a, a, a, a, this.getX(), this.getY() - 70);
			
			GameManager.getGameManagerInstance().queueSprite((Sprite)shot);
			time -= SHOOT_SPEED;
		}
	}
	
}