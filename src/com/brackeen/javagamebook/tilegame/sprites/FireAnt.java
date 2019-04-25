package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class FireAnt extends Ant{
	
	public FireAnt(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
		
		trackPlayer = true;
		jumpSpeed = 0f;
	}
	
	public float getMaxSpeed() {
		return .17f * enemySpeedMultiplier;
	}
	
	

}
