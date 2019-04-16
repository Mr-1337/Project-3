/*
 * Created on Jan 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;
import java.lang.Math;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SinuousFly extends Creature {
	
	//Constructor, calls super constructor
	public SinuousFly(Animation left, Animation right,
	        Animation deadLeft, Animation deadRight)
	    {
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
	    }
	
    public float getMaxSpeed() 
    {//Return the max speed of this Sinuous Fly
    	
    	//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0.2f * enemySpeedMultiplier;
    }

    public boolean isFlying() 
    {//Returns if the Sinuous Fly is "Flying"
    	
    	//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
   	
    	//make sure the SinuousFly is alive
        return isAlive() && super.isFlying();
    }
    
    public void update(long elapsedTime) 
    {//Override the call to update
    	
		// Call super to maintain animation behavior
		super.update(elapsedTime);
		
		//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	
        if (!onGround && isAlive()) 
        {
            //onGround = false;
            setVelocityY((float)Math.sin(this.getX()/100)*this.getVelocityX() * enemySpeedMultiplier);
        }
	}
}
