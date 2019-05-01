package com.brackeen.javagamebook.tilegame;
//Test comment for github
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFormat;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.graphics.StartMenu;
import com.brackeen.javagamebook.graphics.ToolFrame;
import com.brackeen.javagamebook.input.GameAction;
import com.brackeen.javagamebook.input.InputManager;
import com.brackeen.javagamebook.sound.MidiPlayer;
import com.brackeen.javagamebook.sound.Sound;
import com.brackeen.javagamebook.sound.SoundManager;
import com.brackeen.javagamebook.test.GameCore;
import com.brackeen.javagamebook.test.ScoreBoard;
import com.brackeen.javagamebook.tilegame.sprites.Bear;
import com.brackeen.javagamebook.tilegame.sprites.Bee;
import com.brackeen.javagamebook.tilegame.sprites.Boss;
import com.brackeen.javagamebook.tilegame.sprites.Creature;
import com.brackeen.javagamebook.tilegame.sprites.Player;
import com.brackeen.javagamebook.tilegame.sprites.PowerUp;
import com.brackeen.javagamebook.tilegame.sprites.Zombie;
import com.brackeen.javagamebook.util.RandomUtil;
import com.brackeen.javagamebook.util.TimeSmoothie;

import our.stuff.graphics.LobbyScreen;
import our.stuff.graphics.Projectile;
import our.stuff.graphics.RoundCount;
import our.stuff.networking.NetworkManager;
import our.stuff.networking.PacketManager;

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
	        	case MODE_RACE:
	        		break;
	        	case MODE_COOP:
	        		gameManager.initCoop();
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
    	sem = new Semaphore(1, true);
    	sem.release();
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
    
    private Semaphore sem;
    
    private static NetworkManager networkManager = NetworkManager.GetInstance();
    
    public static final int MODE_NORMAL = 0;
    public static final int MODE_WAVE = 1;
    public static final int MODE_RACE = 2;
    public static final int MODE_COOP = 3;
    
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
    
    public void initCoop() {
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
    	CodeReflection.traceMethods(value);
    }
    
    public boolean getTracing()
    {
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
    	showFPS = fps;
    }
    
    public boolean getShowFPS()
    {
    	return showFPS;
    }
    
    public void setMusicOn(boolean music)
    {
    	MUSIC_ON = music;
    }
    
    public boolean getMusicOn()
    {
    	return MUSIC_ON;
    }
    
    public void setSoundFXOn(boolean sound)
    {
    	SOUND_ON = sound;
    }
    
    public boolean getSoundFXOn()
    {
    	return SOUND_ON;
    }
    
    public void setPlayerSpeedMultiplier(float speed)
    {
    	playerSpeedMultiplier = speed;
    }
    
    public float getPlayerSpeedMultiplier()
    {
    	return playerSpeedMultiplier;
    }
    
    public void setEnemySpeedMultiplier(float speed)
    {
    	Creature.enemySpeedMultiplier = speed;
    }

    public float getEnemySpeedMultiplier()
    {
    	return Creature.enemySpeedMultiplier;
    }
    
    public void setEnemyJumpSpeedMultiplier(float speed)
    {
    	Creature.enemyJumpSpeedMultiplier = speed;
    }
    
    public float getEnemyJumpSpeedMultiplier()
    {
    	return Creature.enemyJumpSpeedMultiplier;
    }
    
    public void setPlayerJumpSpeedMultiplier(float speed)
    {
    	Player.playerJumpSpeedMultiplier = speed;
    }
    
    public float getPlayerJumpSpeedMultiplier()
    {
    	return Player.playerJumpSpeedMultiplier;
    }
    
    public void setGravityMultiplier(float newGravityMultiplier)
    {
    	gravityMultiplier = newGravityMultiplier;
    }
    
    public float getGravityMultiplier()
    {
    	return gravityMultiplier;
    }
    
    public boolean getInvincible()
    {
    	return(INVINCIBLE);
    }
    public void setMaxHitClock(int x)
    {
    	MAX_HIT_CLOCK=x;
    }
    public int getInvulnerableTime()
    {
    	return(MAX_HIT_CLOCK);
    }
    public void setHealth(int x)
    {
    	if(x>=HEALTH_MAX)	//x exceeds maximum health, set health to max
    		health = HEALTH_MAX;
    	else if(x<=0)		//x is less than 0 (dead), set to 1
    			health = 1;
    		else health = x;	//x is fine set health to it
    }
    public int getHealth()
    {	//return the players current health
    	return(health);
    }
    
    public void setMaxHealth(int x)
    {
    	if(x<0)		//x is too small
    		HEALTH_MAX=1;
    	else HEALTH_MAX=x;	
    	
    	if(HEALTH_MAX<health)	//health excedes Maximum now, fix it
    		health=HEALTH_MAX;
    }
    
    public int getMaxHealth()
    {
    	return (HEALTH_MAX);
    }
    
    public ResourceManager getResourceManagerInstance()
    {
    	return(resourceManager);
    }
    
    public void toggleMusic()
    {
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
    	INVINCIBLE = !INVINCIBLE;
    }
    
    /**
        Closes any resources used by the GameManager.
    */
    public void stop() {
        super.stop();
        if (MUSIC_ON)
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
            }
            player.setVelocityX(velocityX);
        }

    }

    public void draw(Graphics2D g) {
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
    
    public Semaphore getSemmy()
    {
    	return sem;
    }

    private ConcurrentLinkedQueue<DatagramPacket> packetQueue = new ConcurrentLinkedQueue<DatagramPacket>();
    
    public void queuePacket(DatagramPacket packet)
    {
	    packetQueue.add(packet);
    }
    
    public void processPackets()
    {
    	while(!packetQueue.isEmpty())
    	{
			DatagramPacket packet = packetQueue.poll();
    		//DatagramPacket packet = new DatagramPacket(new byte[128], 128);
			if(packet == null)
				return;
	    	byte[] data = packet.getData();
			ByteBuffer bb = ByteBuffer.allocate(data.length);
			bb.put(data);
			byte packetType = data[0];
			switch (packetType)
			{
			case PacketManager.TYPE_SPAWN:
 				String name = new String(Arrays.copyOfRange(data, 1, data.length));
 				name = name.substring(0, name.indexOf("END"));
 				int spawnID = bb.getInt(name.length()+4);
 				System.out.println(name);
				switch (name)
				{
				case "Ant":
					break;
				case "Balloon":
					System.out.println("BALLOON");
					break;
				case "Bear":
					Bear be = (Bear) resourceManager.bear.clone();
					be.setID(spawnID);
					queueSprite(be);
					break;
				case "Bee":
					Bee b = (Bee) resourceManager.bee.clone();
					b.setID(spawnID);
					queueSprite(b);
					break;
				case "Boss":
					break;
				case "Dragonfly":
					break;
				case "FireAnt":
					break;
				case "Fly":
					break;
				case "Grub":
					break;
				case "HomingFly":
					break;
				case "Monkey":
					break;
				case "Player":
					System.out.println("i have a clone, and he's coming to kill me");
					Player p = (Player) resourceManager.playerSprite.clone();
					p.setX(100);
					p.setY(250);
					p.setID(spawnID);
					p.local = false;
					queueSprite(p);
					break;
				case "Raccoon":
					break;
				case "RandomFly":
					break;
				case "SinuousFly":
					break;
				case "Zombie":
					System.out.println("THE BIG Z");
					Animation a = new Animation();
			    	a.addFrame(resourceManager.loadImage("zombie.png"), 50);
			    	Zombie z = new Zombie(a, a, a, a);
			    	z.setX(600);
			    	z.setY(450);
					queueSprite(z);
					break;
				}
				break;
			case PacketManager.TYPE_CREATUREPOS:
				
				int posID = bb.getInt(1);
				float x = bb.getFloat(5);
				float y = bb.getFloat(9);
				System.out.println(posID);
				System.out.println(x);
				System.out.println(y);
				Iterator iter = map.getSprites();
				while (iter.hasNext())
				{
					Sprite s = (Sprite) iter.next();
					if (s instanceof Creature)
					{
						if (((Creature)s).getID() == posID)
						{
							System.out.println("MOVEY BOI");
							s.setX(x);
							s.setY(y);
							break;
						}
					}
				}
				break;
			case PacketManager.TYPE_KILL:
				
				int killID = bb.getInt(1);
				Iterator killIter = map.getSprites();
				while (killIter.hasNext())
				{
					Sprite s = (Sprite) killIter.next();
					if (s instanceof Creature)
					{
						Creature c = (Creature)s;
						if (c.getID() == killID)
						{
							c.setState(Creature.STATE_DEAD);
							break;
						}
					}
				}
			}
			if (networkManager.isServer())
			{
				//networkManager.getServer().send(packet);
			}
    	}
    }
    
    
    /**
        Updates Animation, position, and velocity of all Sprites
        in the current map.
    */
	public void update(long elapsedTime)
	{
		elapsedTime = timeSmoothie.getTime(elapsedTime);
		currentElapsedTime = elapsedTime;
		totalElapsedTime += elapsedTime;
		if (hitClock != 0) // the player was hit, give invulnerability for hitClock seconds
		{
			hitClock -= elapsedTime;
			if (hitClock < 0)
				hitClock = 0;
		}

		Creature player = (Creature) map.getPlayer();
		Graphics2D g = screen.getGraphics();

		if (networkManager.getCurrent() == null)
		{
			if (player.getY() > screen.getHeight() + player.getHeight())
			{// if the player falls out of the bottom of the screen, kill them
				player.setState(Creature.STATE_DEAD);
				midiPlayer.stop();
				soundManager.play(dieSound);
				this.baseScoreMultiplier = 1.0f;
				scoreBoard.setMultiplier(this.baseScoreMultiplier);

				totalElapsedTime = 0;

				// reset Star total
				scoreBoard.setStarTotal(0);
				try
				{
					Thread.sleep(750);
				} catch (Exception io)
				{
				}
				;
				hitClock = 0;
			}
		}

		// player is dead! start map over
		if (networkManager.getCurrent() == null)
		{
			if (player.getState() == Creature.STATE_DEAD)
			{
				health = START_HEALTH;
				map = resourceManager.reloadMap();
				try
				{
					Thread.sleep(LEVEL_SWITCH_PAUSE + 300); // give a little time before reloading the map
				} catch (Exception io)
				{
				}
				;
				if (MUSIC_ON)
				{
					midiPlayer.stop();
					midiPlayer.play(sequence, true);
				}

				return;
			}
		}

		// get keyboard/mouse input
		checkInput(elapsedTime);

		// update player
		updateCreature(player, elapsedTime);
		player.update(elapsedTime);

		switch (mode)
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
		case MODE_COOP:
			networkManager.send(PacketManager.genCreaturePosPacket(player));
			break;
		}

		try
		{
			sem.acquire();
			processPackets();
			sem.release();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// update other sprites
		while (!spriteQueue.isEmpty())
		{
			map.addSprite(spriteQueue.remove());
		}

		Iterator i = map.getSprites();
		while (i.hasNext())
		{
			Sprite sprite = (Sprite) i.next();
			if (sprite instanceof Creature)
			{
				Creature creature = (Creature) sprite;
				if (creature instanceof Player && !((Player)(creature)).local)
					continue;
				else if (creature.getState() == Creature.STATE_DEAD)
				{
					if (networkManager.getCurrent() != null)
						networkManager.send(PacketManager.genKillPacket(creature));
					i.remove();
				} else
				{
					updateCreature(creature, elapsedTime);
				}
			}
			// normal update
			sprite.update(elapsedTime);
		}
	}
    
    private Queue<Sprite> spriteQueue = new LinkedList<Sprite>();
    
    public void queueSprite(Sprite s)
    {
    	spriteQueue.add(s);
    }
    
    public void startWaveRound()
    {
    	Animation a = new Animation();
    	a.addFrame(resourceManager.loadImage("zombie.png"), 50);
    	
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

    	Player player = (Player)map.getPlayer();
        // apply gravity
    	if (networkManager.getCurrent() == null || creature == player || networkManager.isServer())
    	{
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

    }


    /**
        Checks for Player collision with other Sprites. If
        canKill is true, collisions with Creatures will kill
        them.
    */
	public void checkPlayerCollision(Player player, boolean canKill)
	{
		boolean execute = true;
		if (!player.isAlive())
		{
			return;
		}

		// check for player collision with other sprites
		Sprite collisionSprite = getSpriteCollision(player);

		if (collisionSprite instanceof PowerUp)
		{
			acquirePowerUp((PowerUp) collisionSprite);
			collisionSprite = null;
		} else if (collisionSprite instanceof Creature)
		{
			Creature badguy = (Creature) collisionSprite;
			if (canKill)
			{
				// kill the badguy and make player bounce, and increase score

				// Multiplier is equal to base level multiplier times 2^numberOfBadGuysKilled

				if (badguy instanceof Boss)
				{
					execute = false;
					((Boss) badguy).decrementHealth();
					if (((Boss) badguy).getHealth() == 0) // beat the boss, move to next leve
					{
						this.baseScoreMultiplier++;
						player.consecutiveHits++;
						scoreBoard.setMultiplier((float) (10 * player.consecutiveHits));
						scoreBoard.addScore(10);

						if (SOUND_ON)
							soundManager.play(boopSound);
						badguy.setState(Creature.STATE_DYING);
						player.setY(badguy.getY() - player.getHeight());
						player.jump(true);

						midiPlayer.stop(); // temporarily stop music

						if (SOUND_ON)
							soundManager.play(endOfLevelSound);

						scoreBoard.drawLevelOver(); // draw Level over banner

						draw(screen.getGraphics());
						screen.update();

						try
						{
							Thread.sleep(LEVEL_SWITCH_PAUSE); // give a little time before reloading the map
							// needed to avoid a sound bug
						} catch (Exception io)
						{
						}
						;

						scoreBoard.stopLevelOver(); // take down level over banner

						totalElapsedTime = 0; // reset the clock
						map = resourceManager.loadNextMap();
						renderer.setBackground(resourceManager.loadImage(resourceManager.levelBackground()));

						// change music
						if (MUSIC_ON)
						{
							sequence = midiPlayer.getSequence("sounds/" + resourceManager.levelMusic());
							midiPlayer.play(sequence, true);
						}
						execute = false;
					} else
					{
						if (SOUND_ON)
							soundManager.play(boopSound);

						player.consecutiveHits++;
						scoreBoard.setMultiplier((float) (10 * player.consecutiveHits));
						scoreBoard.addScore(10);

						player.setY(badguy.getY() - player.getHeight());
						badguy.setState(Creature.STATE_HURT);

						player.jump(true);
					}
				}

				if (execute && !(badguy instanceof Projectile))
				{
					if (SOUND_ON)
						soundManager.play(boopSound);
					if (!badguy.isHelper())
					{// if it's a helper, don't do the following
						badguy.decrementHealth();
						if (badguy.getHealth() == 0)
						{ // The Bad Guy is dead
							badguy.setState(Creature.STATE_DYING);
						} else
						{ // The Bad Guy is hurt, but still alive
							badguy.setState(Creature.STATE_HURT);
						}
						player.consecutiveHits++;
						scoreBoard.setMultiplier((float) (10 * player.consecutiveHits));
						scoreBoard.addScore(10);
					}
					player.setY(badguy.getY() - player.getHeight());
					player.jump(true);
				}
				execute = true;
			} else
			{
				if ((hitClock == 0) && (!badguy.isHelper()))
				{
					if (!INVINCIBLE) // If you're not invincible...
						if (health == 0)
						{ // player has run out of health, he will die
							// player dies!
							if (MUSIC_ON)
								midiPlayer.stop();

							if (SOUND_ON)
								soundManager.play(dieSound);
							player.setState(Creature.STATE_DYING);

							// reset score multipliers
							this.baseScoreMultiplier = 1.0f;
							scoreBoard.setMultiplier(this.baseScoreMultiplier);

							player.consecutiveHits = 0;
							totalElapsedTime = 0;

							// reset Star total
							scoreBoard.setStarTotal(0);
						} else
						{
							hitClock = MAX_HIT_CLOCK;
							if (SOUND_ON)
								soundManager.play(hurtSound);
							health--; // deduct from players health
						}
				}
			}
		}
	}


    /**
        Gives the player the specified power up and removes it
        from the map.
    */
    public void acquirePowerUp(PowerUp powerUp) {  	
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
        	
        	if (MUSIC_ON)
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
        	if(MUSIC_ON)
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
