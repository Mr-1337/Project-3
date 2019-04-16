/*
 * Created on Nov 22, 2005
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
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InvincibilityCheckBoxListener implements ActionListener {

	private GameManager	gameManagerInstance;
	private JCheckBox	invincibleCheckBox;
	
	public InvincibilityCheckBoxListener(JCheckBox invincible)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
		invincibleCheckBox = invincible;
	}
	public void actionPerformed(ActionEvent arg0) {
			gameManagerInstance.toggleInvincibility();
		}
}