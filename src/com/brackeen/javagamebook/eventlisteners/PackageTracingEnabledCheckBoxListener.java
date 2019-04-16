package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.tilegame.sprites.*;
import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.input.*;
import com.brackeen.javagamebook.sound.*;
import com.brackeen.javagamebook.test.*;
import com.brackeen.javagamebook.util.*;
import com.brackeen.javagamebook.tilegame.*;

/**
 * @author Josh Figueiredo
 */

public class PackageTracingEnabledCheckBoxListener implements ActionListener{
	private GameManager	gameManagerInstance;
	private JCheckBox	PackageTracingCheckBox;
/*	private JCheckBox	graphicsPackageTracingEnabledCheckBox;
	private JCheckBox	inputPackageTracingEnabledCheckBox;
	private JCheckBox	soundPackageTracingEnabledCheckBox;
	private JCheckBox	testPackageTracingEnabledCheckBox;
	private JCheckBox	tilegamePackageTracingEnabledCheckBox;
	private JCheckBox	spritesPackageTracingEnabledCheckBox;
	private JCheckBox	utilPackageTracingEnabledCheckBox;
*/	int packageNumber;
	
	public PackageTracingEnabledCheckBoxListener(JCheckBox checkBox, int packageNum)
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
/*		switch(packageNum)
		{
		case 1:
	         graphicsPackageTracingEnabledCheckBox = checkBox;
	         break;
		case 2:
	         inputPackageTracingEnabledCheckBox = checkBox;
	         break;
		case 3:
	         soundPackageTracingEnabledCheckBox = checkBox;
	         break;
		case 4:
	         testPackageTracingEnabledCheckBox = checkBox;
	         break;
		case 5:
	         tilegamePackageTracingEnabledCheckBox = checkBox;
	         break;
		case 6:
	         spritesPackageTracingEnabledCheckBox = checkBox;
	         break;
		case 7:
	         utilPackageTracingEnabledCheckBox = checkBox;
	         break;
			
		}
*/		
		packageNumber = packageNum;
		PackageTracingCheckBox = checkBox;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(PackageTracingCheckBox.isSelected())
		{
//			gameManagerInstance.setToolScreen(true);
			switch(packageNumber)
			{
			case 1:
				GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().setEnabled(true);
		         break;
			case 2:
		         InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().setEnabled(true);
		         break;
			case 3:
		         SoundPackageTracingEnabled.getSoundPackageTracingEnabledInstance().setEnabled(true);
		         break;
			case 4:
		         TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().setEnabled(true);
		         break;
			case 5:
		         TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().setEnabled(true);
		         break;
			case 6:
				SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().setEnabled(true);
		         break;
			case 7:
		         UtilPackageTracingEnabled.getUtilPackageTracingEnabledInstance().setEnabled(true);
		         break;
				
			}
//			tracingOnCheckBox.setEnabled(true);
		}
		else
		{
			switch(packageNumber)
			{
			case 1:
				GraphicsPackageTracingEnabled.getGraphicsPackageTracingEnabledInstance().setEnabled(false);
		         break;
			case 2:
		         InputPackageTracingEnabled.getInputPackageTracingEnabledInstance().setEnabled(false);
		         break;
			case 3:
		         SoundPackageTracingEnabled.getSoundPackageTracingEnabledInstance().setEnabled(false);
		         break;
			case 4:
		         TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().setEnabled(false);
		         break;
			case 5:
		         TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().setEnabled(false);
		         break;
			case 6:
				SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().setEnabled(false);
		         break;
			case 7:
		         UtilPackageTracingEnabled.getUtilPackageTracingEnabledInstance().setEnabled(false);
		         break;
				
			}
		}

	}

}
