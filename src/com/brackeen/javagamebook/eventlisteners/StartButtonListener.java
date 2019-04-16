package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.brackeen.javagamebook.tilegame.GameManager;


public class StartButtonListener implements ActionListener {

	private GameManager gameManagerInstance;
	
	public StartButtonListener()
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		gameManagerInstance.setRunGame(true);
		

	}

}
