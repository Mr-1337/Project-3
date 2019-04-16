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



public class MusicCheckBoxListener implements ActionListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	private GameManager	gameManagerInstance;
	private JCheckBox	musicCheckBox;

	public MusicCheckBoxListener(JCheckBox music)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		musicCheckBox 		= music;
	}
	
	public void actionPerformed(ActionEvent arg0) {
	
		gameManagerInstance.toggleMusic();

	}

}
