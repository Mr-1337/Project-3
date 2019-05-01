package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

public class CreepingDarkness extends Creature
{
    public CreepingDarkness(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        intelligent = true;
        health = 3;
    }

    public float getMaxSpeed()
    {
        return .05f * enemySpeedMultiplier;
    }
}