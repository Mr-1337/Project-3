package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.tilegame.GameManager;

import our.stuff.graphics.Projectile;

public class BetterBear extends Bear
{

	public BetterBear(Animation left, Animation right, Animation deadLeft, Animation deadRight)
	{
		super(left, right, deadLeft, deadRight);
		// TODO Auto-generated constructor stub
		
	}
	
	public float getMaxSpeed()
	{
		return super.getMaxSpeed() * 1.5f;
	}
	
	private int time = 0;
	private static int SHOOT_SPEED = 90;
	
	public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        time += elapsedTime;
        if (time > SHOOT_SPEED && Math.abs(GameManager.getGameManagerInstance().getMap().getPlayer().getX() - this.getX()) < 400)
        {
            Animation a = new Animation();
            a.addFrame(GameManager.getGameManagerInstance().getResourceManagerInstance().loadImage("fireball.png"), 15);
            Projectile shot = new Projectile(a, a, a, a, this.getX(), this.getY(), 0.5f);
            
            GameManager.getGameManagerInstance().queueSprite((Sprite)shot);
            time -= SHOOT_SPEED;
        }
    }

}
