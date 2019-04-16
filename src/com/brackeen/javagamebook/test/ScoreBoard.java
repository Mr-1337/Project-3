/*
 * Created on Mar 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


package com.brackeen.javagamebook.test;

import java.awt.*;
import java.text.*;
import javax.swing.ImageIcon;

import com.brackeen.javagamebook.tilegame.ScriptManager;
import com.brackeen.javagamebook.codereflection.*;

public class ScoreBoard {
	
	private int score=0;
	private float multiplier=1;
    private String fontFamily = "Serif";
    private int normalStyle = Font.PLAIN;
    private int animStyle = Font.BOLD;
	private int normalFontSz=26;
	private int fontIntervals=10;
	private int maxFontSz=normalFontSz+fontIntervals;
	private Font defaultFont;
	private Font[] animFonts = new Font[fontIntervals];
	private long lastScore=0;
	private long totalElapsedTime;
	private long timeStartAnim=0;
	private long timeStopAnim=0;
	private int totalAnimTime=0;
	private int animTimeScaleFactor=0;
	private int maxAnimTime= 1500; //1.5 seconds max animation
	private boolean animFont=false;

	private Image i1;
	private Image i2;
	
	private int blink;
	private int starTotal=0;
	
	private boolean drawLevelComplete = false;
	
	//Set score left side to 0's until they get a high enough score
	private NumberFormat f =  new DecimalFormat("00000000");
	private String currentScore="0";
    private Throwable e = new Throwable();
	
	public ScoreBoard()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		//Generate fonts from size 32 to 12, to use in animating scoreboard
		for(int i=0; i< fontIntervals; i++) {
			//Setup font animation styles
			animFonts[i] = new Font(fontFamily, animStyle, normalFontSz/*+maxFontSz-i*/);
		}
		refreshScore();
	}
	//TODO Draw the scoreBoard at specific location
	public void draw(Graphics2D g, int width, int height, long totalElapsedTime
			, int level, int health, long hitClock, ScriptManager sci)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		String s = "Total Score: "+getScore()+"   Level: "+level+"   Time: "+(totalElapsedTime/1000);
		if(multiplier!=1.0)
			s=s+"   Bonus: "+multiplier+"x";
		
		Color oldForeColor = g.getColor();
		g.setColor(Color.BLACK);
		this.totalElapsedTime = totalElapsedTime;
		g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(),15));
		//updateFontSize(g);
		g.drawString(s, 10, height-15 );
		
		//Draw health
		if(i1==null){
			i1 = (new ImageIcon("images/"+sci.getItemImage("health",1))).getImage();
			i1=i1.getScaledInstance(30,30,Image.SCALE_FAST);//switch to SCALE_FAST to increase speed
		}
		
		if(health!=0)
			for(int x=0; x<health;x++){
				if(hitClock==0){	//a hit hasn't happened
					g.drawImage(i1,30+(32*x), 35,null);
				}
				else{ 		//a hit has happened, blink health until hitClock=0
					if(blink<5)
					{
						g.drawImage(i1,30+(32*x),35,null);
					}
				}
			}
		
		if(hitClock!=0)	//start up the blinking
		{	
			blink++;
			if(blink==10)
				blink=0;
		}
		
		//Draw Stars
		if(i2==null){
			i2 = (new ImageIcon("images/"+sci.getItemImage("coin",1))).getImage();
			i2=i2.getScaledInstance(30,30,Image.SCALE_FAST);//switch to SCALE_FAST to increase speed
		}
		
		g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(),25));
		g.fillRoundRect(width-130, 30,95,45, 10,10);
		//g.fillRect(width-130,30,95,45);
		g.setColor(Color.LIGHT_GRAY);
		g.drawImage(i2, width-125, 35,null);
		g.drawString("= "+starTotal,width-90,60);
		
		
		
		if(drawLevelComplete)
		{
			g.setColor(Color.BLUE);	
			g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(),35));
			g.fillRoundRect(0, height/2-100, width-1,200,50,50);

			g.setColor(Color.GREEN);
			for(int x=0; x<10;x++)
				g.drawRoundRect(0+x, height/2-100+x, width-1-2*x,200-2*x,50,50);

			g.drawString("Level Complete!!!!", width/2-150, height/2);
		}
		
		g.setColor(oldForeColor);
		//resetFontSize(g);
		
	}
	private void updateFontSize(Graphics2D g)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		//Check if we are animating the font
		if(animFont){
			this.defaultFont = g.getFont();
			int animElapsedTime = (int)(this.totalElapsedTime - this.timeStartAnim);
			//Calculate lookup into Font[] to see which font we should display
			int animIndex = animElapsedTime/animTimeScaleFactor;
			if(animIndex!=animFonts.length-1) { //not at end of animation
				g.setColor(Color.GREEN);
				g.setFont(this.animFonts[animIndex]);
			}
			else { //end of animation
				animFont=false;
			}
			
		}
		
		
	}
	
	private void resetFontSize(Graphics2D g)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		if(animFont) {
			long elapsedAnimTime = this.totalElapsedTime - this.timeStartAnim;
			
			g.setFont(this.defaultFont);	
		}
	
	}
	
	public float getMultiplier()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		return multiplier;
	}
	
	public synchronized void setMultiplier(float newMult)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		multiplier = newMult;
	}
	
	public String getScore()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		return currentScore;
	}
	public synchronized void setScore(int newScore)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		score = newScore;
		refreshScore();
	}
	public synchronized void setStarTotal(int total)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		starTotal=total;
	}
	public synchronized int getStarTotal()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		return starTotal;
	}
	public synchronized void addScore(int points)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		//Setup scoreboard animation
		animFont=true;
		this.timeStartAnim=this.totalElapsedTime;
		this.totalAnimTime = this.maxAnimTime;
		this.timeStopAnim=this.timeStartAnim + this.totalAnimTime;
		this.animTimeScaleFactor = this.totalAnimTime/this.fontIntervals;
		score += this.multiplier * points;
		refreshScore();
	}
	public synchronized void drawLevelOver()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		drawLevelComplete=true;
	}
	
	public synchronized void stopLevelOver()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		drawLevelComplete = false;
	}
	
	private void refreshScore()
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		this.currentScore = f.format(score);
	}
	

}
