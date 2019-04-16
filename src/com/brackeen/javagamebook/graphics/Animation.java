package com.brackeen.javagamebook.graphics;

import java.awt.Image;
import java.util.ArrayList;

import com.brackeen.javagamebook.codereflection.*;


/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/
public class Animation {

    private ArrayList frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;
    private Throwable e = new Throwable();


    /**
        Creates a new, empty Animation.
    */
    public Animation() {
        this(new ArrayList(), 0);
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    }


    private Animation(ArrayList frames, long totalDuration) {
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        this.frames = frames;
        this.totalDuration = totalDuration;
        start();
    }


    /**
        Creates a duplicate of this animation. The list of frames
        are shared between the two Animations, but each Animation
        can be animated independently.
    */
    public Object clone() {
        return new Animation(frames, totalDuration);
    }


    /**
        Adds an image to the animation with the specified
        duration (time to display the image).
    */
    public synchronized void addFrame(Image image,
        long duration)
    {
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }


    /**
        Starts this animation over from the beginning.
    */
    public synchronized void start() {
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        animTime = 0;
        currFrameIndex = 0;
    }


    /**
        Updates this animation's current image (frame), if
        neccesary.
    */
    public synchronized void update(long elapsedTime) {
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (frames.size() > 1) {
            animTime += elapsedTime;

            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;
            }
        }
    }


    /**
        Gets this Animation's current image. Returns null if this
        animation has no images.
    */
    public synchronized Image getImage() {
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }
    }


    private AnimFrame getFrame(int i) {
    	if(CodeReflection.isTracing() && GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return (AnimFrame)frames.get(i);
    }


    private class AnimFrame {

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
