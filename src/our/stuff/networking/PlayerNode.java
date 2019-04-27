package our.stuff.networking;

import java.net.InetAddress;

public class PlayerNode
{
	
	private String name = "no name billy joe";
	private InetAddress ip;
	private int port;
	
	public PlayerNode(String name, InetAddress ip, int port)
	{
		this.name = name;
		this.ip = ip;
		this.port = port;
	}
	
	public String getName()
	{
		return name;
	}
	
	public InetAddress getIP()
	{
		return ip;
	}
	
	public int getPort()
	{
		return port;
	}
}
