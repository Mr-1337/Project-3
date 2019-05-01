package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.codereflection.*;

/**
    The Player.
*/
public class Player extends Creature {
	
//    private Throwable e = new Throwable();
    
	public int consecutiveHits=0;
	
	public static float playerJumpSpeedMultiplier = 1.0f;
	
    public Player(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        helper = true;
    }

    public void jump(boolean forceJump) {
        if ((onGround || forceJump) && isAlive()) {
            onGround = false;
            setVelocityY(jumpSpeed * playerJumpSpeedMultiplier);
        }
    }

    public void collideHorizontal() {
        setVelocityX(0);
    }




    public void wakeUp() {
    }




    public float getMaxSpeed() {
        return 0.5f;
    }

}
