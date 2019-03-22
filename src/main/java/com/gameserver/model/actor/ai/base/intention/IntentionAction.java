package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionAction extends AbstractIntention {

    public BaseActor Target;
    public boolean ForceMoveToPawn;

    public IntentionAction(BaseActor target, boolean forceMoveToPawn) {
        super(IntentionType.INTENTION_ACTION);
        Target = target;
        ForceMoveToPawn = forceMoveToPawn;
    }
}
