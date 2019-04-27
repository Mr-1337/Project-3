package our.stuff.networking;

import java.net.DatagramPacket;
import java.util.Arrays;

import javax.swing.JTextArea;

import our.stuff.eventlisteners.NetworkListener;

public class LobbyClientListener implements NetworkListener
{
	
	private JTextArea chatHistory;

	@Override
	public void callback(DatagramPacket packet)
	{
		byte[] data = packet.getData();
		int id = data[0];
		
		switch(id)
		{
		case PacketManager.TYPE_CHAT:
			String message = new String(Arrays.copyOfRange(data, 1, data.length)) + '\n';
			chatHistory.append(message);
			break;
		}
		
	}

	public void setChatHistory(JTextArea chatHistory)
	{
		this.chatHistory = chatHistory;
	}
}
