package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.brackeen.javagamebook.graphics.ToolFrame;
import com.brackeen.javagamebook.tilegame.GameManager;

public class UpdateButtonListener implements ActionListener {

	private GameManager	gameManagerInstance;
	
	public UpdateButtonListener()
	{
		gameManagerInstance = GameManager.getGameManagerInstance();
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		gameManagerInstance.setGravityMultiplier(ToolFrame.getToolFrameInstance().getGravityMultiplierValue());
		gameManagerInstance.setPlayerSpeedMultiplier(ToolFrame.getToolFrameInstance().getPlayerSpeedValue());
		gameManagerInstance.setPlayerJumpSpeedMultiplier(ToolFrame.getToolFrameInstance().getPlayerJumpSpeedValue());
		gameManagerInstance.setEnemySpeedMultiplier(ToolFrame.getToolFrameInstance().getEnemySpeedValue());
		gameManagerInstance.setEnemyJumpSpeedMultiplier(ToolFrame.getToolFrameInstance().getEnemyJumpSpeedValue());
		gameManagerInstance.setHealth(ToolFrame.getToolFrameInstance().getPlayerHealthValue());
		gameManagerInstance.setMaxHealth(ToolFrame.getToolFrameInstance().getPlayerMaxHealthValue());
		gameManagerInstance.setMaxHitClock(ToolFrame.getToolFrameInstance().getInvulnerableTimeValue());
	}

}
