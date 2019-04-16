/*
 * Created on Nov 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import com.brackeen.javagamebook.tilegame.GameManager;

/**
 * @author U_CDavidson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SoundFXCheckBoxListener implements ActionListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	private GameManager	gameManagerInstance;
	private JCheckBox	soundFXCheckBox;
	
	public SoundFXCheckBoxListener(JCheckBox soundfx)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		soundFXCheckBox = soundfx;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if(soundFXCheckBox.isSelected())
		{
			gameManagerInstance.setSoundFXOn(true);
		}
		else
		{
			gameManagerInstance.setSoundFXOn(false);
		}
	}

}
