package com.brackeen.javagamebook.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.lang.Thread;
import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.tilegame.ScriptManager;
import com.brackeen.javagamebook.eventlisteners.*;
/**
 * @authors: Josh Figueiredo, 
 */

public class ToolFrame extends JFrame implements ChangeListener, Runnable{
	
	private JTabbedPane tabbedPane;
	private TextArea 	textArea;
	
	private JPanel 		panel;
	private JPanel 		tutorialPanel;
	private JPanel		tbPanel;
	private JPanel		physicsPanel;
	private JPanel		healthPanel;
	private JPanel		updatePanel;
	private JPanel		soundPanel;
	private JPanel		tracingPanel;
	private JPanel		packageTracePanel;
	private JPanel		codeExecutionPanel;
	
	private TextField	gravityTextField;
	private	TextField	playerSpeedTextField;
	private TextField	playerJumpSpeedTextField;
	private TextField	enemySpeedTextField;
	private TextField	enemyJumpSpeedTextField;
	
	private TextField	playerHealthTextField;
	private TextField	playerMaxHealthTextField;
	private TextField	invulnerableTimeTextField;
	
	private JCheckBox	musicCheckBox;
	private JCheckBox	soundFXCheckBox;
	private JCheckBox	invincibleCheckBox;
	private JCheckBox	tracingCheckBox;
	
	private JCheckBox	graphicsPackageTracingEnabledCheckBox;
	private JCheckBox	inputPackageTracingEnabledCheckBox;
	private JCheckBox	soundPackageTracingEnabledCheckBox;
	private JCheckBox	testPackageTracingEnabledCheckBox;
	private JCheckBox	tilegamePackageTracingEnabledCheckBox;
	private JCheckBox	spritesPackageTracingEnabledCheckBox;
	private JCheckBox	utilPackageTracingEnabledCheckBox;

	
	private JComboBox	tracingAbstractionLevelComboBox;
	
	private JButton		updateButton;
	private JButton		tutorialButton;
	private JButton		clearTraceTextButton;
	
	private static Thread 		tutorialPaintThread;
	private String 		executionString;
	private boolean 	newString = false;
	
	private static String tutorialString;
	
	private ScriptManager sci = ScriptManager.getScriptManagerInstance();
    
    //Get the image of the player and scale it down
    private Image i1 = (new ImageIcon("images/"+sci.getSpriteImage(1))).getImage();
    
	private static ToolFrame 	toolFrameSingleton;
	
	public static ToolFrame getToolFrameInstance()
	{
		if(toolFrameSingleton == null)
		{
			toolFrameSingleton = new ToolFrame();
	        tutorialPaintThread = new Thread(toolFrameSingleton);
	        tutorialPaintThread.start();
		}
		
		return toolFrameSingleton;
	}
	
	private ToolFrame() {
		setSize(600, 600);
		addWindowListener(new WindowAdapter()
            {  public void windowClosing(WindowEvent e)
               {  
            	//	System.exit(0);
               }
            } );

         tabbedPane = new JTabbedPane();
         tabbedPane.addChangeListener(this);
         textArea = new TextArea();
         this.textArea.setRows(300);         

         /* we set the components to null and delay their
            loading until the tab is shown for the first time
         */

         
 
         gravityTextField = new TextField();
         gravityTextField.setSize(10, 60);
         gravityTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getGravityMultiplier()));
         
         playerSpeedTextField = new TextField();
         playerSpeedTextField.setSize(10, 60);
         playerSpeedTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getPlayerSpeedMultiplier()));
         
         playerJumpSpeedTextField = new TextField();
         playerJumpSpeedTextField.setSize(10, 60);
         playerJumpSpeedTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getPlayerJumpSpeedMultiplier()));
         
         enemySpeedTextField = new TextField();
         enemySpeedTextField.setSize(10,60);
         enemySpeedTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getEnemySpeedMultiplier()));
         
         enemyJumpSpeedTextField = new TextField();
         enemyJumpSpeedTextField.setSize(10,60);
         enemyJumpSpeedTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getEnemyJumpSpeedMultiplier()));
         
         playerHealthTextField = new TextField();
         playerHealthTextField.setSize(10, 60);
         playerHealthTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getHealth()));
         
         playerMaxHealthTextField = new TextField();
         playerMaxHealthTextField.setSize(10, 60);
         playerMaxHealthTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getMaxHealth()));
         
         invulnerableTimeTextField = new TextField();
         invulnerableTimeTextField.setSize(10,60);
         invulnerableTimeTextField.setText(String.valueOf(GameManager.getGameManagerInstance().getInvulnerableTime()/1000));
         
         invincibleCheckBox = new JCheckBox("Invincible");
         invincibleCheckBox.setSelected(GameManager.getGameManagerInstance().getInvincible());
         invincibleCheckBox.addActionListener(new InvincibilityCheckBoxListener(invincibleCheckBox));
         
         musicCheckBox = new JCheckBox("Play Music");
         musicCheckBox.setSelected(GameManager.getGameManagerInstance().getMusicOn());
         musicCheckBox.addActionListener(new MusicCheckBoxListener(musicCheckBox));
         
         soundFXCheckBox = new JCheckBox("Play Sound FX");
         soundFXCheckBox.setSelected(GameManager.getGameManagerInstance().getSoundFXOn());
         soundFXCheckBox.addActionListener(new SoundFXCheckBoxListener(soundFXCheckBox));
         
         
         tracingAbstractionLevelComboBox = new JComboBox();
         tracingAbstractionLevelComboBox.addItem("0 - Minimal");
         tracingAbstractionLevelComboBox.addItem("1 - Constructors");
         tracingAbstractionLevelComboBox.addItem("2 - Some Methods");
         tracingAbstractionLevelComboBox.addItem("3 - More Methods");
         tracingAbstractionLevelComboBox.addItem("4 - Even More Methods");
         tracingAbstractionLevelComboBox.addItem("5 - Maximum");
         tracingAbstractionLevelComboBox.setEnabled(GameManager.getGameManagerInstance().getTracing());
         tracingAbstractionLevelComboBox.addActionListener(new TracingAbstractionLevelComboBoxListener(tracingAbstractionLevelComboBox));
 
         tracingCheckBox = new JCheckBox("Tracing");
         tracingCheckBox.setSelected(GameManager.getGameManagerInstance().getTracing());
         tracingCheckBox.addActionListener(new TracingCheckBoxListener(tracingCheckBox, tracingAbstractionLevelComboBox));
         
         graphicsPackageTracingEnabledCheckBox = new JCheckBox("graphics Package");
         graphicsPackageTracingEnabledCheckBox.setSelected(true);
         graphicsPackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(graphicsPackageTracingEnabledCheckBox, 1));
         inputPackageTracingEnabledCheckBox = new JCheckBox("input Package");
         inputPackageTracingEnabledCheckBox.setSelected(true);
         inputPackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(inputPackageTracingEnabledCheckBox, 2));
         soundPackageTracingEnabledCheckBox = new JCheckBox("sound Package");
         soundPackageTracingEnabledCheckBox.setSelected(true);
         soundPackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(soundPackageTracingEnabledCheckBox, 3));
         testPackageTracingEnabledCheckBox = new JCheckBox("test Package");
         testPackageTracingEnabledCheckBox.setSelected(true);
         testPackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(testPackageTracingEnabledCheckBox, 4));
         tilegamePackageTracingEnabledCheckBox = new JCheckBox("tilegame Package");
         tilegamePackageTracingEnabledCheckBox.setSelected(true);
         tilegamePackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(tilegamePackageTracingEnabledCheckBox, 5));
         spritesPackageTracingEnabledCheckBox = new JCheckBox("sprites Package");
         spritesPackageTracingEnabledCheckBox.setSelected(true);
         spritesPackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(spritesPackageTracingEnabledCheckBox, 6));
         utilPackageTracingEnabledCheckBox = new JCheckBox("util Package");
         utilPackageTracingEnabledCheckBox.setSelected(true);
         utilPackageTracingEnabledCheckBox.addActionListener(new PackageTracingEnabledCheckBoxListener(utilPackageTracingEnabledCheckBox, 7));
     	
         updateButton = new JButton("update");
         updateButton.addActionListener(new UpdateButtonListener());
         
         clearTraceTextButton = new JButton("clear text");
         clearTraceTextButton.addActionListener(new ClearTraceTextButtonListener());
         
         physicsPanel = new JPanel();
         physicsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Physics"));
         physicsPanel.setLayout(new GridLayout(5,2));
         physicsPanel.add(new JLabel("Gravity Multiplier"));
         physicsPanel.add(gravityTextField);
         physicsPanel.add(new JLabel("Player Speed Multiplier"));
         physicsPanel.add(playerSpeedTextField);
         physicsPanel.add(new JLabel("Player Jump Speed Multiplier"));
         physicsPanel.add(playerJumpSpeedTextField);
         physicsPanel.add(new JLabel("Enemy Speed Multiplier"));
         physicsPanel.add(enemySpeedTextField);
         physicsPanel.add(new JLabel("Enemy Jump Speed Multiplier"));
         physicsPanel.add(enemyJumpSpeedTextField);
         
         updatePanel = new JPanel();
         updatePanel.add(updateButton);
         
         healthPanel = new JPanel();
         healthPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Health"));
         healthPanel.setLayout(new GridLayout(4,2));
         healthPanel.add(new JLabel("Player Health"));
         healthPanel.add(playerHealthTextField);
         healthPanel.add(new JLabel("Max Player Health"));
         healthPanel.add(playerMaxHealthTextField);
         healthPanel.add(new JLabel("Invulnerability Time after time (sec)"));
         healthPanel.add(invulnerableTimeTextField);
         healthPanel.add(invincibleCheckBox);
         healthPanel.add(new JPanel());
         
   
         soundPanel = new JPanel();
         soundPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Sound"));
         soundPanel.setLayout(new GridLayout(2,1));
         soundPanel.add(musicCheckBox);
         soundPanel.add(soundFXCheckBox);
         
         tracingPanel = new JPanel();
         tracingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Tracing"));
         tracingPanel.add(tracingCheckBox);
         tracingPanel.add(tracingAbstractionLevelComboBox);
         tracingPanel.add(clearTraceTextButton);
         
         panel = new JPanel(new GridLayout(4,0));
         panel.add(physicsPanel);
         //panel.add(centerPanel);
         panel.add(healthPanel);
         panel.add(updatePanel);
         panel.add(soundPanel);
         //panel.add(tracingPanel);
         
         packageTracePanel = new JPanel();
         //packageTracePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Package Selection"));
         packageTracePanel.add(graphicsPackageTracingEnabledCheckBox);
         packageTracePanel.add(inputPackageTracingEnabledCheckBox);
         packageTracePanel.add(soundPackageTracingEnabledCheckBox);
         packageTracePanel.add(testPackageTracingEnabledCheckBox);
         packageTracePanel.add(tilegamePackageTracingEnabledCheckBox);
         packageTracePanel.add(spritesPackageTracingEnabledCheckBox);
         packageTracePanel.add(utilPackageTracingEnabledCheckBox);
         
         tutorialPanel = new JPanel();
         tbPanel = new JPanel();
         
         tutorialPanel.setLayout((new BorderLayout()));
         
         tutorialButton = new JButton("Next");
         tutorialButton.addActionListener(new TutorialButtonListener());
        
         tbPanel.add(tutorialButton);
         tutorialPanel.add(BorderLayout.SOUTH,tbPanel);
         tbPanel.setBackground(Color.LIGHT_GRAY);
         //Add button
         
         JPanel totalTracePanel = new JPanel();
         totalTracePanel.setLayout(new GridLayout(2,1));
         totalTracePanel.add(tracingPanel);
         totalTracePanel.add(packageTracePanel);
         
         codeExecutionPanel = new JPanel();
         codeExecutionPanel.setLayout(new BorderLayout());
         codeExecutionPanel.add(this.textArea, BorderLayout.CENTER);
         codeExecutionPanel.add(totalTracePanel, BorderLayout.SOUTH);
       
                  
         tabbedPane.add("Tutorial", this.tutorialPanel);
         tabbedPane.add("Code Execution", codeExecutionPanel);
         tabbedPane.add("On-The-Fly Options", this.panel);
         //tabbedPane.add("Package Trace Selection", this.packageTracePanel);
        

         getContentPane().add(tabbedPane, "Center");
         
         
         this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        

      }

	public static void changeTutorial()
	{
		tutorialString = ScriptManager.nextTutorialMessage();
	}
	
      public void stateChanged(ChangeEvent event) {  
    	  JTabbedPane pane = (JTabbedPane)event.getSource();
    	  
         // check if this tab still has a null component
/*
         if (pane.getSelectedComponent() == null)
         {  // set the component to the image icon

            int n = pane.getSelectedIndex();
            String title = pane.getTitleAt(n);
            ImageIcon planetIcon = new ImageIcon(title + ".gif");
            pane.setComponentAt(n, new JLabel(planetIcon));

            // indicate that this tab has been visited--just for fun

            pane.setIconAt(n, new ImageIcon("red-ball.gif"));
         }
*/
      }
      
      public void setTracingAbstractionLevelComboBoxEnabled(boolean value)
      {
    	  tracingAbstractionLevelComboBox.setEnabled(value);
      }
      
      public float getGravityMultiplierValue()
      {
      		return Float.parseFloat(gravityTextField.getText());
      }
      
      public float getPlayerSpeedValue()
      {
      		return Float.parseFloat(playerSpeedTextField.getText());
      }
      
      public float getPlayerJumpSpeedValue()
      {
    	  return Float.parseFloat(playerJumpSpeedTextField.getText());
      }
      
      public float getEnemySpeedValue()
      {
    	  return Float.parseFloat(enemySpeedTextField.getText());
      }
      
      public float getEnemyJumpSpeedValue()
      {
    	  return Float.parseFloat(enemyJumpSpeedTextField.getText());
      }
      
      public int getPlayerHealthValue()
      {
    	  return Integer.parseInt(playerHealthTextField.getText());
      }
      
      public int getPlayerMaxHealthValue()
      {
    	  return Integer.parseInt(playerMaxHealthTextField.getText());
      }
      
      public int getInvulnerableTimeValue()
      {//return the Invulnerable Time as enter by the player
      	return (Integer.parseInt(invulnerableTimeTextField.getText())*1000);
      }
      
      public void writeText(String str) {
    	  executionString = str;
    	  newString = true;
    	  //this.textArea.append(str + "\n");
      }
      
      public void clearTextArea()
      {
    	  this.textArea.setText("");
      }
      
      public void printToCodeExecution(String s)
      {
			  this.textArea.append(s + "\n");
      }
      
      public void run()
      {
      	while(true){
      	paintTutorial();
      	try{
      	Thread.sleep(500);
      	}catch(Exception io){};
      	}
      	
      }
      
      public void paintTutorial()
      { 
      	int x1 = 0, x2;
      	int nextline=0;
      	boolean finished = false;
      	if (tabbedPane.getSelectedComponent() == tutorialPanel){
      	//if the tabbed pane has focus
      		String sub;
      		String sub2;
      		Graphics g = tutorialPanel.getGraphics();
      		tutorialPanel.setBackground(Color.LIGHT_GRAY);
      		int width = tutorialPanel.getWidth();
      		int height = tutorialPanel.getHeight();
      		if(g!=null){
      			g.drawImage(i1, width-i1.getWidth(null)-5,height-i1.getHeight(null)-5, null);
      			g.setColor(Color.GREEN);
      		    g.fillRoundRect(0, 0,width ,height-i1.getHeight(null)-10,50,50);
      			g.setColor(Color.BLUE);
      			g.fillRoundRect(0+5, 0+5,width-10 ,height-i1.getHeight(null)-10-10,50,50);
      		    if(tutorialString == null)
      		    	changeTutorial();//prime it
      		    g.setColor(Color.GREEN);
      		    sub = tutorialString;
      		    
      		    Font previous = g.getFont();
      		    g.setFont(new Font(g.getFont().getFontName(),Font.BOLD,15));
      		    while(!finished)
      		    {
      		    	if((x2=sub.indexOf('\n',x1))!=-1)
      		    	{
      		    			g.drawString(sub.substring(x1,x2),30,30+nextline);
      		    			x1=x2+1;
      		    			nextline+=g.getFont().getSize();
      		    	}
      		    	else
      		    	{
      		    		g.drawString(sub.substring(x1,sub.length()),30,30);
      		    		finished = true;
      		    	}
      		    }
      		    g.setFont(previous);
      		}
      	}
      }
      
}

