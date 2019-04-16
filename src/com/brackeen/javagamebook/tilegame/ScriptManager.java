/*
 * Created on Oct 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.tilegame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;


import com.brackeen.javagamebook.codereflection.*;
import com.brackeen.javagamebook.input.InputPackageTracingEnabled;


/**
 * @author U_CRogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScriptManager 
{
	private static String levelMap = "levelmap.spt";
	
	private String[] listOfImages;
	private int enemies;
	private int sprites;
	private String[] archType;
	private String[] maps;
    private String[] levelImages;
    private String[] levelMusic;
    private String[][] itemSounds;
    
    private String[] coinImages;
    private String[] noteImages;
    private String[] eolImages;
    private String[] warpImages;
    private String[] healthImages;
    
    private int soundArrayLength;
    private Throwable e = new Throwable();
	private static BufferedReader tutorialReader;
    
	private ScriptManager() throws IOException
	{//Singleton
		//Load Sprite Information
	 	BufferedReader reader = new BufferedReader(
	            new FileReader("script/sprite.spt"));
	    String line;
	    int imageIndex,archIndex;
	    
	    while((line=reader.readLine()).charAt(0)=='#');//skip comments
	    enemies=Integer.valueOf(line).intValue();	//set total enemies
	  
	    while((line=reader.readLine()).charAt(0)=='#');//skip comments
	    sprites=Integer.valueOf(line).intValue();	//set total sprite images
	    
	    listOfImages= new String[sprites];
	    archType= new String[enemies];
	    
	    //Load player image names
	    while((line=reader.readLine()).charAt(0)=='#');//skip comments
	    listOfImages[0]=line.substring(1);
	    while((line=reader.readLine()).charAt(0)=='#');//skip comments
	    listOfImages[1]=line.substring(1);
	    while((line=reader.readLine()).charAt(0)=='#');//skip comments
	    listOfImages[2]=line.substring(1);
	    
	    imageIndex=3;//Init
	    archIndex=0; //init
	    //Load enemy image names and archtypes
		while ((line=reader.readLine()) != null) 
		{
		 		if(line.charAt(0)!='#')	//its not a comment
		 		{
		 			if(line.charAt(0)=='>')//its a image filename
		 				listOfImages[imageIndex++]=line.substring(1);
		 			else	//it must be an archtype
		 				archType[archIndex++]=line;
		 		}
		}
		reader.close();
		
		
		//Load Map information
		int mapIndex=0;
		reader = null;
		
		try{
		reader = new BufferedReader(
	            new FileReader("script/"+levelMap));
		}catch(FileNotFoundException io)
		{
			reader = new BufferedReader(
		            new FileReader("script/levelmap.spt"));
		} 
		while((line=reader.readLine()).charAt(0)=='#');//skip comments
		
		maps = new String[Integer.valueOf(line).intValue()]; //init size to total # of maps
		levelImages = new String[Integer.valueOf(line).intValue()]; //init size to total # of maps
		levelMusic =  new String[Integer.valueOf(line).intValue()]; //init size to total # of maps
		
		while ((line=reader.readLine()) != null) 
		{
		 // scan every line look for your level
			if (line.startsWith(Integer.toString(mapIndex+1))) 
			{	//get ride of level number identified and use the rest
	            maps[mapIndex] = line.substring(line.indexOf(">")+1);
	           
	            while((line=reader.readLine()).charAt(0)=='#');//skip comments
	            levelImages[mapIndex] = line.substring(line.indexOf(">")+1);	//read background filenames
	           
	            while((line=reader.readLine()).charAt(0)=='#');//skip comments
	            levelMusic[mapIndex++] = line.substring(line.indexOf(">")+1);	//read music filenames
	          
			}
		}
		reader.close();
		
		//Read in Sound filenames
		int soundIndex=0;
		reader = new BufferedReader(
	            new FileReader("script/soundmap.spt"));
	      
		while((line=reader.readLine()).charAt(0)=='#');//skip comments
		
		soundArrayLength = Integer.valueOf(line).intValue();
		itemSounds = new String[soundArrayLength][2]; //init size to total # of sounds
				
		while ((line=reader.readLine()) != null) 
		{
		 // scan every line look for valid input
			if(line.charAt(0)!='#')//skip comments
			{
				itemSounds[soundIndex][0]=line.substring(0,line.indexOf(" "));
				itemSounds[soundIndex++][1]=line.substring(line.indexOf('>')+1,line.length());
			}
		}
		reader.close();
		
		//Read in item images filenames
		reader = new BufferedReader(
	            new FileReader("script/items.spt"));
		
		coinImages = new String[4]; //init size 4 (4 frames of animation)
		noteImages = new String[4]; //init size 4 (4 frames of animation)
		warpImages = new String[4]; //init size 4 (4 frames of animation)
		eolImages = new String[4]; //init size 4 (4 frames of animation)
		healthImages = new String[4]; //init size 4 (4 frames of animation)
				
		while ((line=reader.readLine()) != null) 
		{					
		 // scan every line look for valid input
			if(line.charAt(0)!='#')//skip comments
			{
				if(line.compareTo("coin")==0)
				{
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					coinImages[0]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					coinImages[1]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					coinImages[2]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					coinImages[3]=line.substring(line.indexOf(">")+1,line.length());
				}else
				if(line.compareTo("note")==0)
				{
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					noteImages[0]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					noteImages[1]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					noteImages[2]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					noteImages[3]=line.substring(line.indexOf(">")+1,line.length());
				}else
				if(line.compareTo("warp")==0)
				{
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					warpImages[0]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					warpImages[1]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					warpImages[2]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					warpImages[3]=line.substring(line.indexOf(">")+1,line.length());
				}else
				if(line.compareTo("eol")==0)
				{
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					eolImages[0]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					eolImages[1]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					eolImages[2]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					eolImages[3]=line.substring(line.indexOf(">")+1,line.length());
				}else
				if(line.compareTo("health")==0)
				{
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					healthImages[0]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					healthImages[1]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					healthImages[2]=line.substring(line.indexOf(">")+1,line.length());
					while((line=reader.readLine()).charAt(0)=='#');//skip comments
					healthImages[3]=line.substring(line.indexOf(">")+1,line.length());
				}
			}
		}
		reader.close();
	}
	
    private static ScriptManager scriptManagerInstance = null;
    
    public static void rebuildInstance()
    {
    	scriptManagerInstance = null;
    	try{
    	scriptManagerInstance = new ScriptManager();
    	}catch(Exception IO){}
    }
    
    public static String nextTutorialMessage()
    {
    	String message = "";
    	String buf =null;
    	boolean stopMessage=false;
    	
    	if(tutorialReader == null)
    	{
    		try{
    		tutorialReader = new BufferedReader(
    	            new FileReader("tutorial/tutorial.txt"));
    		}catch(Exception IO){
    			System.out.println("nextTutorialMessage()::"+IO.getMessage());
    		};
    	}
    	
    	try{
  		while((!stopMessage)&&((buf=tutorialReader.readLine())!=null))
			if((buf.charAt(0))!='>')
				if(buf.charAt(0)!='&')
					message+=buf+"\n";
				else stopMessage = true;
			
  		if(buf==null){
  			tutorialReader.close();	//loop back to begining
  			tutorialReader=null;
  			//Run again so it's not skipped
  			return(ScriptManager.nextTutorialMessage());
  		}
    	}catch(Exception IO){
    		//System.out.println("nextTutorialMessage()::"+IO.getMessage());
    	}
    	return message;
    }
    
    public static ScriptManager getScriptManagerInstance()
	{
    	if(scriptManagerInstance == null)
        try{
        	scriptManagerInstance = new ScriptManager();
        }catch(IOException ex){};
		return scriptManagerInstance;
	}
	
	 public String getMap(int level) throws IOException
	 {
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	//Given a level number, this function will find the textfile
	 	//name of the level map and return it; Default level1.txt

	 	if((level>0)&&(level<maps.length+1))
	 		return(maps[level-1]);
        return(null);
        
	 }
	 public String getArchType(int x)
	 {  //returns the specified ArchType of enemy x
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return (archType[x]);
	 }
	 public int getNumberOfEnemies()
	 {  //returns the number of enemies
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=3)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return (enemies);
	 }
	 public String getSpriteImage(int x)
	 {  //returns the sprite image filename of x
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return (listOfImages[x]);
	 }
	 public int getNumberOfSprites()
	 {  //returns the number of sprite images
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return (sprites);
	 }
	 public String getLevelImage(int level)
	 {
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return(levelImages[level-1]);
	 }
	 public String getlevelMusic(int level)
	 {
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return(levelMusic[level-1]);
	 }
	 public String getSoundByReference(String x)
	 {
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	for(int z=0; z<soundArrayLength; z++)
	 	{
	 		if(x.compareTo(itemSounds[z][0])==0)
	 			return itemSounds[z][1];
	 	}
	 	return null;
	 }
	 public String getItemImage(String name, int number)
	 {	//get the type of image it is, and return the name of the image based on which one (number)
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	if(name.compareTo("coin")==0)
	 		return(coinImages[number-1]);
	 	if(name.compareTo("note")==0)
	 		return(noteImages[number-1]);
	 	if(name.compareTo("warp")==0)
	 		return(warpImages[number-1]);
	 	if(name.compareTo("eol")==0)
	 		return(eolImages[number-1]);
	 	if(name.compareTo("health")==0)
	 		return(healthImages[number-1]);
	 	return (null);
	 }
	 
	 public void setLevelMappingFile(String scriptFile)
	 {	//This tells what script file to use for level loading
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	levelMap = scriptFile;
	 }
	 
	 public String getLevelMappingFile()
	 {	//Tell what script file is being used to load levels
	    	if(CodeReflection.isTracing() && InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return(levelMap);
	 }
}
