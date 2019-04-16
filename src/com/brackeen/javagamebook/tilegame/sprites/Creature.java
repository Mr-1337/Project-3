package com.brackeen.javagamebook.tilegame.sprites;

import java.lang.reflect.Constructor;
import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.codereflection.*;

/**
    A Creature is a Sprite that is affected by gravity and can
    die. It has four Animations: moving left, moving right,
    dying on the left, and dying on the right.
*/
public abstract class Creature extends Sprite {

    /**
        Amount of time to go from STATE_DYING to STATE_DEAD.
    */
    private static final int DIE_TIME = 1000;
    private static final int HURT_TIME = 300;
    
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DYING = 1;
    public static final int STATE_DEAD = 2;
    public static final int STATE_HURT = 3;
    
    public static float enemySpeedMultiplier		= 1.0f;
    public static float enemyJumpSpeedMultiplier	= 1.0f;
    
    private Animation left;
    private Animation right;
    private Animation deadLeft;
    private Animation deadRight;
    private int state;
    private long stateTime;
    protected Throwable e = new Throwable();
    
    protected long totalElapsedTime = 0;
    protected float jumpSpeed = -.95f;
    protected boolean flying=false; 
    protected boolean trackPlayer=false;
    protected boolean helper;
    protected boolean intelligent = false;
    
    protected int health;
    
    protected float horizontalResponceTime = 25.5f;
    protected float verticalResponceTime = 25.5f;
    
    protected boolean onGround;
    
    public boolean isOnGround()
    {
    	return onGround;
    }
    /**
        Creates a new Creature with the specified Animations.
    */
    public Creature(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(right);
        
        this.left = left;
        this.right = right;
        this.deadLeft = deadLeft;
        this.deadRight = deadRight;
        state = STATE_NORMAL;
        health = 1;
        helper = false;
    }


    public Object clone() {
        // use reflection to create the correct subclass
        Constructor constructor = getClass().getConstructors()[0];
        try {
            return constructor.newInstance(new Object[] {
                (Animation)left.clone(),
                (Animation)right.clone(),
                (Animation)deadLeft.clone(),
                (Animation)deadRight.clone()
            });
        }
        catch (Exception ex) {
            // should never happen
            ex.printStackTrace();
            return null;
        }
    }


    /**
        Gets the maximum speed of this Creature.
    */
    public float getMaxSpeed() {    
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0;
    }


    /**
        Wakes up the creature when the Creature first appears
        on screen. Normally, the creature starts moving left.
    */
    public void wakeUp() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (getState() == STATE_NORMAL && getVelocityX() == 0) {
            setVelocityX(-getMaxSpeed());
        }
    }


    /**
        Gets the state of this Creature. The state is either
        STATE_NORMAL, STATE_DYING, or STATE_DEAD.
    */
    public int getState() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return state;
    }


    /**
        Sets the state of this Creature to STATE_NORMAL,
        STATE_DYING, or STATE_DEAD.
    */
    public void setState(int state) {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (this.state != state) {
            this.state = state;
            stateTime = 0;
            if ((state == STATE_DYING)||(state == STATE_HURT)) {
                setVelocityX(0);
                setVelocityY(0);
            }
        }
    }


    /**
        Checks if this creature is alive.
    */
    public boolean isAlive() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=5)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return (state == STATE_NORMAL);
    }


    /**
        Checks if this creature is flying.
    */
    public boolean isFlying() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return flying;
    }

/**
    Checks if this creature is intelligent.
*/
public boolean isIntelligent() {
	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
    	if(CodeReflection.getAbstactionLevel()>=4)
    	{//check to make sure it's this level of abstraction
    		e.fillInStackTrace();		
    		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
    								e.getStackTrace()[0].getMethodName());
    	}
	}
    return intelligent;
}
    
    /**
     * Checks if this creature is tracking the player
     * 
     */
    public boolean isTrackingPlayer() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return this.trackPlayer;
    }

    /**
        Called before update() if the creature collided with a
        tile horizontally.
    */
    public void collideHorizontal() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        setVelocityX(-getVelocityX());
    }


    /**
        Called before update() if the creature collided with a
        tile vertically.
    */
    public void collideVertical() {
        // check if collided with ground
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (getVelocityY() > 0 && !flying) 
        {
            onGround = true;
        }
        setVelocityY(0);
    }


    /**
        Updates the animaton for this creature.
    */
    public void update(long elapsedTime) {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=5)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // select the correct Animation
        Animation newAnim = anim;
        if (getVelocityX() < 0) {
            newAnim = left;
        }
        else if (getVelocityX() > 0) {
            newAnim = right;
        }
        if ((state == STATE_DYING || state == STATE_HURT) && newAnim == left) {
            newAnim = deadLeft;
        }
        else if ((state == STATE_DYING || state == STATE_HURT) && newAnim == right) {
            newAnim = deadRight;
        }

        // update the Animation
        if (anim != newAnim) {
            anim = newAnim;
            anim.start();
        }
        else {
            anim.update(elapsedTime);
        }

        // update to "dead" state
        stateTime += elapsedTime;
        if (state == STATE_DYING && stateTime >= DIE_TIME) {
        		setState(STATE_DEAD);
        }
        else if(state == STATE_HURT && stateTime >= HURT_TIME)
        {
        	setState(STATE_NORMAL);
        }
        //update total time
        totalElapsedTime += elapsedTime;
     
    }
    

    /**
        Makes the creature jump if the player is on the ground or
        if forceJump is true.
    */
    public void jump(boolean forceJump) {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if ((onGround || forceJump) && isAlive()) {
            onGround = false;
            setVelocityY(jumpSpeed * enemyJumpSpeedMultiplier);
        }
    }
    /**
	    Makes the creature jump if the creature is on the ground.	  
	*/
	public void jump() {
	    jump(false);
	}

    public void setY(float y) {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // check if falling
        if (Math.round(y) > Math.round(getY())) {
            onGround = false;
        }
        super.setY(y);
    }
    
    public float getHorizontalResponceTime()
    {//Return the reaction time of a creature
     //for tracking purposes, how long before it takes to turn around
    	return horizontalResponceTime;
    }
    
    public void setHorizontalResponceTime(float time)
    {//Set the reaction time for this creature
     //for tracking purposes, how long before it takes to turn around
    	horizontalResponceTime = time;
    }

    public float getVerticalResponceTime()
    {//Return the reaction time of a creature
     //for tracking purposes, how long before it takes to turn around
    	return verticalResponceTime;
    }
    
    public void setVerticalResponceTime(float time)
    {//Set the reaction time for this creature
     //for tracking purposes, how long before it takes to turn around
    	verticalResponceTime = time;
    }
    
	public int getHealth()
	{
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		return health;
	}
	
	public void decrementHealth()
	{
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		health--;
	}
	
	public void setHealth(int x)
	{
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		health=x;
	}
	
	public boolean isHelper()
	{//Returns whether the creature is a helper or an enemy
		return helper;
	}
}
