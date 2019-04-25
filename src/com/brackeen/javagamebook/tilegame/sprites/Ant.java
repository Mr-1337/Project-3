package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class Ant extends Creature{
	
	public Ant(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
		
		jumpSpeed = -0f;
		
	}
	
	public float getMaxSpeed() {
		return .12f * enemySpeedMultiplier;
	}
	
	

}
