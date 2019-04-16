/*
 * Created on Jan 9, 2006
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
public class Balloon extends Creature {

	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Balloon(Animation left, Animation right, Animation deadLeft,
			Animation deadRight) 
	{
		//Call Creature constructor
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
		
		//Define as a helper 
		helper = true;
		
		//Set as flying
		flying = true;
	}
	
    public float getMaxSpeed() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0.2f;
    }

}
