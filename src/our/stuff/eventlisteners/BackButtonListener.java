package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.brackeen.javagamebook.tilegame.GameManager;

import our.stuff.graphics.LobbyScreen;

public class BackButtonListener implements ActionListener
{

	private LobbyScreen screen;
	
	public BackButtonListener(LobbyScreen s)
	{
		this.screen = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		screen.back();
	}

}
