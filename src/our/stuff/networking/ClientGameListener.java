package our.stuff.networking;

import java.net.DatagramPacket;
import java.util.Arrays;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.tilegame.ResourceManager;
import com.brackeen.javagamebook.tilegame.sprites.Zombie;

public class ClientGameListener implements NetworkListener
{

	@Override
	public void callback(DatagramPacket packet)
	{
		byte[] data = packet.getData();
		byte id = data[0];
		switch (id)
		{
		case PacketManager.TYPE_SPAWN:
			GameManager g = GameManager.getGameManagerInstance();
			ResourceManager r = g.getResourceManagerInstance();
			String name = new String(Arrays.copyOfRange(data, 1, data.length));
			switch (name)
			{
			case "Ant":
				break;
			case "Balloon":
				break;
			case "Bear":
				break;
			case "Bee":
				break;
			case "Boss":
				break;
			case "Dragonfly":
				break;
			case "FireAnt":
				break;
			case "Fly":
				break;
			case "Grub":
				break;
			case "HomingFly":
				break;
			case "Monkey":
				break;
			case "Player":
				break;
			case "Raccoon":
				break;
			case "RandomFly":
				break;
			case "SinuousFly":
				break;
			case "Zombie":
				Animation a = new Animation();
		    	a.addFrame(r.loadImage("zombie.png"), 50);
				g.queueSprite(new Zombie(a, a, a, a));
				break;
			}
			break;
		case PacketManager.TYPE_PLAYERPOS:
			break;
		}
		
	}

}
