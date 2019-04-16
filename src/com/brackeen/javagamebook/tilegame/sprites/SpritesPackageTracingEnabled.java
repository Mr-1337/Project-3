package com.brackeen.javagamebook.tilegame.sprites;

/**
 * @author Josh Figueiredo
 */

public class SpritesPackageTracingEnabled {
	private boolean isEnabled = true;
    private static SpritesPackageTracingEnabled instance = new SpritesPackageTracingEnabled();
	
    private SpritesPackageTracingEnabled()
    {
    
    }

    public static SpritesPackageTracingEnabled getSpritesPackageTracingEnabledInstance()
    {
    	return(instance);
    }
    
    public void setEnabled(boolean value)
    {
    	isEnabled = value;
    }
    
    public boolean isEnabled()
    {
    	return isEnabled;
    }
}
