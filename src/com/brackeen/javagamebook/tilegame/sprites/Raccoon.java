package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class Raccoon extends Creature {
	
	public Raccoon(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
	}
	
	public float getMaxSpeed() {
		return .25f * enemySpeedMultiplier;
	}
	
}