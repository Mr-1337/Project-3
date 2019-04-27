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
public class Client implements NetworkInterface
{
	
	private DatagramSocket socket;
	private int port = 0;
	private InetAddress serverIP = null;
	
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
		serverIP = host;
	}
	
	public void send(byte[] data) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, serverIP, port);
		socket.send(packet);
	}
}
