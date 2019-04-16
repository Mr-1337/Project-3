package com.brackeen.javagamebook.test;

/**
 * @author Josh Figueiredo
 */

public class TestPackageTracingEnabled {
	private boolean isEnabled = true;
    private static TestPackageTracingEnabled instance = new TestPackageTracingEnabled();
	
    private TestPackageTracingEnabled()
    {
    
    }

    public static TestPackageTracingEnabled getTestPackageTracingEnabledInstance()
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
