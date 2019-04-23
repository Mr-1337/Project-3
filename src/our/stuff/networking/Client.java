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
	 * @param port
	 * The port of the game host
	 * @throws IOException 
	 */
	public Client(InetAddress host, int port) throws IOException
	{
		socket = new DatagramSocket();
		byte[] data = new byte[64];
		DatagramPacket packet = new DatagramPacket(data, 64, host, port);
		socket.send(packet);
	}
}
