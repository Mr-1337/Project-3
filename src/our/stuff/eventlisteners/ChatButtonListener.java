package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import our.stuff.networking.Client;
import our.stuff.networking.NetworkInterface;
import our.stuff.networking.NetworkManager;
import our.stuff.networking.PacketManager;
import our.stuff.networking.Server;

public class ChatButtonListener implements ActionListener
{
	
	private JTextField chat;
	private JTextArea history;
	
	public ChatButtonListener(JTextField chat, JTextArea history)
	{
		this.chat = chat;
		this.history = history;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
			String text = chat.getText();
			if (NetworkManager.GetInstance().isServer())
				text = "Server: " + text;
			if (!text.isEmpty())
			{
				NetworkManager.GetInstance().send(PacketManager.genChatPacket(text));
				if (NetworkManager.GetInstance().isServer())
					history.append(text + '\n');
			}
			chat.setText(null);
		
	}

}
