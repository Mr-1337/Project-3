package our.stuff.networking;

/**
 * A node representing some player over the network. 
 * @author Kyan
 */
public class PlayerNetworkNode
{
	
	public PlayerNetworkNode()
	{
		
	}
	
	private String name = "noname";
	private int ping = -1;
	
	public String getName()
	{
		return name;
	}
	
	public int getPing()
	{
		return ping;
	}

}
