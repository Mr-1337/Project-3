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
	
	public InetAddress getIP()
	{
		return address;
	}
	
	public Server()
	{
		super("Server Thread");
		try
		{
			socket = new DatagramSocket(25565);
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
		socket.close();
	}
}
