package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import com.brackeen.javagamebook.tilegame.GameManager;

import our.stuff.graphics.LobbyScreen;

public class StartButtonListener implements ActionListener
{

	private LobbyScreen screen;
	private ButtonGroup buttons;
	
	public StartButtonListener(LobbyScreen s, ButtonGroup buttons)
	{
		screen = s;
		this.buttons = buttons;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		int mode = 0;
		Enumeration<AbstractButton> elements = buttons.getElements();
		while (elements.hasMoreElements())
		{
			AbstractButton button = elements.nextElement();
			if (button.isSelected())
			{
				switch(button.getText())
				{
				case "Wave defense":
					mode = GameManager.MODE_WAVE;
					break;
				case "Race":
					mode = GameManager.MODE_RACE;
					break;
				}
			}
		}
		
		screen.startGame(mode);
		
	}

}
