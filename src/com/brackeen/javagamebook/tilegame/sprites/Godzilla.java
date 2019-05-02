
package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;

public class Godzilla  extends Creature{
	
	private Player player;
	
	public Godzilla(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left,right, deadLeft,deadRight);
		flying = false;
		trackPlayer = true;
        intelligent = true;
		health = 8;
	}
	
	public float getMaxSpeed()
    {
        return .05f * enemySpeedMultiplier;
    }

}