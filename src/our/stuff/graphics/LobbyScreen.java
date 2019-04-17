package our.stuff.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.brackeen.javagamebook.tilegame.GameManager;

import our.stuff.eventlisteners.BackButtonListener;
import our.stuff.eventlisteners.HostButtonListener;
import our.stuff.eventlisteners.JoinButtonListener;
import our.stuff.networking.Server;

public class LobbyScreen extends JFrame
{
	
	private Server server;
	
	private JButton hostButton;
	private JButton joinButton;
	private JButton menuButton;
	
	private JPanel buttonPanel;
	private JPanel screenContainer;
	private JPanel connectPanel;
	private JPanel hostPanel;
	
	private ImageIcon icon;
	private JTextField ipBox;
	
	public LobbyScreen()
	{
		hostButton = new JButton("Host Game");
		hostButton.addActionListener(new HostButtonListener(this));
		joinButton = new JButton("Join Game");
		joinButton.addActionListener(new JoinButtonListener(this));
		menuButton = new JButton("Main Menu");
		menuButton.addActionListener(new BackButtonListener(this));
		buttonPanel = new JPanel(new GridLayout(3, 1));
		buttonPanel.add(hostButton);
		buttonPanel.add(joinButton);
		buttonPanel.add(menuButton);
		
		screenContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		screenContainer.add(buttonPanel);
		this.setSize(800,600);
		this.add(screenContainer);
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	
    	icon = new ImageIcon("images/background.png");
    	 
		JPanel panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				g.drawImage(icon.getImage(), 0, 0, null);
 				super.paintComponent(g);
			}
		};
		panel.setOpaque( false );
		panel.setPreferredSize( new Dimension(400, 400) );
 
		panel.add(screenContainer);
		this.add(panel);
	}
	
	private int state = 0;
	
	private static final int DEFAULT = 0;
	private static final int JOIN = 1;
	private static final int HOST = 2;
	
	/**
	 * Method the back button will trigger.
	 */
	public void back()
	{
		switch (state)
		{
		case DEFAULT:
			GameManager.getGameManagerInstance().setMultiScreen(false);
			state = DEFAULT;
			break;
		case JOIN:
			screenContainer.remove(connectPanel);
			buttonPanel.setVisible(true);
			state = DEFAULT;
			break;
		case HOST:
			screenContainer.remove(hostPanel);
			buttonPanel.setVisible(true);
			server.close();
			state = DEFAULT;
			break;
			
		}
		
	}
	
	/**
	 * Contains the logic for hosting a session
	 * 
	 */
	public void host()
	{
		state = HOST;
		buttonPanel.setVisible(false);
		hostPanel = new JPanel();
		JButton backButton = new JButton("Back");
		JTextArea logText = new JTextArea(10, 20);
		logText.append("Hosting game");
		logText.setMargin(new Insets(5, 5, 5, 5));
		logText.setEditable(false);
		backButton.addActionListener(new BackButtonListener(this));
		hostPanel.add(backButton);
		hostPanel.add(logText);
		
		screenContainer.add(hostPanel);
		
		server = new Server();
		server.start();
		logText.append("\nServer started on " + server.getIP().getHostAddress());
	}
	/**
	 * Initiates logic for joining a lobby
	 */
	public void join()
	{
		state = JOIN;
		buttonPanel.setVisible(false);
		connectPanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this));
		
		ipBox = new JTextField("Enter IP here",15);
		
		connectPanel.add(backButton);
		connectPanel.add(ipBox);
		connectPanel.add(connectButton);
		screenContainer.add(connectPanel);
	}
	
}
