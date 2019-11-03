package com.gameserver.model.actor.ai.type;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.actor.ai.base.intention.IntentionAttack;
import com.gameserver.model.actor.ai.base.intention.IntentionIdle;
import com.gameserver.util.math.xyz.Math3d;

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
        if(target == null) {
            target = source;
            targetPreviousX = target.getLocationX();
            targetPreviousY = target.getLocationY();
            targetPreviousZ = target.getLocationZ();
            moveToActor(target, 100);
            actor.getActorIntention().setIntention(new IntentionAttack(target));
        }
    }

    @Override
    public void intentionAttackThink() {
        if(target instanceof PlayableCharacter)
        {
            if(!((PlayableCharacter) target).isConnected())
            {
                actor.getActorIntention().setIntention(new IntentionIdle());
            }
        }
        int weaponAttackDistance = 150; //TODO: change this const to var
        System.out.println("Executed intention think. Target is null: "+target);
        if(!actor.isCanAttack())
        {
            actor.setMoveData(null);
            return;
        }
        if(Math3d.calculateBetweenTwoActors(actor, target, true) < weaponAttackDistance)
        {
            actor.setMoveData(null);
            if(actor.isCanAttack())
            {
                actor.attack(target);
            }
        }
        else if(targetLocationChanged())
        {
            targetPreviousX = target.getLocationX();
            targetPreviousY = target.getLocationY();
            targetPreviousZ = target.getLocationZ();
            System.out.println("target location changed");
            moveToActor(target, 100);
        }
    }

    @Override
    public void resetAi() {
        target = null;
        targetPreviousX = 0;
        targetPreviousY = 0;
        targetPreviousZ = 0;
    }

    private boolean targetLocationChanged()
    {
        return targetPreviousX != target.getLocationX() || targetPreviousY != target.getLocationY() || targetPreviousZ != target.getLocationZ();
    }
}
