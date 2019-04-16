package com.brackeen.javagamebook.input;

/**
 * @author Josh Figueiredo
 */

public class InputPackageTracingEnabled {
	private boolean isEnabled = true;
    private static InputPackageTracingEnabled instance = new InputPackageTracingEnabled();
	
    private InputPackageTracingEnabled()
    {
    
    }

    public static InputPackageTracingEnabled getInputPackageTracingEnabledInstance()
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
