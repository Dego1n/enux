package com.gameserver.model.actor.ai.type;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.game2client.MoveToPawn;

public abstract class AbstractAI {

    protected BaseActor actor;

    public AbstractAI(BaseActor actor) {
        this.actor = actor;
    }

    protected void moveToActor(BaseActor actor, int offset)
    {
        this.actor.broadcastPacket(new MoveToPawn(this.actor, actor, offset));
    }

    public abstract void onAttacked(BaseActor source);
}
