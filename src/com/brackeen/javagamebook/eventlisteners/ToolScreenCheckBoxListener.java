package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import com.brackeen.javagamebook.tilegame.GameManager;

public class ToolScreenCheckBoxListener implements ActionListener {

	private GameManager	gameManagerInstance;
	private JCheckBox	toolScreenCheckBox;
	private JCheckBox	tracingOnCheckBox;
	
	public ToolScreenCheckBoxListener(JCheckBox toolScreen)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		toolScreenCheckBox = toolScreen;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(toolScreenCheckBox.isSelected())
		{
			gameManagerInstance.setToolScreen(true);
		}
		else
		{
			gameManagerInstance.setToolScreen(false);
		}

	}

}
