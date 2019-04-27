package our.stuff.networking;

import java.io.IOException;

import our.stuff.eventlisteners.NetworkListener;

public interface NetworkInterface
{
	/**
	 * Sends data
	 * @param data 
	 * Sends the byte array over this network interface
	 * @throws IOException 
	 */
	public void send(byte[] data) throws IOException;
	public void setCallback(NetworkListener listener);
	
}
