package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.brackeen.javagamebook.tilegame.GameManager;

import our.stuff.graphics.LobbyScreen;

public class StartButtonListener implements ActionListener
{

	private LobbyScreen screen;
	
	public StartButtonListener(LobbyScreen s)
	{
		screen = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		GameManager.getGameManagerInstance().setRunGame(true);
		GameManager.getGameManagerInstance().setMultiScreen(false);
	}

}
