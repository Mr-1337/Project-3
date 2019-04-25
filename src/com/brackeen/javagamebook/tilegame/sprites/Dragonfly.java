package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class Dragonfly extends Creature{
	
	public Dragonfly(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
		
		trackPlayer = true;
		flying = true; 
		health = 2;
	}
	
	public float getMaxSpeed() {
		return .12f * enemySpeedMultiplier;
	}
	
	

}
