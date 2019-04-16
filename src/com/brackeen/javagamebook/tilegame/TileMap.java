package com.brackeen.javagamebook.tilegame;

import java.awt.Image;
import java.util.LinkedList;
import java.util.Iterator;

import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.codereflection.*;

/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Of course, Images are used multiple times in the tile
    map.
*/
public class TileMap {

    private Image[][] tiles;
    private LinkedList sprites;
    private Sprite player;
    private Throwable e = new Throwable();

    /**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */
    public TileMap(int width, int height) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        tiles = new Image[width][height];
        sprites = new LinkedList();
    }


    /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=5)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return tiles.length;
    }


    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=5)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return tiles[0].length;
    }


    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTile(int x, int y) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=5)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (x < 0 || x >= getWidth() ||
            y < 0 || y >= getHeight())
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }


    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        tiles[x][y] = tile;
    }


    /**
        Gets the player Sprite.
    */
    public Sprite getPlayer() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return player;
    }


    /**
        Sets the player Sprite.
    */
    public void setPlayer(Sprite player) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        this.player = player;
    }


    /**
        Adds a Sprite object to this map.
    */
    public void addSprite(Sprite sprite) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        sprites.add(sprite);
    }


    /**
        Removes a Sprite object from this map.
    */
    public void removeSprite(Sprite sprite) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        sprites.remove(sprite);
    }


    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */
    public Iterator getSprites() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return sprites.iterator();
    }

}
