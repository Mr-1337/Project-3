package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;

public class Zombie extends Creature
{

	public Zombie(Animation left, Animation right, Animation deadLeft, Animation deadRight)
	{
		super(left, right, deadLeft, deadRight);
		// TODO Auto-generated constructor stub
		trackPlayer = true;
		this.wakeUp();
	}
	
	public float getMaxSpeed() {
        return 0.20f * enemySpeedMultiplier;
    }

}
