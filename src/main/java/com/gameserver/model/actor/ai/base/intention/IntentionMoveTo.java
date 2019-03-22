package com.gameserver.model.actor.ai.base.intention;

import com.gameserver.model.actor.ai.base.IntentionType;

public class IntentionMoveTo extends AbstractIntention {
    public int LocationX;
    public int LocationY;
    public int LocationZ;

    public IntentionMoveTo(int x, int y, int z) {
        super(IntentionType.INTENTION_MOVE_TO);
        LocationX = x;
        LocationY = y;
        LocationZ = z;
    }
}
