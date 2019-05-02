package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;;
public class Hyena extends Creature
{
    public Hyena(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        trackPlayer = true;      
        health = 2;
    }

    public float getMaxSpeed()
    {
        return .3f * enemySpeedMultiplier;
    }
}