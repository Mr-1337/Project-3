package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Arrays;

import javax.swing.JTextArea;

import our.stuff.eventlisteners.NetworkListener;

public class LobbyHostListener implements NetworkListener
{
	
	private JTextArea infoBox, chatBox;
	private Server server;
	
	public LobbyHostListener(JTextArea textBox, JTextArea chatBox, Server server)
	{
		infoBox = textBox;
		this.server = server;
		this.chatBox = chatBox;
	}
	
	@Override
	public void callback(DatagramPacket packet)
	{
		packet.setLength(packet.getOffset() + packet.getLength()); 
		byte[] data = packet.getData();
	    StringBuilder sb = new StringBuilder();
	    for (byte b : data) {
	        sb.append(String.format("%02X ", b));
	    }
	    System.out.println(sb.toString());
		System.out.println();
		switch (data[0])
		{
		case PacketManager.TYPE_PING:
			break;
		case PacketManager.TYPE_CONNECT:
			infoBox.append("\nConnection from " + packet.getAddress().getHostAddress());
			server.addPlayer(packet.getAddress(), packet.getPort());
			break;
		case PacketManager.TYPE_CHAT:
			String message = new String(Arrays.copyOfRange(data, 1, data.length)) + '\n';
			chatBox.append(message);
			break;
		}
	}
}
