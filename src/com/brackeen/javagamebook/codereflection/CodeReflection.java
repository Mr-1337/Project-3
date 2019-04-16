/*
 * Created on Nov 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.codereflection;

import com.brackeen.javagamebook.graphics.*;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CodeReflection implements Runnable{
	private CodeReflection()
	{
		//singleton
	}
	
	private static CodeReflection codeReflection = new CodeReflection();
	//Only instance of Code Reflection
	
	private static int abstraction=0;
	//level of abstraction: how much code do we want to see
	
	private static Thread codeReflectionThread;
	//spin a new thread to avoid significant slowdown
	
	private static boolean isRunning = false;
	
	private static Queue codeQueue = new Queue();
	
	public static CodeReflection getCodeReflectionInstance()
    {	//hand out the only CodeReflection Instance here
    	return(codeReflection);
    }
	
	public static int getAbstactionLevel()
	{	//return the integer level of Abstraction needed
		return abstraction;
	}

	public static void setAbstractionLevel(int x)
	{	//set the integer level of Abstraction wanted
		abstraction = x;
	}
	
	public static void traceMethods(boolean start)
	{   //call this method to start tracing the code
		isRunning=start;
		if(isRunning){
			codeReflectionThread = new Thread(codeReflection);
			codeReflectionThread.start();
		}else
		//purge the queue
			while(!codeQueue.isEmpty())
				codeQueue.deQueue();
	}
	
	public static boolean isTracing()
	{
		return isRunning;
	}
	
	public static void registerMethod(String className, String methodName)
	{
		/*Use this method to register a Method as being called
		 * For example place the following at the beginning
		 * of each method:
		 * Exception e = new Exception();
		 * CodeReflection.getCodeReflectionInstance().registerMethod(
		 *    		e.getStackTrace()[0].getClassName(),e.getStackTrace()[0].getMethodName());
		 */
		if(codeQueue.roomLeft()!=0)
			codeQueue.enQueue(className+"::>"+methodName);
	}
	
    public void run()
    {
  	  while(isRunning)
  	  {
  		  ToolFrame f = ToolFrame.getToolFrameInstance();
  		 
  		  //print the contents of the queue
  		  while(!codeQueue.isEmpty())
  		  	f.printToCodeExecution((String)codeQueue.deQueue());

  		  try
  		  {
  			  Thread.sleep(20);
  		  }
  		  catch(Exception e)
  		  {
  			  
  		  }
  	  }
    }
}
