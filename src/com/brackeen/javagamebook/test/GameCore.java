package com.brackeen.javagamebook.test;

import java.awt.*;
import javax.swing.ImageIcon;
import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.graphics.ScreenManager;
/**
    Simple abstract class used for testing. Subclasses should
    implement the draw() method.
*/
public abstract class GameCore {

    protected static final int FONT_SIZE = 24;

    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(640, 480, 16, 0),
        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 24, 0),
        //new DisplayMode(1024, 768, 16, 0),
        //new DisplayMode(1024, 768, 32, 0),
        //new DisplayMode(1024, 768, 24, 0),
    };

    private boolean isRunning;
    protected ScreenManager screen;
    protected boolean fullScreen 	= false;
    protected static boolean toolScreen 	= false;
    protected boolean pauseGame 	= false;
    
    private Dimension 	screenResolution = new Dimension(800, 600);
    private int			colorDepth = 16;
    


    /**
        Signals the game loop that it's time to quit
    */
    public void stop() {
        isRunning = false;
    }


    /**
        Calls init() and gameLoop()
    */
    public void run() {
        try {
            init();
            gameLoop();
        }
        finally {
             screen.restoreScreen();
//            lazilyExit();
        }
    }

    /**
        Exits the VM from a daemon thread. The daemon thread waits
        2 seconds then calls System.exit(0). Since the VM should
        exit when only daemon threads are running, this makes sure
        System.exit(0) is only called if neccesary. It's neccesary
        if the Java Sound system is running.
    */
    public void lazilyExit() {
        Thread thread = new Thread() {
            public void run() {
                // first, wait for the VM exit on its own.
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ex) { }
                // system is still running, so force an exit
                System.exit(0);
            }
        };
        thread.setDaemon(true);
   
        	screen.restoreScreen();
        thread.start();
    }

    
    public void setScreenResolution(int width, int height)
    {
    	screenResolution.setSize(width, height);
    }
    
    public void setColorDepth(int depth)
    {
    	colorDepth = depth;
    }

    /**
        Sets full screen mode and initiates and objects.
    */
    public void init() {
        screen = new ScreenManager();
        //screenResolution = new Dimension();
        DisplayMode displayMode = 
        	new DisplayMode(screenResolution.width, screenResolution.height, colorDepth, 0);
        //    screen.findFirstCompatibleMode(POSSIBLE_MODES);
        if(fullScreen) {
        	screen.setFullScreen(displayMode);
        }
        else {
        	if(toolScreen) {
        		screen.setToolWindow(displayMode);
        	}
        	screen.setWindowedScreen(displayMode);
        }
        Window window = screen.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        window.setBackground(Color.blue);
        window.setForeground(Color.white);

        isRunning = true;
    }


    public Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    protected void togglePause()
    {
    	pauseGame = !pauseGame;
    }
    
    /**
        Runs through the game loop until stop() is called.
    */
    public void gameLoop() {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        boolean switchPaint = true;
        
        while (isRunning) {
        	
        	while(!screen.frame().isFocused())
        	{
        		try
        		{
        			screen.getGraphics().drawString("Automatic Pause", screen.frame().getWidth()/2-150, screen.frame().getHeight()/2);
        			screen.update();
        			Thread.sleep(20);
        			
        		}
        		catch(Exception e)
        		{
        		}
        	}
        	
        	while(pauseGame)
        	{
        		try
        		{
        			screen.getGraphics().drawString("Manual Pause", screen.frame().getWidth()/2-150, screen.frame().getHeight()/2);
        			screen.update();
        			Thread.sleep(20);
        		}
        		catch(Exception e)
        		{
        			
        		}
        		
        		GameManager.getGameManagerInstance().checkInput(0);		//check to see if the pause key was pressed
        	}
        	
            long elapsedTime =
                System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // update
            update(elapsedTime);

            // draw the screen
            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();
            
            // don't take a nap! run as fast as possible
            /*try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) { }*/
        }
    }


    /**
        Updates the state of the game/animation based on the
        amount of elapsed time that has passed.
    */
    public void update(long elapsedTime) {
        // do nothing
    }


    /**
        Draws to the screen. Subclasses must override this
        method.
    */
    public abstract void draw(Graphics2D g);
}
