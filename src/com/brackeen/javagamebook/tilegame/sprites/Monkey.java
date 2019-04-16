/*
 * Created on Mar 2, 2005
 * by Daniels.Douglas@gmail.com
 */
package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

import com.brackeen.javagamebook.util.*;
import com.brackeen.javagamebook.codereflection.*;

/**
 * @author danielsd
 * Daniels.Douglas@gmail.com
 */
public class Monkey extends Creature {

	private static final int MILI_PER_SECOND = 1000; 
	//TODO set a jump interval to only jump after a certain number of seconds
	// have elapsed.
	private long initJumpInterval = 2 * MILI_PER_SECOND; 
	private long jumpInterval = initJumpInterval;
//    private Throwable e = new Throwable();
	
	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Monkey(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		//TODO set the creatures jump speed using protected jumpSpeed class variable
		this.jumpSpeed = -0.65f;
		this.trackPlayer=true;
		
	}
	
	
	//TODO Set how fast you want the monkey to move.
    public float getMaxSpeed() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0.15f * enemySpeedMultiplier;
    }
    

    
    //TODO Extend the update method to provide new creature behavior
    // Can use time elapsed to do actions only at certain time intervals.
    
	/*
	 * @see com.brackeen.javagamebook.graphics.Sprite#update(long)
	 */
	public void update(long elapsedTime) {
		// Call super to maintain animation behavior
		super.update(elapsedTime);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		//See if its time for the monkey to jump
		//Because time comes in at intervals we have to make sure that
		//we jump in whichever elapsedTime interval that the timer has gone off in.
		if((totalElapsedTime % jumpInterval)-elapsedTime<0){
			//Make the monkey jump if he's not already in the air
			jump();
			jumpInterval = initJumpInterval + RandomUtil.getRandomInt(200);
		}
	}
}
