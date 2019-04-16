package com.brackeen.javagamebook.util;

/**
 * @author Josh Figueiredo
 */

public class UtilPackageTracingEnabled {
	private boolean isEnabled = true;
    private static UtilPackageTracingEnabled instance = new UtilPackageTracingEnabled();
	
    private UtilPackageTracingEnabled()
    {
    
    }

    public static UtilPackageTracingEnabled getUtilPackageTracingEnabledInstance()
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
