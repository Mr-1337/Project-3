/*
 * Created on Nov 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.codereflection;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.*;  

public class Queue {

	private Vector queue;
	
	public Queue ()  
	{
        queue = new Vector();
    }

    public void enQueue (Object ob) 
    {  //add to the queue
        queue.addElement(ob);
    }

    public Object deQueue () 
    {	//remove from queue
        Object obj = null;

        if (!queue.isEmpty()) 
        {
            obj = queue.firstElement();
            queue.removeElementAt(0);
        }
        return obj;
    }

    public boolean isEmpty() 
    {	//return whether the queue is empty or not
        return queue.isEmpty();
    }

    public int size() 
    {	//return the total size of the queue
    	return queue.size();
    }

    public int roomLeft() 
    {	//return the total amount of space left on the queue
    	return (queue.capacity() - queue.size());
    }
}