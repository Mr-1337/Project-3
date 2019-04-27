package our.stuff.networking;

import java.io.IOException;
import java.net.DatagramPacket;

import javax.swing.JTextArea;

import our.stuff.eventlisteners.NetworkListener;

public class LobbyHostListener extends NetworkListener
{
	
	private JTextArea infoBox, chatBox;
	
	public LobbyHostListener(JTextArea textBox, JTextArea chatBox)
	{
		infoBox = textBox;
		this.chatBox = chatBox;
	}
	
	@Override
	public void callback(DatagramPacket packet)
	{
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
			break;
		case PacketManager.TYPE_CHAT:
			chatBox.append(new String(packet.getData()));
		}
	}
}
