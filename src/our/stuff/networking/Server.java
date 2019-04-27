package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import our.stuff.eventlisteners.NetworkListener;

/**
 * The server that is responsible for maintaining the connections of all the connected game clients
 * @author Kyan
 */
public class Server extends Thread implements NetworkInterface
{
	private DatagramSocket socket;
	
	private InetAddress address;
	private final int port;
	private boolean open = false;
	private NetworkListener callback;
	
	public int getPort()
	{
		return port;
	}
	
	public Server(int p, NetworkListener callback)
	{
		super("Server Thread");
		this.callback = callback;
		port = p;
		try
		{
			socket = new DatagramSocket(port);
			address = socket.getLocalAddress();
			open = true;
			
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		DatagramPacket p = new DatagramPacket(new byte[64], 64);
		try
		{
			while(open)
			{
				p.setData(new byte[64]);
				socket.receive(p);
				callback.callback(p);
			}
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
		open = false;
		socket.close();
	}

	@Override
	public void send(byte[] data)
	{
		System.out.println("Sending packet to clients");
	}
}
