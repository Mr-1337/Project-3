package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;;
public class Vulture extends Creature
{
    public Vulture(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        trackPlayer = false;
        intelligent = true;       
        health = 2;
        flying = true;
    }

    public float getMaxSpeed()
    {
        return .195f * enemySpeedMultiplier;
    }
}