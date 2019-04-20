package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import our.stuff.graphics.LobbyScreen;

public class ConnectButtonListener implements ActionListener
{
	
	private LobbyScreen lobby;
	
	public ConnectButtonListener(LobbyScreen s)
	{
		lobby = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		lobby.connect();
	}

}
