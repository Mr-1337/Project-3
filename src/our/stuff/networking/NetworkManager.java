package our.stuff.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;

public class NetworkManager
{
	
    // the other players
    private LinkedList<PlayerNode> otherPlayer = new LinkedList<PlayerNode>();
	
	private Server server;
	private Client client;
	
	private NetworkInterface current;
	
	public boolean isServer()
	{
		return (current == server) && (current != null);
	}
	
	private NetworkManager()
	{
		
	}
	
	private static NetworkManager instance = null;
	
	public static NetworkManager GetInstance()
	{
		if (instance == null)
		{
			instance = new NetworkManager();
		}
		return instance;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public Server getServer()
	{
		return server;
	}
	
	public void OpenClient(InetAddress ip, int port)
	{
		try
		{
			client = new Client(ip, port);
			current = client;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void StartClient()
	{
		client.start();
	}
	
	public void OpenServer(int port)
	{
		server = new Server(port);
		current = server;
	}
	
	public void StartServer()
	{
		server.start();
	}
	
	public NetworkInterface getCurrent()
	{
		return current;
	}
	
	public void send(byte[] data)
	{
		try
		{
			current.send(data);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
