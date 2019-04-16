/*
 * Created on Dec 12, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.TextField;

import com.brackeen.javagamebook.tilegame.ScriptManager;

/**
 * @author U_CDavidson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadLevelSetButtonListener implements ActionListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	private ScriptManager 	scriptManagerInstance;
	private TextField		levelSetTextField;
	
	public LoadLevelSetButtonListener(TextField levelSet)
	{
		scriptManagerInstance = ScriptManager.getScriptManagerInstance();
		levelSetTextField = levelSet;
	}
	
	public void actionPerformed(ActionEvent arg0) {

		scriptManagerInstance.setLevelMappingFile(levelSetTextField.getText());
		ScriptManager.rebuildInstance();
	}

}
