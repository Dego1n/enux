package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.ai.base.IntentionType;

public abstract class AbstractIntention {
    public IntentionType intentionType;

    AbstractIntention(IntentionType intentionType)
    {
        this.intentionType = intentionType;
    }
}
