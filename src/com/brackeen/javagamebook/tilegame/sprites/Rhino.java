package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;;
public class Rhino extends Creature
{
    public Rhino(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        trackPlayer = false;
        intelligent = true;       
        health = 3;
        flying = false;
    }

    public float getMaxSpeed()
    {
        return .102f * enemySpeedMultiplier;
    }
}