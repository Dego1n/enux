package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionAttack extends AbstractIntention {

    public final BaseActor Target;

    public IntentionAttack(BaseActor target) {
        super(IntentionType.INTENTION_ATTACK);
        Target = target;
    }
}
