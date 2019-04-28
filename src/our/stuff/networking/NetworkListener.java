package our.stuff.networking;

import java.net.DatagramPacket;

/**
 * Base class for the different network listeners
 * @author Kyan
 *
 */
public interface NetworkListener
{
	public void callback(DatagramPacket packet);
}
