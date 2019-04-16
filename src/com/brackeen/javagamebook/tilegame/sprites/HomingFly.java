/*
 * Created on Jan 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HomingFly extends Creature {

	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public HomingFly(Animation left, Animation right, Animation deadLeft,
			Animation deadRight) {
		//Call the constructor for Creature
		super(left, right, deadLeft, deadRight);
		
		//Code Tracing
        if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	
    	//We don't apply gravity to the fly
        flying = true;
        
        //The fly is following the hero
        trackPlayer = true;
        
        //Set Vertical speed
        setVelocityY(0.15f);
        
        //Set Horizontal Responce Time
        setHorizontalResponceTime(225.0f);
        
        //Set Vertical Responce Time
        setVerticalResponceTime(0.5f);
		
	}
	
	public float getMaxSpeed() 
    {//Return the max speed of this Homing Fly
    	
    	//Code Tracing
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
	
    public boolean isFlying() 
    {//Override isFlying method to check to see if the fly is alive
    	
    	//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	
        return isAlive() && super.isFlying();
    }
    
    public void collideVertical() 
    {	//Reset the Velocity to regular Y velocity
    	super.collideVertical();
    	setVelocityY(0.15f);
    }
    
    public void setState(int state)
    {
    	//Call setState 
    	super.setState(state);
    	
    	//and override the y velocity if hurt
    	if (state == STATE_HURT)
    		this.setVelocityY(0.15f);
    }
}
