package com.brackeen.javagamebook.tilegame.sprites;

import java.lang.reflect.Constructor;
import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.codereflection.*;

/**
    A PowerUp class is a Sprite that the player can pick up.
*/
public abstract class PowerUp extends Sprite {
	
//    private Throwable e = new Throwable();

    public PowerUp(Animation anim) {
        super(anim);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    }


    public Object clone() {
        // use reflection to create the correct subclass
        Constructor constructor = getClass().getConstructors()[0];
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        try {
            return constructor.newInstance(
                new Object[] {(Animation)anim.clone()});
        }
        catch (Exception ex) {
            // should never happen
            ex.printStackTrace();
            return null;
        }
    }


    /**
        A Star PowerUp. Gives the player points.
    */
    public static class Star extends PowerUp {
        public Star(Animation anim) {
            super(anim);
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=1)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
        }
    }


    /**
        A Music PowerUp. Changes the game music.
    */
    public static class Music extends PowerUp {
        public Music(Animation anim) {
            super(anim);
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=1)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
        }
    }


    /**
        A Goal PowerUp. Advances to the next map.
    */
    public static class Goal extends PowerUp {
        public Goal(Animation anim) {
            super(anim);
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=1)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
        }
    }
    
    
    /**
     * 	A Warp PowerUp.  Depending on the value will warp ahead x levels
     */
    public static class Warp extends PowerUp 
	{
    	private int value;
        public Warp(Animation anim) 
        {
        	super(anim);
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=1)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
        }
        public void setWarpValue(int x)
        {
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=2)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
        	value = x;
        }
        public int getWarpValue()
        {
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=2)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
        	return value;
        }
    }
    
    /**
     * A health PowerUp, when received will increase the hits
     * you can take before dying
     */
    public static class Health extends PowerUp
	{
    	public Health(Animation anim)
    	{
    		super(anim);
        	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=1)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
    	}
	}
}
