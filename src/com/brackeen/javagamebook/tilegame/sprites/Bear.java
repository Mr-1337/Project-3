package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class Bear extends Creature {
	
	public Bear(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
		trackPlayer = true;
		health = 2;
	}
	
	public float getMaxSpeed() {
		return .225f * enemySpeedMultiplier;
	}
	
	@Override
	public void update(long elapsedTime) {
		super.update(elapsedTime);
		Animation newAnim = anim;
		if (state == STATE_HURT && newAnim == deadLeft) {
            newAnim = left;
        }
        else if (state == STATE_HURT && newAnim == deadRight) {
            newAnim = right;
        }
		if (anim != newAnim) {
            anim = newAnim;
            anim.start();
        }
        else {
            anim.update(elapsedTime);
        }
	}
}