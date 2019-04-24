package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.codereflection.*;

/**
    A Fly is a Creature that fly slowly in the air.
*/
public class Fly extends Creature {

//    private Throwable e = new Throwable();
    
    public Fly(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        flying = true;
    }


    public float getMaxSpeed() {
        return 0.2f * enemySpeedMultiplier;
    }


    public boolean isFlying() {
        return isAlive() && super.isFlying();
    }

}
