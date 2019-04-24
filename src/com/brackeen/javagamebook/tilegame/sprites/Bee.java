package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class Bee extends Creature {
	
	public Bee(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
		flying =  true;
		trackPlayer = true;
	}
	
	public float getMaxSpeed() {
		return .05f * enemySpeedMultiplier;
	}
	
	public boolean isFlying() {
		return isAlive() && super.isFlying();
	}
	
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		if (!onGround && isAlive()) {
			setVelocityY((float)Math.sin(this.getX()/100*this.getVelocityX()*enemySpeedMultiplier));
		}
	}
}