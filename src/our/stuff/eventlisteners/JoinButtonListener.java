package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import our.stuff.graphics.LobbyScreen;

public class JoinButtonListener implements ActionListener
{
	
	private LobbyScreen screen;
	
	public JoinButtonListener(LobbyScreen s)
	{
		this.screen = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		screen.join();
	}

}
