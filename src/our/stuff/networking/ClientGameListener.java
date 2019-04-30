package our.stuff.networking;

import java.net.DatagramPacket;
import java.util.Arrays;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.tilegame.ResourceManager;
import com.brackeen.javagamebook.tilegame.sprites.Zombie;

public class ClientGameListener implements NetworkListener
{

	GameManager gm = GameManager.getGameManagerInstance();
	
	@Override
	public void callback(DatagramPacket packet)
	{
		gm.queuePacket(packet);
	}

}
