package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import com.brackeen.javagamebook.tilegame.GameManager;

public class ResolutionCheckBoxListener implements ItemListener {

	private int			resolutionWidth;
	private int			resolutionHeight;
	private GameManager	gameManagerInstance;
	
	public ResolutionCheckBoxListener(int width, int height)
	{
		resolutionWidth = width;
		resolutionHeight = height;
		gameManagerInstance = GameManager.getGameManagerInstance();
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		gameManagerInstance.setScreenResolution(resolutionWidth, resolutionHeight);
	}

}
