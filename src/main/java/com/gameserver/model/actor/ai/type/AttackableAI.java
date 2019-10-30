package com.gameserver.model.actor.ai.type;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.ai.base.intention.IntentionAttack;

public class AttackableAI extends AbstractAI {

    private BaseActor target;

    private int targetPreviousX;

    private int targetPreviousY;

    private int targetPreviousZ;

    public AttackableAI(BaseActor actor) {
        super(actor);
    }

    @Override
    public void onAttacked(BaseActor source) {
        target = source;
        targetPreviousX = target.getLocationX();
        targetPreviousY = target.getLocationY();
        targetPreviousZ = target.getLocationZ();
        moveToActor(target,100);
        actor.getActorIntention().setIntention(new IntentionAttack(target,true));
    }

    @Override
    public void intentionAttackThink() {
        System.out.println("Executed intention think");
        if(targetLocationChanged())
        {
            targetPreviousX = target.getLocationX();
            targetPreviousY = target.getLocationY();
            targetPreviousZ = target.getLocationZ();
            System.out.println("target location changed");
            moveToActor(target, 100);
        }
    }

    public boolean targetLocationChanged()
    {
        return targetPreviousX != target.getLocationX() || targetPreviousY != target.getLocationY() || targetPreviousZ != target.getLocationZ();
    }
}
