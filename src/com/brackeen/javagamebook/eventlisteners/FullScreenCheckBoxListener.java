package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import com.brackeen.javagamebook.tilegame.GameManager;

public class FullScreenCheckBoxListener implements ActionListener {

	private GameManager	gameManagerInstance;
	private JCheckBox	fullScreenCheckBox;
	private JCheckBox	toolScreenCheckBox;
	
	public FullScreenCheckBoxListener(JCheckBox fullScreen, JCheckBox toolScreen)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		fullScreenCheckBox = fullScreen;
		toolScreenCheckBox = toolScreen;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(fullScreenCheckBox.isSelected())
		{
			gameManagerInstance.setFullScreen(true);
			toolScreenCheckBox.setSelected(false);
			toolScreenCheckBox.setEnabled(false);
		}
		else
		{
			gameManagerInstance.setFullScreen(false);
			toolScreenCheckBox.setEnabled(true);
		}

	}

}
