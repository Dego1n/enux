package com.gameserver.util.math.xyz;

import com.gameserver.model.actor.BaseActor;

public class Math3d {

    private static double calculateDistanceBetween3dPoints(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        return Math.sqrt(
          Math.pow((x2 - x1), 2) +
          Math.pow((y2 - y1), 2) +
          Math.pow((z2 - z1), 2)
        );
    }

    public static double calculateBetweenTwoActors(BaseActor actor, BaseActor actorTarget)
    {
        return calculateDistanceBetween3dPoints(
                actor.getLocationX(),
                actor.getLocationY(),
                actor.getLocationZ(),
                actorTarget.getLocationX(),
                actorTarget.getLocationY(),
                actorTarget.getLocationZ()
        );
    }
}
