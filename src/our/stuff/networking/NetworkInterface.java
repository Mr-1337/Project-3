package our.stuff.networking;

import java.io.IOException;

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
