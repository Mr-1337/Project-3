package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import com.brackeen.javagamebook.tilegame.GameManager;
/**
 * @author Josh Figueiredo
 */

public class TracingAbstractionLevelComboBoxListener implements ActionListener {

	private GameManager	gameManagerInstance;
	private JComboBox	TracingAbstractionLevelComboBox;
		
	public TracingAbstractionLevelComboBoxListener(JComboBox tracingComboBox)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		TracingAbstractionLevelComboBox = tracingComboBox;
	}
		
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		gameManagerInstance.setTracingAbstractionLevel(TracingAbstractionLevelComboBox.getSelectedIndex());
	}
}
