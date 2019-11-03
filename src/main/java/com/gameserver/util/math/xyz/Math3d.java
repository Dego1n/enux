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

    private static double calculateDistanceBetween2dPoints(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(
                Math.pow((x2 - x1), 2) +
                Math.pow((y2 - y1), 2)
        );
    }

    private static double calculateBetweenTwoActors(BaseActor actor, BaseActor actorTarget)
    {
        //TODO: считаем в 2D пространстве вместо 3D пока не решили проблему с падением акторов. позже поменять на 3D
//        return calculateDistanceBetween3dPoints(
//                actor.getLocationX(),
//                actor.getLocationY(),
//                actor.getLocationZ(),
//                actorTarget.getLocationX(),
//                actorTarget.getLocationY(),
//                actorTarget.getLocationZ()
//        );
        return calculateDistanceBetween2dPoints(
                actor.getLocationX(),
                actor.getLocationY(),
                actorTarget.getLocationX(),
                actorTarget.getLocationY()
        );
    }

    /**
     * Возможно не правильно считается с коллизиями в 3d пространстве! Скорее всего нужно учитывать collisionHeight
     * @param actor - Текущий Actor
     * @param actorTarget - Цель
     * @param useCollision - Считать расстояние между капсулами
     * @return - Возвращает дистацнию
     */
    public static double calculateBetweenTwoActors(BaseActor actor, BaseActor actorTarget, boolean useCollision)
    {
        double distanceWithoutCollision = calculateBetweenTwoActors(actor,actorTarget);

        if(useCollision)
        {
            double distanceWithCollision = distanceWithoutCollision - actor.getCollisionRadius() - actorTarget.getCollisionRadius();
            return distanceWithCollision > 0 ? distanceWithCollision : 0;
        }
        else
        {
            return distanceWithoutCollision;
        }
    }
}
