package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;

public class Moth  extends Creature{
	
	private Player player;
	
	public Moth(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left,right, deadLeft,deadRight);
		flying = true;
		health = 5;
	}
	
	private static float SPEED = .11f;
	
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		player = (Player)GameManager.getGameManagerInstance().getMap().getPlayer();
		float distance = player.getX() - this.getX();
		if (this.getState() == STATE_NORMAL)
			this.setVelocityX( Math.signum(distance) * (1f/ (float)Math.sqrt((float)Math.abs(0.05f * distance))) * SPEED);
	}

}