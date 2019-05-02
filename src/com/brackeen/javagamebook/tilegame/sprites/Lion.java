package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;;
public class Lion extends Creature
{
    public Lion(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        trackPlayer = true;
        health = 2;
    }

    public float getMaxSpeed()
    {
        return .2f * enemySpeedMultiplier;
    }
}