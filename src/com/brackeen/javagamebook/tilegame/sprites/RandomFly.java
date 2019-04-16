/*
 * Created on Jan 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;
import java.util.Random;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RandomFly extends Creature {

	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	
	protected long totalElapsedTime = 0;
	protected long randomInterval = 100;
	protected Random random1;
	protected Random random2;
	
	public RandomFly(Animation left, Animation right, Animation deadLeft,
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
        
        //The fly is not following the hero
        trackPlayer = false;
        
        //Create Random instances
        random1 = new Random();
        
        //Sleep a short while so the two different generates will be different
        try{
        	Thread.sleep(10);
        }catch(Exception e)
		{
        	System.out.println("Thread Sleep failed in RandomFly constructor");
        };
        
        random2 = new Random();
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
        return 0.2f * enemySpeedMultiplier;
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
    	
    	//Keep track of total elapsedTime
		totalElapsedTime += elapsedTime;
    	
		//Is it time to change the direction and speed
		if(((totalElapsedTime / randomInterval)>=1)&&isAlive())
		{
			totalElapsedTime = 0;
			int randomInt = random1.nextInt(2);
			
			if(randomInt == 0)
			{
				setVelocityX(((random1.nextFloat()-0.5f)/1.3f)*enemySpeedMultiplier);
			}else
				if(randomInt == 1)
				{
					setVelocityY(((random2.nextFloat()-0.5f)/1.3f)*enemySpeedMultiplier);
				}
				
			//If the fly "flies" too high, send it in the other direction
			if(getY()<-400)
				setVelocityY(1.5f);
		}
			
	}
}
