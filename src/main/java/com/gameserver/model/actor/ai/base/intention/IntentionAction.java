package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionAction extends AbstractIntention {

    public final BaseActor Target;

    public IntentionAction(BaseActor target) {
        super(IntentionType.INTENTION_ACTION);
        Target = target;
    }
}
