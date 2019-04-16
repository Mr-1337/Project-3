package com.brackeen.javagamebook.graphics;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.tilegame.ScriptManager;
import com.brackeen.javagamebook.eventlisteners.*;

public class OptionsMenu extends JFrame{
	
	private JButton 		doneButton;
	private JCheckBox 		fullScreenCheckBox;
	private JCheckBox		toolScreenCheckBox;
	private JCheckBox		fpsCheckBox;
	private TextField		levelSetTextField;
	private JButton			loadLevelSetButton;
	private JPanel			levelSetPanel;
	private JPanel			optionsContainer;
	private JPanel			videoSettings;
	private JPanel			otherSettings;
	private JPanel			donePanel;
	private CheckboxGroup	resolutionCheckboxGroup;
	private CheckboxGroup	colorDepthCheckboxGroup;
	private Checkbox		resolution640;
	private Checkbox		resolution800;
	private Checkbox		resolution1024;
	private Checkbox		resolution1280;
	private Checkbox		colorDepth16;
	private Checkbox		colorDepth24;
	private Checkbox		colorDepth32;
	
	
	
	
	public OptionsMenu()
	{
		doneButton = new JButton("Done");
		doneButton.addActionListener(new DoneButtonListener(this));

		toolScreenCheckBox = new JCheckBox("Run Game with Tool Screen");
		toolScreenCheckBox.addActionListener(new ToolScreenCheckBoxListener(toolScreenCheckBox));
		
		fullScreenCheckBox = new JCheckBox("Run Game in Full-Screen Mode");
		fullScreenCheckBox.addActionListener(new FullScreenCheckBoxListener(fullScreenCheckBox, toolScreenCheckBox));
		
		fpsCheckBox = new JCheckBox("Show FPS");
		fpsCheckBox.setSelected(GameManager.getGameManagerInstance().getShowFPS());
		fpsCheckBox.addActionListener(new FPSCheckBoxListener(fpsCheckBox));
		
		otherSettings = new JPanel();
		otherSettings.setLayout(new GridLayout(4,1));
		otherSettings.add(fullScreenCheckBox);
		otherSettings.add(toolScreenCheckBox);
		otherSettings.add(fpsCheckBox);
		//otherSettings.add(tracingOnCheckBox);
		//otherSettings.add(tracingAbstractionLevelComboBox);
		
		resolutionCheckboxGroup = new CheckboxGroup();
		
		resolution640 = new Checkbox("640 x 480", resolutionCheckboxGroup, false);
		resolution640.addItemListener(new ResolutionCheckBoxListener(640, 480));
		resolution800 = new Checkbox("800 x 600", resolutionCheckboxGroup, false);
		resolution800.addItemListener(new ResolutionCheckBoxListener(800, 600));
		resolution1024 = new Checkbox("1024 x 768", resolutionCheckboxGroup, false);
		resolution1024.addItemListener(new ResolutionCheckBoxListener(1024, 768));
		resolution1280 = new Checkbox("1280 x 1024", resolutionCheckboxGroup, false);
		resolution1280.addItemListener(new ResolutionCheckBoxListener(1280, 1024));
		resolution800.setState(true);
		
		colorDepthCheckboxGroup = new CheckboxGroup();
		
		colorDepth16 = new Checkbox("16-bit", colorDepthCheckboxGroup, false);
		colorDepth16.addItemListener(new ColorDepthCheckBoxListener(16));
		colorDepth24 = new Checkbox("24-bit", colorDepthCheckboxGroup, false);
		colorDepth24.addItemListener(new ColorDepthCheckBoxListener(24));
		colorDepth32 = new Checkbox("32-bit", colorDepthCheckboxGroup, false);
		colorDepth32.addItemListener(new ColorDepthCheckBoxListener(32));
		colorDepth16.setState(true);
		
		levelSetPanel = new JPanel();
		levelSetPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Level Set to Play"));
		levelSetPanel.setLayout(new GridLayout(2,1));
		JPanel levelChoicePanel = new JPanel();
		levelSetTextField = new TextField();
		levelSetTextField.setText(ScriptManager.getScriptManagerInstance().getLevelMappingFile());
		loadLevelSetButton = new JButton("Load Level Set");
		loadLevelSetButton.addActionListener(new LoadLevelSetButtonListener(levelSetTextField));
		levelChoicePanel.add(levelSetTextField);
		levelChoicePanel.add(loadLevelSetButton);
		JLabel levelSetInfo = new JLabel("Enter in a level set file in the script folder to play");
		levelSetPanel.add(levelChoicePanel);
		levelSetPanel.add(levelSetInfo);
		
		videoSettings = new JPanel();
		videoSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Video Settings"));
		videoSettings.setLayout(new GridLayout(5,2));
		videoSettings.add(new JLabel("Resolution"));
		videoSettings.add(new JLabel("Color Depth"));
		videoSettings.add(resolution640);
		videoSettings.add(colorDepth16);
		videoSettings.add(resolution800);
		videoSettings.add(colorDepth24);
		videoSettings.add(resolution1024);
		videoSettings.add(colorDepth32);
		videoSettings.add(resolution1280);
		
		donePanel = new JPanel();
		donePanel.add(doneButton);
		
		optionsContainer = new JPanel(new GridLayout(4,1));
		optionsContainer.add(otherSettings);
		optionsContainer.add(levelSetPanel);
		optionsContainer.add(videoSettings);
		optionsContainer.add(donePanel);
		
		this.setSize(400,400);
    	this.add(optionsContainer);
    	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
