package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * The server that is responsible for maintaining the connections of all the connected game clients
 * @author Kyan
 */
public class Server extends Thread
{
	private DatagramSocket socket;
	
	private InetAddress address;
	private final int port;
	
	public int getPort()
	{
		return port;
	}
	
	public Server(int p)
	{
		super("Server Thread");
		port = p;
		try
		{
			socket = new DatagramSocket(port);
			address = socket.getLocalAddress();
			
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		DatagramPacket p = new DatagramPacket(new byte[256], 256);
		try
		{
			socket.receive(p);
			System.out.println("WE GOT A PACKET!!!");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Closes the server and releases the hosting socket
	 */
	public void close()
	{
		socket.disconnect();
		socket.close();
	}
}
