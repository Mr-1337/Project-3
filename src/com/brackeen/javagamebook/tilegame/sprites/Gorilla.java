package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.tilegame.GameManager;

public class Gorilla  extends Creature{
	
	private Player player;
	
	public Gorilla(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left,right, deadLeft,deadRight);
		flying = false;
		health = 6;
	}
	
	private static float SPEED = .2f;

}