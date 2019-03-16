package com.gameserver.util.math.xyz;

import com.gameserver.model.actor.BaseActor;

public class Math2d {
    public static double calculateDistanceBetween2dPoints(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(
                Math.pow((x2 - x1), 2) +
                        Math.pow((y2 - y1), 2)
        );
    }

    public static double calculateBetweenTwoActorsIn2d(BaseActor actor, BaseActor actorTarget)
    {
        System.out.println(actor.getLocationX());
        System.out.println(actor.getLocationY());
        System.out.println(actorTarget.getLocationX());
        System.out.println(actorTarget.getLocationY());
        return calculateDistanceBetween2dPoints(
                actor.getLocationX(),
                actor.getLocationY(),
                actorTarget.getLocationX(),
                actorTarget.getLocationY()
        );
    }
}
