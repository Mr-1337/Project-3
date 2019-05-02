package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;

public class BadBalloon extends Balloon
{

	public BadBalloon(Animation left, Animation right, Animation deadLeft, Animation deadRight)
	{
		super(left, right, deadLeft, deadRight);
		// TODO Auto-generated constructor stub
		flying = true;
	}
	
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		Player p = (Player)GameManager.getGameManagerInstance().getMap().getPlayer();
		float dx = p.getX() - getX();
		float dy = p.getY() - getY() - 60f;
		float theta = (float)Math.atan2(dy, dx);
		
		this.setVelocityX((float)Math.cos(theta) * this.getMaxSpeed());
		this.setVelocityY((float)Math.sin(theta) * this.getMaxSpeed());	
		
		if(Math.abs(dx) <= 55f && Math.abs(dy) <= 55f)
		{
			this.setVelocityY(-getMaxSpeed());
			this.setVelocityX(0f);
			p.setY(getY() + 100f);
			p.setX(getX());
			p.setVelocityY(0f);
			p.setVelocityX(0f);
		}
		
		if (this.getY() < -250f)
		{
			p.setY(-100f);
			p.setVelocityY(0.5f);
		}
	}
	
	public float getMaxSpeed()
	{
		return 0.1f * enemySpeedMultiplier;
	}

}
