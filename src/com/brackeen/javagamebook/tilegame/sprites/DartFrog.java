package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;;
public class DartFrog extends Creature
{
    public DartFrog(Animation left, Animation right, Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        trackPlayer = false;
        intelligent = true;       
        health = 1;
        flying = false;
    }

    public float getMaxSpeed()
    {
        return .268f * enemySpeedMultiplier;
    }
}