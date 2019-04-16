package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import com.brackeen.javagamebook.tilegame.GameManager;
/**
 * @author Josh Figueiredo
 */

public class TracingCheckBoxListener implements ActionListener {

	private GameManager	gameManagerInstance;
	private JCheckBox	TracingCheckBox;
	private JComboBox	TracingAbstractionLevelComboBox;
		
	public TracingCheckBoxListener(JCheckBox tracing, JComboBox TALComboBox)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		TracingCheckBox = tracing;
		TracingAbstractionLevelComboBox = TALComboBox;
	}
		
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(TracingCheckBox.isSelected())
		{
			gameManagerInstance.setTracing(true);
			TracingAbstractionLevelComboBox.setEnabled(true);
		}
		else
		{
			gameManagerInstance.setTracing(false);
			TracingAbstractionLevelComboBox.setEnabled(false);
		}

	}
}
