package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.brackeen.javagamebook.tilegame.GameManager;

public class ExitButtonListener implements ActionListener {

	private GameManager gameManagerInstance;
	
	public ExitButtonListener()
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		gameManagerInstance.setExitGame(true);
		System.exit(0);

	}

}
