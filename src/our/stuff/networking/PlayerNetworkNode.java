package our.stuff.networking;

/**
 * 
 * @author Kyan
 *	A node representing some player over the network. 
 */
public class PlayerNetworkNode
{
	
	/**
	 * A representation of a player over the network.
	 */
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
