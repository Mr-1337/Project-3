/*
 * Created on Nov 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.util.*;
import com.brackeen.javagamebook.codereflection.*;

public class Boss extends Creature {

	private static final int MILI_PER_SECOND = 1000; 
	private long initJumpInterval = 2 * MILI_PER_SECOND; 
	private long jumpInterval = initJumpInterval;

    private Throwable e = new Throwable();
	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Boss(Animation left, Animation right, Animation deadLeft, Animation deadRight) 
	{
		super(left, right, deadLeft, deadRight);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		
		this.jumpSpeed = -0.65f;
		this.trackPlayer=true;
		
		health = 10;
		
		//Make Boss intelligent
		intelligent = true;
	}
	
    public float getMaxSpeed() {
        return 0.15f * enemySpeedMultiplier;
    }
    
    public void update(long elapsedTime) {
		// Call super to maintain animation behavior
		super.update(elapsedTime);
		
		if((totalElapsedTime % jumpInterval)-elapsedTime<0){
			//Jump is on the ground
			jump();
			jumpInterval = initJumpInterval + RandomUtil.getRandomInt(200);
		}
	}
}