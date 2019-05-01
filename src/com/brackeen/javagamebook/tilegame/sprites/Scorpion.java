package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;;
public class Scorpion extends Creature
{
    public Scorpion(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        trackPlayer = true;
        intelligent = true;       
        health = 1;
        flying = false;
    }

    public float getMaxSpeed()
    {
        return .155f * enemySpeedMultiplier;
    }
}