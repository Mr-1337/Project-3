package our.stuff.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import our.stuff.networking.Client;
import our.stuff.networking.NetworkInterface;
import our.stuff.networking.PacketManager;
import our.stuff.networking.Server;

public class ChatButtonListener implements ActionListener
{
	
	private JTextField chat;
	private JTextArea history;
	private NetworkInterface connection;
	
	public ChatButtonListener(JTextField chat, JTextArea history, NetworkInterface connection)
	{
		this.chat = chat;
		this.history = history;
		this.connection = connection;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		try
		{
			String text = chat.getText();
			if (!text.isEmpty())
			{
				connection.send(PacketManager.genChatPacket(text));
				history.append(text + '\n');
			}
			chat.setText(null);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
