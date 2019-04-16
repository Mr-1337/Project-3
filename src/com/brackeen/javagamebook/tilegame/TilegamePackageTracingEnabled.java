package com.brackeen.javagamebook.tilegame;



public class TilegamePackageTracingEnabled {
	private boolean isEnabled = true;
    private static TilegamePackageTracingEnabled instance = new TilegamePackageTracingEnabled();
	
    private TilegamePackageTracingEnabled()
    {
    
    }

    public static TilegamePackageTracingEnabled getTilegamePackageTracingEnabledInstance()
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
