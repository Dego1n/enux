package com.gameserver.util.math.xy;

import com.gameserver.model.actor.BaseActor;

/*
 * @author Dego1n
 * Copyright (c) 23.03.2019
 */

public class Math2d {
    private static double calculateDistanceBetween2dPoints(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(
                Math.pow((x2 - x1), 2) +
                        Math.pow((y2 - y1), 2)
        );
    }

    public static double calculateBetweenTwoActorsIn2d(BaseActor actor, BaseActor actorTarget)
    {
        return calculateDistanceBetween2dPoints(
                actor.getLocationX(),
                actor.getLocationY(),
                actorTarget.getLocationX(),
                actorTarget.getLocationY()
        );
    }
    public static double calculateBetweenTwoActorsIn2d(BaseActor actor, BaseActor actorTarget, boolean useCollision)
    {
        double distanceWithoutCollision = calculateBetweenTwoActorsIn2d(actor, actorTarget);
        if(useCollision) {
            double distanceWithCollision = distanceWithoutCollision - actor.getCollisionRadius() - actorTarget.getCollisionRadius();

            return distanceWithCollision > 0 ? distanceWithCollision : 0;
        }
        else
        {
            return distanceWithoutCollision;
        }
    }
}
