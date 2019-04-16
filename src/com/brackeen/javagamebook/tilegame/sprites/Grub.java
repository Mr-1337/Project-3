package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.codereflection.*;

/**
    A Grub is a Creature that moves slowly on the ground.
*/
public class Grub extends Creature {

//    private Throwable e = new Throwable();
    
    public Grub(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
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
        return 0.05f * enemySpeedMultiplier;
    }

}
