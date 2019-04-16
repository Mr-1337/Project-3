package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.brackeen.javagamebook.graphics.OptionsMenu;

public class OptionsButtonListener implements ActionListener {

	private OptionsMenu optionsMenuInstance;
	
	public OptionsButtonListener(OptionsMenu om)
	{
		optionsMenuInstance = om;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		optionsMenuInstance.setVisible(true);
	}

}
