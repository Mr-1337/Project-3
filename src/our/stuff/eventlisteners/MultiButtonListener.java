package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.brackeen.javagamebook.tilegame.GameManager;

public class MultiButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
		// Go to the new multiplayer screen here
		GameManager.getGameManagerInstance().setMultiScreen(true);
	}

}
