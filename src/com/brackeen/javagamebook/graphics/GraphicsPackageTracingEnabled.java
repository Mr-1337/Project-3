package com.brackeen.javagamebook.graphics;




/**
 * @author Josh Figueiredo
 */

public class GraphicsPackageTracingEnabled {
	private boolean isEnabled = true;
    private static GraphicsPackageTracingEnabled instance = new GraphicsPackageTracingEnabled();
	
    private GraphicsPackageTracingEnabled()
    {
    
    }

    public static GraphicsPackageTracingEnabled getGraphicsPackageTracingEnabledInstance()
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
