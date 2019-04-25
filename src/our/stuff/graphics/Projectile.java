package our.stuff.graphics;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.tilegame.sprites.Creature;
import com.brackeen.javagamebook.tilegame.sprites.Player;

public class Projectile extends Creature
{

	private float theta = 0f;
	
	public Projectile(Animation left, Animation right, Animation deadLeft, Animation deadRight, float x, float y)
	{
		super(left, right, deadLeft, deadRight);
		this.setX(x);
		this.setY(y);
		Player p = (Player)GameManager.getGameManagerInstance().getMap().getPlayer();
		float px = p.getX();
		float py = p.getY() - 30;
		theta = (float)Math.atan2((py - this.getY()), px - this.getX());
		this.setVelocityX(this.getMaxSpeed() * (float)Math.cos(theta));
		this.setVelocityY(this.getMaxSpeed() * (float)Math.sin(theta));
		flying = true;
		jumpSpeed = 0f;
	}
	
	@Override
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
	}
	
	@Override
	public float getMaxSpeed()
	{
		return Creature.enemySpeedMultiplier * 0.2f;
	} 

}
