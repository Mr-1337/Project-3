package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.brackeen.javagamebook.tilegame.GameManager;

public class ClearTraceTextButtonListener implements ActionListener {

	private GameManager	gameManagerInstance;
	
	public ClearTraceTextButtonListener()
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		gameManagerInstance.clearTraceText();
	}

}
