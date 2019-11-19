package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionCast extends AbstractIntention {

    public final BaseActor Target;

    public final Ability ability;

    public IntentionCast(BaseActor target, Ability ability) {
        super(IntentionType.INTENTION_CAST);
        Target = target;
        this.ability = ability;
    }
}
