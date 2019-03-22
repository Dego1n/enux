package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionAttack extends AbstractIntention {

    public BaseActor Target;
    public boolean ForceMoveToPawn;

    public IntentionAttack(BaseActor target, boolean forceMoveToPawn) {
        super(IntentionType.INTENTION_ATTACK);
        Target = target;
        ForceMoveToPawn = forceMoveToPawn;
    }
}
