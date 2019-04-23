package com.brackeen.javagamebook.tilegame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFormat;

import com.brackeen.javagamebook.util.*;

import our.stuff.graphics.LobbyScreen;
import our.stuff.graphics.RoundCount;

import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.sound.*;
import com.brackeen.javagamebook.input.*;
import com.brackeen.javagamebook.test.*;
import com.brackeen.javagamebook.tilegame.sprites.*;

import com.brackeen.javagamebook.codereflection.*;

/**
    GameManager manages all parts of the game.
*/
public class GameManager extends GameCore {
	private boolean showFPS=true;
	
    public static void main(String[] args) {
    	
    	startMenu = new StartMenu();
    	lobbyScreen = new LobbyScreen();
    	startMenu.setVisible(true);
    	
    	
    	
    
    	while(!exitGame)
    	{
	        try
	        {
	        	Thread.sleep(20);
	        }
	        catch(Exception e)
	        {
	        	
	        }
	       
	        if(runGame)
	        {
	        	startMenu.setVisible(false);
	        	System.out.println("Starting to run");
	        	switch (mode)
	        	{
	        	case MODE_NORMAL:
	        		gameManager.run();
	        		break;
	        	case MODE_WAVE:
	        		gameManager.initWave();
	        		gameManager.gameLoop();
	        		break;
	        			
	        	}
	        	
	        	runGame = false;
	        	
	        	if(toolScreen)
	        	{
	        		ToolFrame.getToolFrameInstance().setVisible(false);
	        	}
	        	
	        	startMenu.setVisible(true);
	        }
	        else if(multiScreen)
	        {
	        	lobbyScreen.setLocation(startMenu.getLocation());
	        	lobbyScreen.setVisible(true);
	        	startMenu.setVisible(false);
	        	
	        	while (multiScreen)
	        	{
	        		try
	        		{
	        			Thread.sleep(1);
	        		} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	
	        	startMenu.setLocation(lobbyScreen.getLocation());
	        	lobbyScreen.setVisible(false);
	        	startMenu.setVisible(true);
	        }
	        	
	        
    	}
    	startMenu.setVisible(false);
    }
    
    private GameManager()
    {
    
    }

    public static GameManager getGameManagerInstance()
    {
    	return(gameManager);
    }
    
    // the gamemode we will launch
    
    private static int mode = 0;
    public void setMode(int m)
    {
    	mode = m;
    }
    
    public static final int MODE_NORMAL = 0;
    public static final int MODE_WAVE = 1;
    public static final int MODE_RACE = 2;
    
    private static GameManager gameManager = new GameManager();
    
    // uncompressed, 44100Hz, 16-bit, mono, signed, little-endian
    private static final AudioFormat PLAYBACK_FORMAT =
        new AudioFormat(22050, 16, 1, true , false);
    
    private static final int DRUM_TRACK = 1;
    
    private int START_HEALTH=3;		//health you start a level with
    private int LEVEL_SWITCH_PAUSE=2100; //amount of time to wait between levels
    private int MAX_HIT_CLOCK=2000;	//amount of invulnerability time after getting hit
    private int HEALTH_MAX=10;		//total amount of health a player can have
    private boolean MUSIC_ON = true; //by default, have the music on
    private boolean SOUND_ON = true;	//by default, have the sound on
    private boolean INVINCIBLE = false;	//by default the player is not invincible
    
    private static final float GRAVITY = 0.002f;
    private float gravityMultiplier = 1.0f;
    
    private float playerSpeedMultiplier = 1.0f;
    
    private int health=0;

    private Point pointCache = new Point();
    private TileMap map;
    private MidiPlayer midiPlayer;
    private SoundManager soundManager;
    private ResourceManager resourceManager;
    
    private Sound starSound;
    private Sound boopSound;
    private Sound noteSound;
    private Sound warpSound;
    private Sound endOfLevelSound;
    private Sound dieSound;
    private Sound healthSound;
    private Sound hurtSound;
    private Sound roundSound;
    
    private InputManager inputManager;
    private TileMapRenderer renderer;
    
    private TimeSmoothie timeSmoothie;
    private long totalElapsedTime = 0;
    private long currentElapsedTime = 0;
    private long hitClock = 0;
    
    private static int MILIS_PER_SECOND = 1000;
    private float currentFPS=0.0f;
    
    private float baseScoreMultiplier=1.0f;
    
    private ScoreBoard scoreBoard = new ScoreBoard();
    private RoundCount roundCount;

    private GameAction moveLeft;
    private GameAction moveRight;
    private GameAction jump;
    private GameAction exit;
    private GameAction pause;

    private static StartMenu startMenu;
    private static LobbyScreen lobbyScreen;
    private static boolean runGame = false;
    private static boolean multiScreen = false;
    private static boolean exitGame = false;
    private Throwable e = new Throwable();
    
  
    
    private Sequence sequence;

    public void init() {
        super.init();
        
        health=START_HEALTH;
        // set up input manager
        initInput();

        // start resource manager
      
        resourceManager = new ResourceManager(
        screen.getFullScreenWindow().getGraphicsConfiguration());

        // load resources
        renderer = new TileMapRenderer();
        renderer.setBackground(
            resourceManager.loadImage(resourceManager.levelBackground()));
        // load first map
        map = resourceManager.loadNextMap();

        // load sounds
        soundManager = new SoundManager(PLAYBACK_FORMAT);
        starSound = soundManager.getSound("sounds/"+resourceManager.getStarSound());
        boopSound = soundManager.getSound("sounds/"+resourceManager.getBoopSound());
        noteSound = soundManager.getSound("sounds/"+resourceManager.getNoteSound());
        warpSound = soundManager.getSound("sounds/"+resourceManager.getWarpSound());
        endOfLevelSound = soundManager.getSound("sounds/"+resourceManager.getEndOfLevelSound());
        dieSound = soundManager.getSound("sounds/"+resourceManager.getDieSound());
        healthSound = soundManager.getSound("sounds/"+resourceManager.getHealthSound());
        hurtSound = soundManager.getSound("sounds/"+resourceManager.getHurtSound());
        
        // start music
        if(MUSIC_ON){
        	midiPlayer = new MidiPlayer();
        	sequence =
        		midiPlayer.getSequence("sounds/"+resourceManager.levelMusic());
        	midiPlayer.play(sequence, true);
        	//toggleDrumPlayback();
        }
        
        //Time smoothie
        timeSmoothie = new TimeSmoothie();
    }
    
    public void initWave()
    {
    	super.init();
    	health=START_HEALTH;
        // set up input manager
        initInput();

        // start resource manager
        
        ScriptManager.getScriptManagerInstance().setLevelMappingFile("G1Maps.spt");
        ScriptManager.rebuildInstance();
        
        resourceManager = new ResourceManager(
        screen.getFullScreenWindow().getGraphicsConfiguration());

        // load resources

        renderer = new TileMapRenderer();
        renderer.setBackground(
            resourceManager.loadImage(resourceManager.levelBackground()));
        // load first map
        map = resourceManager.loadNextMap();

        // load sounds
        soundManager = new SoundManager(PLAYBACK_FORMAT);
        starSound = soundManager.getSound("sounds/"+resourceManager.getStarSound());
        boopSound = soundManager.getSound("sounds/"+resourceManager.getBoopSound());
        noteSound = soundManager.getSound("sounds/"+resourceManager.getNoteSound());
        warpSound = soundManager.getSound("sounds/"+resourceManager.getWarpSound());
        endOfLevelSound = soundManager.getSound("sounds/"+resourceManager.getEndOfLevelSound());
        dieSound = soundManager.getSound("sounds/"+resourceManager.getDieSound());
        healthSound = soundManager.getSound("sounds/"+resourceManager.getHealthSound());
        hurtSound = soundManager.getSound("sounds/"+resourceManager.getHurtSound());
        roundSound = soundManager.getSound("sounds/"+resourceManager.getRoundSound());
        
        roundCount = new RoundCount(roundSound, soundManager);
        
        // start music
        if(MUSIC_ON){
        	midiPlayer = new MidiPlayer();
        	sequence =
        		midiPlayer.getSequence("sounds/"+resourceManager.levelMusic());
        	//midiPlayer.play(sequence, true);
        	//toggleDrumPlayback();
        }
        
        //Time smoothie
        timeSmoothie = new TimeSmoothie();
        roundCount.increment();
    }

    public void setRunGame(boolean value)
    {
    	runGame = value;
    }
    
    public void setMultiScreen(boolean value)
    {
    	multiScreen = value;
    }
    
    public void setExitGame(boolean value)
    {
    	exitGame = value;
    }
    
    public void setFullScreen(boolean value)
    {
    	this.fullScreen = value;
    }
    
    public void setToolScreen(boolean value)
    {
    	toolScreen = value;
    }
    
    public void setTracing(boolean value)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	CodeReflection.traceMethods(value);
    }
    
    public boolean getTracing()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return CodeReflection.isTracing();
    }
    
    public void setTracingAbstractionLevel(int value)
    {
    	CodeReflection.setAbstractionLevel(value);
    }
    
    public int getTracingAbstractionLevel()
    {
    	return CodeReflection.getAbstactionLevel();
    }
    
    public void clearTraceText()
    {
    	ToolFrame.getToolFrameInstance().clearTextArea();
    }
    
    public void setShowFPS(boolean fps)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	showFPS = fps;
    }
    
    public boolean getShowFPS()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return showFPS;
    }
    
    public void setMusicOn(boolean music)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	MUSIC_ON = music;
    }
    
    public boolean getMusicOn()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return MUSIC_ON;
    }
    
    public void setSoundFXOn(boolean sound)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	SOUND_ON = sound;
    }
    
    public boolean getSoundFXOn()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return SOUND_ON;
    }
    
    public void setPlayerSpeedMultiplier(float speed)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	playerSpeedMultiplier = speed;
    }
    
    public float getPlayerSpeedMultiplier()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return playerSpeedMultiplier;
    }
    
    public void setEnemySpeedMultiplier(float speed)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	Creature.enemySpeedMultiplier = speed;
    }

    public float getEnemySpeedMultiplier()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return Creature.enemySpeedMultiplier;
    }
    
    public void setEnemyJumpSpeedMultiplier(float speed)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	Creature.enemyJumpSpeedMultiplier = speed;
    }
    
    public float getEnemyJumpSpeedMultiplier()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return Creature.enemyJumpSpeedMultiplier;
    }
    
    public void setPlayerJumpSpeedMultiplier(float speed)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	Player.playerJumpSpeedMultiplier = speed;
    }
    
    public float getPlayerJumpSpeedMultiplier()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return Player.playerJumpSpeedMultiplier;
    }
    
    public void setGravityMultiplier(float newGravityMultiplier)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	gravityMultiplier = newGravityMultiplier;
    }
    
    public float getGravityMultiplier()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return gravityMultiplier;
    }
    
    public boolean getInvincible()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(INVINCIBLE);
    }
    public void setMaxHitClock(int x)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	MAX_HIT_CLOCK=x;
    }
    public int getInvulnerableTime()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(MAX_HIT_CLOCK);
    }
    public void setHealth(int x)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(x>=HEALTH_MAX)	//x exceeds maximum health, set health to max
    		health = HEALTH_MAX;
    	else if(x<=0)		//x is less than 0 (dead), set to 1
    			health = 1;
    		else health = x;	//x is fine set health to it
    }
    public int getHealth()
    {	//return the players current health
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(health);
    }
    
    public void setMaxHealth(int x)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(x<0)		//x is too small
    		HEALTH_MAX=1;
    	else HEALTH_MAX=x;	
    	
    	if(HEALTH_MAX<health)	//health excedes Maximum now, fix it
    		health=HEALTH_MAX;
    }
    
    public int getMaxHealth()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return (HEALTH_MAX);
    }
    
    public ResourceManager getResourceManagerInstance()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(resourceManager);
    }
    
    public void toggleMusic()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(MUSIC_ON)	//the music is on, shut it off
    	{
    		midiPlayer.close();
    		MUSIC_ON = false;
    	}else{ 			//the music is off, turn it on
            // start music
            midiPlayer = new MidiPlayer();
            sequence =
                midiPlayer.getSequence("sounds/"+resourceManager.levelMusic());
            midiPlayer.play(sequence, true);
            MUSIC_ON=true;
    	}
    }
    
    public void toggleInvincibility()
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	INVINCIBLE = !INVINCIBLE;
    }
    
    /**
        Closes any resources used by the GameManager.
    */
    public void stop() {
        super.stop();
        midiPlayer.close();
        soundManager.close();
        scoreBoard.setStarTotal(0);
        this.baseScoreMultiplier=1.0f;
		scoreBoard.setMultiplier(this.baseScoreMultiplier);
		scoreBoard.setScore(0);
		totalElapsedTime = 0;
    }


    private void initInput() {
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        jump = new GameAction("jump",
            GameAction.DETECT_INITAL_PRESS_ONLY);
        exit = new GameAction("exit",
            GameAction.DETECT_INITAL_PRESS_ONLY);
        pause = new GameAction("pause",
        		GameAction.DETECT_INITAL_PRESS_ONLY);

        inputManager = new InputManager(
            screen.getFullScreenWindow());
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);
    }
    
    public void checkInput(long elapsedTime) {

    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (exit.isPressed()) {
            stop();
        }

        if(pause.isPressed()){
        	this.togglePause();
        }
        
        Player player = (Player)map.getPlayer();
        if (player.isAlive()) {
            float velocityX = 0;
            if (moveLeft.isPressed()) {
                velocityX-=(player.getMaxSpeed() * playerSpeedMultiplier);
            }
            if (moveRight.isPressed()) {
                velocityX+=(player.getMaxSpeed() * playerSpeedMultiplier);
            }
            if (jump.isPressed()) {
                player.jump(false);
                if (mode == MODE_WAVE)
                	roundCount.increment();
            }
            player.setVelocityX(velocityX);
        }

    }

    public void draw(Graphics2D g) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	Font temp;
        renderer.draw(g, map,
            screen.getWidth(), screen.getHeight());
        
        //Draw the FPS counter at bottom right of screen
        if(showFPS){
        	temp = g.getFont();
        	g.setFont(new Font(temp.getFontName(),temp.getStyle(),10));
        	g.drawString("FPS: " + this.timeSmoothie.getFrameRate(), screen.getWidth()-100, screen.getHeight()-10);
        	g.setFont(temp);
        }
        
        //pass in totalElapsedTime and currentElapsedTime
        
        switch (mode)
        {
        case MODE_NORMAL:
        	scoreBoard.draw(g, screen.getWidth(),screen.getHeight(), totalElapsedTime, resourceManager.getLevel(), health, hitClock, resourceManager.getScriptClass());
        	break;
        case MODE_WAVE:
        	roundCount.draw(g);
        	break;
        }
    }
        


    /**
        Gets the current map.
    */
    public TileMap getMap() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return map;
    }


    /**
        Turns on/off drum playback in the midi music (track 1).
    
    public void toggleDrumPlayback() {
        Sequencer sequencer = midiPlayer.getSequencer();
        if (sequencer != null) {
                 sequencer.setTrackMute(DRUM_TRACK,
                !sequencer.getTrackMute(DRUM_TRACK));
        }
    }
*/

    /**
        Gets the tile that a Sprites collides with. Only the
        Sprite's X or Y should be changed, not both. Returns null
        if no collision is detected.
    */
    public Point getTileCollision(Sprite sprite,
        float newX, float newY)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        float fromX = Math.min(sprite.getX(), newX);
        float fromY = Math.min(sprite.getY(), newY);
        float toX = Math.max(sprite.getX(), newX);
        float toY = Math.max(sprite.getY(), newY);

        // get the tile locations
        int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
        int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
        int toTileX = TileMapRenderer.pixelsToTiles(
            toX + sprite.getWidth() - 1);
        int toTileY = TileMapRenderer.pixelsToTiles(
            toY + sprite.getHeight() - 1);

        // check each tile for a collision
        for (int x=fromTileX; x<=toTileX; x++) {
            for (int y=fromTileY; y<=toTileY; y++) {
                if (x < 0 || x >= map.getWidth() ||
                    map.getTile(x, y) != null)
                {
                    // collision found, return the tile
                    pointCache.setLocation(x, y);
                    return pointCache;
                }
            }
        }

        // no collision found
        return null;
    }


    /**
        Checks if two Sprites collide with one another. Returns
        false if the two Sprites are the same. Returns false if
        one of the Sprites is a Creature that is not alive.
    */
    public synchronized boolean isCollision(Sprite s1, Sprite s2) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=5)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // if the Sprites are the same, return false
        if (s1 == s2) {
            return false;
        }

        // if one of the Sprites is a dead Creature, return false
        if (s1 instanceof Creature && !((Creature)s1).isAlive()) {
            return false;
        }
        if (s2 instanceof Creature && !((Creature)s2).isAlive()) {
            return false;
        }

        // get the pixel location of the Sprites
        int s1x = Math.round(s1.getX());
        int s1y = Math.round(s1.getY());
        int s2x = Math.round(s2.getX());
        int s2y = Math.round(s2.getY());

        // check if the two sprites' boundaries intersect
        return (s1x < s2x + s2.getWidth() &&
            s2x < s1x + s1.getWidth() &&
            s1y < s2y + s2.getHeight() &&
            s2y < s1y + s1.getHeight());
    }


    /**
        Gets the Sprite that collides with the specified Sprite,
        or null if no Sprite collides with the specified Sprite.
    */
    public synchronized Sprite getSpriteCollision(Sprite sprite) {

    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // run through the list of Sprites
        Iterator i = map.getSprites();
        boolean found = false;
        Sprite otherSprite=null;
       
        while (i.hasNext()&& !found) {
            otherSprite = (Sprite)i.next();
            
            if (isCollision(sprite, otherSprite)) {
                // collision found, return the Sprite
                found = true; //break from loop
            }
        }
               
        if(found)
        	return(otherSprite);
        return null;	//no collision, return null
    }


    /**
        Updates Animation, position, and velocity of all Sprites
        in the current map.
    */
    public void update(long elapsedTime) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	
    	
    	// smooth out the elapsed time
    	elapsedTime = timeSmoothie.getTime(elapsedTime);
    	currentElapsedTime = elapsedTime;
    	totalElapsedTime += elapsedTime;
    	if(hitClock!=0)		//the player was hit, give invulnerability for hitClock seconds
    	{
    		hitClock-=elapsedTime;
    		if(hitClock<0)
    			hitClock=0;
    	}
    	
        Creature player = (Creature)map.getPlayer();
        Graphics2D g = screen.getGraphics();

        if(player.getY()>screen.getHeight()+player.getHeight()) 
        {//if the player falls out of the bottom of the screen, kill them
                player.setState(Creature.STATE_DEAD);
                midiPlayer.stop();
                soundManager.play(dieSound);
                this.baseScoreMultiplier=1.0f;
        		scoreBoard.setMultiplier(this.baseScoreMultiplier);
        		
        		totalElapsedTime = 0;
        		
        		//reset Star total
        		scoreBoard.setStarTotal(0);
            	try{
                	Thread.sleep(750);	
                }catch(Exception io){};
                hitClock=0;
        }                    
        

        // player is dead! start map over
        if (player.getState() == Creature.STATE_DEAD) {
        	health=START_HEALTH;
        	map = resourceManager.reloadMap();
            try{
            	Thread.sleep(LEVEL_SWITCH_PAUSE+300);	//give a little time before reloading the map
            }catch(Exception io){};
            if(MUSIC_ON){
            	midiPlayer.stop();
            	midiPlayer.play(sequence, true);
            }
            
            return;
        }

        // get keyboard/mouse input
        checkInput(elapsedTime);

        // update player
        updateCreature(player, elapsedTime);
        player.update(elapsedTime);
               
        switch(mode)
        {
        case MODE_NORMAL:
        	break;
        case MODE_WAVE:
        	roundCount.update(elapsedTime);
        	if (roundCount.isNewRound())
        	{
        		startWaveRound();
        	}
        	break;
        case MODE_RACE:
        	break;
        }

        // update other sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite)i.next();
            if (sprite instanceof Creature) {
                Creature creature = (Creature)sprite;
                if (creature.getState() == Creature.STATE_DEAD) {
                    i.remove();
                }
                else {
                    updateCreature(creature, elapsedTime);
                }
            }
            // normal update
            sprite.update(elapsedTime);
        }
    }
    
    public void startWaveRound()
    {
    	Animation a = new Animation();
    	a.addFrame(resourceManager.loadImage("zombie1.png"), 50);
    	
    	for (int i = 0; i < (int)Math.floor(0.15 * roundCount.getRound() * 24); i++)
    	{    		
	    	Zombie zombo = new Zombie(a, a, a, a);
	    	zombo.setX(RandomUtil.getRandomInt(128, 8000));
	    	zombo.setY(450);
	    	map.addSprite(zombo);
    	}
    }
    

    /**
        Updates the creature, applying gravity for creatures that
        aren't flying, and checks collisions.
    */    
    private void updateCreature(Creature creature,
        long elapsedTime)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}

    	Player player = (Player)map.getPlayer();
        // apply gravity
        if ((!creature.isFlying())&&(creature.getState()!=Creature.STATE_HURT)) {
            creature.setVelocityY(creature.getVelocityY() +
                GRAVITY * gravityMultiplier * elapsedTime);
        }

        
        if(creature.isTrackingPlayer()) 
        {
        	if((creature.isIntelligent())/*&&(Math.abs(creature.getX()-player.getX())<200)*/
        			&&(!player.isOnGround()))
        	{
        		if(player.getX()<creature.getX())
        		{
        			creature.setVelocityX(creature.getMaxSpeed()*2);
        		}else
        			creature.setVelocityX(-creature.getMaxSpeed()*2);
        	}else
        	{
	        	if(creature.getX()>player.getX()+creature.getHorizontalResponceTime() && creature.getVelocityX()>0
	        			||creature.getX()<player.getX()-creature.getHorizontalResponceTime() && creature.getVelocityX()<0)
	        	{
	        		// Swap direction so we can continue following the player
	        		creature.setVelocityX(-creature.getVelocityX());
	        	}
	        	//If this creature is flying we must also change the y velocity
	        	if((creature.isFlying()))
	            	if(creature.getY()>player.getY()+creature.getVerticalResponceTime() && creature.getVelocityY()>0
	            			||creature.getY()<player.getY()-creature.getVerticalResponceTime() && creature.getVelocityY()<0)
	            	{
	            		// Swap direction so we can continue following the player
	            		creature.setVelocityY(-creature.getVelocityY());
	            	}
	        }
        }
        
        // change x
        float dx = creature.getVelocityX();
        float oldX = creature.getX();
        float newX = oldX + dx * elapsedTime;
        Point tile =
            getTileCollision(creature, newX, creature.getY());
        if (tile == null) {
            creature.setX(newX);
        }
        else {
            // line up with the tile boundary
            if (dx > 0) {
                creature.setX(
                    TileMapRenderer.tilesToPixels(tile.x) -
                    creature.getWidth());
            }
            else if (dx < 0) {
                creature.setX(
                    TileMapRenderer.tilesToPixels(tile.x + 1));
            }
            creature.collideHorizontal();
        }

        // change y

        float dy = creature.getVelocityY();
        float oldY = creature.getY();
        float newY = oldY + dy * elapsedTime;
        //See if we need to hunt down player

        tile = getTileCollision(creature, creature.getX(), newY);
        if (tile == null) {
            creature.setY(newY);
        }
        else {
            // line up with the tile boundary
            if (dy > 0) {
                creature.setY(
                    TileMapRenderer.tilesToPixels(tile.y) -
                    creature.getHeight());
            }
            else if (dy < 0) {
                creature.setY(
                    TileMapRenderer.tilesToPixels(tile.y + 1));
            }
            creature.collideVertical();
        }
        
        if (creature instanceof Player) {
            boolean canKill = (oldY < creature.getY()-0.5);
            Player pl = (Player)creature;
            checkPlayerCollision(pl, canKill);
            
            //check if player is on the ground if so, set score multiplier to base
            //and set consecutive bad guy kills to false
            if(pl.isOnGround()){
                pl.consecutiveHits = 0;
                scoreBoard.setMultiplier(this.baseScoreMultiplier);	
            }
        }

    }


    /**
        Checks for Player collision with other Sprites. If
        canKill is true, collisions with Creatures will kill
        them.
    */
    public void checkPlayerCollision(Player player,
        boolean canKill)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	boolean execute=true;
        if (!player.isAlive()) {
            return;
        }

        // check for player collision with other sprites
        Sprite collisionSprite = getSpriteCollision(player);
        
        if (collisionSprite instanceof PowerUp) {
            acquirePowerUp((PowerUp)collisionSprite);
            collisionSprite=null;
        }
        else if (collisionSprite instanceof Creature) {
            Creature badguy = (Creature)collisionSprite;
            if (canKill) {
                // kill the badguy and make player bounce, and increase score
            	
            	//Multiplier is equal to base level multiplier times 2^numberOfBadGuysKilled
            	if(badguy instanceof Boss){
            		execute=false;
            		((Boss)badguy).decrementHealth();
            		if(((Boss)badguy).getHealth()==0) //beat the boss, move to next leve
            		{
            			this.baseScoreMultiplier++;
                		player.consecutiveHits++;
                		scoreBoard.setMultiplier((float) (10*player.consecutiveHits));
                		scoreBoard.addScore(10);
                		
            			if(SOUND_ON)
            				soundManager.play(boopSound);
                		badguy.setState(Creature.STATE_DYING);
                		player.setY(badguy.getY() - player.getHeight());
                		player.jump(true);
                		
                    	midiPlayer.stop();	//temporarily stop music
                    	
                    	if(SOUND_ON)
                    		soundManager.play(endOfLevelSound);
                    	
                        
                        scoreBoard.drawLevelOver();	//draw  Level over banner
                        
                        draw(screen.getGraphics());
                        screen.update();
            			
                        
                        try{
                        	Thread.sleep(LEVEL_SWITCH_PAUSE);	//give a little time before reloading the map
                        						//needed to avoid a sound bug
                        }catch(Exception io){};
            			
                        scoreBoard.stopLevelOver();	//take down level over banner
                        
                        
                        totalElapsedTime=0;	//reset the clock
                        map = resourceManager.loadNextMap();
                        renderer.setBackground(resourceManager.loadImage(resourceManager.levelBackground()));
              
                        //change music
                        if(MUSIC_ON){
                        sequence =
                            midiPlayer.getSequence("sounds/"+resourceManager.levelMusic());
                        midiPlayer.play(sequence, true);
                        }
                        execute=false;
            		}else
            		{
            			if(SOUND_ON)
            				soundManager.play(boopSound);
                		
            	
            			player.consecutiveHits++;
                		scoreBoard.setMultiplier((float) (10*player.consecutiveHits));
                		scoreBoard.addScore(10);
                		
            			player.setY(badguy.getY() - player.getHeight());
            			badguy.setState(Creature.STATE_HURT);
            			
                		player.jump(true);
            		}
            	}
            	
            	if(execute)
            	{	
            		if(SOUND_ON)
            			soundManager.play(boopSound);
            		if(!badguy.isHelper()){//if it's a helper, don't do the following
            			badguy.decrementHealth();
            			if(badguy.getHealth()==0)
            			{	//The Bad Guy is dead
            				badguy.setState(Creature.STATE_DYING);
            			}else
		        		{	//The Bad Guy is hurt, but still alive
		        			badguy.setState(Creature.STATE_HURT);
		        		}
            			player.consecutiveHits++;
            			scoreBoard.setMultiplier((float) (10*player.consecutiveHits));
            			scoreBoard.addScore(10);
            		}
        			player.setY(badguy.getY() - player.getHeight());
        			player.jump(true);
            	}
            	execute=true;
            }
            else {
            	if((hitClock==0)&&(!badguy.isHelper())){
            		if(!INVINCIBLE)	//If you're not invincible...
	            	if(health==0)
	            	{	//player has run out of health, he will die
	            		// player dies!
	            		midiPlayer.stop();
	            		
	            		if(SOUND_ON)
	            			soundManager.play(dieSound);
	            		player.setState(Creature.STATE_DYING);
	                
	            		//reset score multipliers
	            		this.baseScoreMultiplier=1.0f;
	            		scoreBoard.setMultiplier(this.baseScoreMultiplier);
	            	
	            		player.consecutiveHits=0;
	            		totalElapsedTime = 0;
	            		
	            		//reset Star total
	            		scoreBoard.setStarTotal(0);
	            	}else 
	            		{ 
	            			hitClock=MAX_HIT_CLOCK;
	            			if(SOUND_ON)
	            				soundManager.play(hurtSound);
	            			health--;	//deduct from players health
	            		}
            	}
            }
        }
    }


    /**
        Gives the player the speicifed power up and removes it
        from the map.
    */
    public void acquirePowerUp(PowerUp powerUp) {  	
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // remove it from the map
        map.removeSprite(powerUp);

        if (powerUp instanceof PowerUp.Star) {
            scoreBoard.addScore(5);
            if(scoreBoard.getStarTotal()==99)
            {//if you collect 99 stars, add to health
            	
            	if(SOUND_ON)
            		soundManager.play(healthSound);
            	scoreBoard.setStarTotal(0);
            	if(health!=HEALTH_MAX)
            		health++;
            }
            else{
            	scoreBoard.setStarTotal(scoreBoard.getStarTotal()+1);
            	if(SOUND_ON)
            		soundManager.play(starSound);
            }
        }
        else if (powerUp instanceof PowerUp.Music) {
            // change the music
        	if(SOUND_ON)
        		soundManager.play(noteSound);
        	map.getPlayer().setX(map.getPlayer().getX()+4000);
            //toggleDrumPlayback();
        }
        else if (powerUp instanceof PowerUp.Health)
        {
        	if(SOUND_ON)
        		soundManager.play(healthSound);
        	if(health != HEALTH_MAX)
        		health++;
        }
        else if (powerUp instanceof PowerUp.Goal) {
            // advance to next map
        
        	Player player = (Player)map.getPlayer();
        	player.setY(1000);			//move to arbitrary place; fix collsion issue with heart (two levels forward)
        	this.baseScoreMultiplier++;
        	
        	midiPlayer.stop();	//temporarily stop music
        	
        	scoreBoard.drawLevelOver();	//draw  Level over banner
            
            draw(screen.getGraphics());
            screen.update();
            
            if(SOUND_ON){
            	soundManager.play(endOfLevelSound);
            }
            
            try{
            	Thread.sleep(LEVEL_SWITCH_PAUSE);	//give a little time before reloading the map
            										//needed to avoid a sound bug
            }catch(Exception io){};
			
            soundManager.clearQueue();	//Clear any sounds left behind
            
            totalElapsedTime=0;	//reset the clock
            map = null;
            map = resourceManager.loadNextMap();
            
            scoreBoard.stopLevelOver();		//stop drawing level over banner
            
            renderer.setBackground(resourceManager.loadImage(resourceManager.levelBackground()));
  
     
            //change music
            if(MUSIC_ON){
            sequence =
                midiPlayer.getSequence("sounds/"+resourceManager.levelMusic());
            midiPlayer.play(sequence, true);
            }
            
        }
        else if (powerUp instanceof PowerUp.Warp) {
            // advance to warp map
        	Player player = (Player)map.getPlayer();
        	player.setY(1000); //looks like player went "in"
        	this.baseScoreMultiplier++;
        	midiPlayer.stop();		//temporarily stop music
            
        	//TODO have scoreboard print warp banner :example level over banner
        	draw(screen.getGraphics());
        	screen.update();
        	
        	if(SOUND_ON)
        		soundManager.play(warpSound);
            //change background
            
        	try{
            	Thread.sleep(LEVEL_SWITCH_PAUSE);	//give a little time before loading the map
            						//needed to avoid a sound bug
            }catch(Exception io){};
        	
            soundManager.clearQueue();	//clear any left over sounds
            
            totalElapsedTime=0;	//reset the clock
            map = resourceManager.loadWarpMap(((PowerUp.Warp)powerUp).getWarpValue());
            
            renderer.setBackground(resourceManager.loadImage(resourceManager.levelBackground()));
  
     
            //change music
            if(MUSIC_ON){
            sequence =
                midiPlayer.getSequence("sounds/"+resourceManager.levelMusic());
            midiPlayer.play(sequence, true);
            }
         
        }
    }
}
