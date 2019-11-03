package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionMoveTo extends AbstractIntention {
    public final int LocationX;
    public final int LocationY;
    public final int LocationZ;

    public IntentionMoveTo(int x, int y, int z) {
        super(IntentionType.INTENTION_MOVE_TO);
        LocationX = x;
        LocationY = y;
        LocationZ = z;
    }
}
