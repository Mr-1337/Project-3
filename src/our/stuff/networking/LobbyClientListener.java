package our.stuff.networking;

import java.net.DatagramPacket;
import java.util.Arrays;

import javax.swing.JTextArea;

import our.stuff.eventlisteners.NetworkListener;
import our.stuff.graphics.LobbyScreen;

public class LobbyClientListener implements NetworkListener
{
	
	private JTextArea chatHistory;
	private LobbyScreen screen;
	
	public LobbyClientListener(JTextArea chatHistory, LobbyScreen screen)
	{
		this.chatHistory = chatHistory;
		this.screen = screen;
	}

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
		case PacketManager.TYPE_ACCEPT:
			screen.joinLobby();
			break;
		case PacketManager.TYPE_START:
			byte mode = data[1];
			screen.startClientGame(mode);
			break;
		}
		
	}
}
