package com.brackeen.javagamebook.sound;

/**
 * @author Josh Figueiredo
 */

public class SoundPackageTracingEnabled {
	private boolean isEnabled = true;
    private static SoundPackageTracingEnabled instance = new SoundPackageTracingEnabled();
	
    private SoundPackageTracingEnabled()
    {
    
    }

    public static SoundPackageTracingEnabled getSoundPackageTracingEnabledInstance()
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
