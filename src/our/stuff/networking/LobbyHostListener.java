package our.stuff.networking;

import java.net.DatagramPacket;

import javax.swing.JTextArea;

import our.stuff.eventlisteners.NetworkListener;

public class LobbyHostListener extends NetworkListener
{
	
	JTextArea infoBox;
	
	public LobbyHostListener(JTextArea textBox)
	{
		infoBox = textBox;
	}
	
	@Override
	public void callback(DatagramPacket packet)
	{
		byte[] data = packet.getData();
		infoBox.append("\nConnection from " + packet.getAddress().getHostAddress());
	}
}
