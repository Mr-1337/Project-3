package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class Spooder extends Creature
{
    public Spooder(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        intelligent = true;
        trackPlayer = true;
    }

    public float getMaxSpeed()
    {
        return .245f * enemySpeedMultiplier;
    }
}