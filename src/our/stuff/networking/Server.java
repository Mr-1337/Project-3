package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

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
	private NetworkListener listener;
	
	private LinkedList<PlayerNode> players = new LinkedList<PlayerNode>();
	
	public int getPort()
	{
		return port;
	}
	
	public PlayerNode getPlayerFromIp(InetAddress ip)
	{
		for (PlayerNode p : players)
		{
			if (p.getIP() == ip)
				return p;
		}
		return null;
	}
	
	public void addPlayer(InetAddress ip, int playerPort)
	{
		String name = "Player " + (players.size() + 1);
		PlayerNode player = new PlayerNode(name, ip, playerPort);
		players.add(player);
	}
	
	public LinkedList<PlayerNode> getPlayers()
	{
		return players;
	}
	
	public void addPlayer(PlayerNode player)
	{
		players.add(player);
	}
	
	public Server(int p)
	{
		super("Server Thread");
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
	public void setCallback(NetworkListener listener)
	{
		this.listener = listener;
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
				listener.callback(p);
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

	/**
	 * Sends a packet to all players. Use for synchronization stuff;
	 * @param data
	 * The data to be sent
	 */
	@Override
	public void send(byte[] data)
	{
		System.out.println("Sending packet to clients");
		for (PlayerNode n : players)
		{
			DatagramPacket p = new DatagramPacket(data, data.length);
			p.setAddress(n.getIP());
			p.setPort(n.getPort());
			try
			{
				socket.send(p);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends a packet to a specific player.
	 * @param data
	 * The data to be sent
	 * @param player
	 * The player to send the data to
	 */
	public void sendToPlayer(byte[] data, PlayerNode player)
	{
		DatagramPacket p = new DatagramPacket(data, data.length);
		p.setAddress(player.getIP());
		p.setPort(player.getPort());
		try
		{
			socket.send(p);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
