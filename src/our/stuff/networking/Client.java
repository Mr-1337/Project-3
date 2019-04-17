package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Represents a game client on the network
 * @author Kyan
 *
 */
public class Client
{
	
	private DatagramSocket socket;
	
	/**
	 * Creates a client and attempts a UDP connection
	 * @param host
	 * The IP address of the game host
	 * @throws IOException 
	 */
	public Client(InetAddress host) throws IOException
	{
		socket = new DatagramSocket();
		byte[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		DatagramPacket packet = new DatagramPacket(data, 10, host, 25565);
		socket.send(packet);
	}
}
