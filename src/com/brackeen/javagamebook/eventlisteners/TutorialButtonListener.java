package com.brackeen.javagamebook.eventlisteners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.brackeen.javagamebook.graphics.ToolFrame;
/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TutorialButtonListener implements ActionListener {

	public TutorialButtonListener()
	{
		
	}
	public void actionPerformed(ActionEvent e)
	{
		ToolFrame.changeTutorial();
	}
	 
}
