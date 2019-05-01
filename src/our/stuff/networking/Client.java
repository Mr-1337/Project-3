package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

import com.brackeen.javagamebook.tilegame.GameManager;

/**
 * Represents a game client on the network
 * @author Kyan
 *
 */
public class Client extends Thread implements NetworkInterface
{
	
	private DatagramSocket socket;
	private int port = 0;
	private InetAddress serverIP = null;
	private boolean open = false;
	private NetworkListener listener;
	private GameManager gm = GameManager.getGameManagerInstance();
	
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
		this.port = port;
		serverIP = host;
		open = true;
	}
	
	@Override
	public void setCallback(NetworkListener listener)
	{
		this.listener = listener;
	}
	
	public void send(byte[] data) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, serverIP, port);
		socket.send(packet);
	}
	
	@Override
	public void run()
	{
		DatagramPacket p = new DatagramPacket(new byte[256], 256);
		try
		{
			while(open)
			{
				p.setData(new byte[256]);
				socket.receive(p);
				DatagramPacket p2 = new DatagramPacket(p.getData(), p.getLength());
				listener.callback(p2);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
