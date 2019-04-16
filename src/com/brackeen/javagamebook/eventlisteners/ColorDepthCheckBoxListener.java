package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import com.brackeen.javagamebook.tilegame.GameManager;

public class ColorDepthCheckBoxListener implements ItemListener {

	private int 		colorDepth;
	private GameManager	gameManagerInstance;
	
	public ColorDepthCheckBoxListener(int depth)
	{
		colorDepth = depth;
		gameManagerInstance = GameManager.getGameManagerInstance();
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		gameManagerInstance.setColorDepth(colorDepth);
	}

}
