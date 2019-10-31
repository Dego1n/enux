package com.gameserver.model.actor.ai.type;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.game2client.MoveToPawn;
import com.gameserver.tick.GameTickController;

public abstract class AbstractAI {

    protected BaseActor actor;

    public AbstractAI(BaseActor actor) {
        this.actor = actor;
    }

    public void moveToActor(BaseActor actor, int offset)
    {
        BaseActor.MoveData moveData = new BaseActor.MoveData();
        moveData.x_destination = actor.getLocationX();
        moveData.y_destination = actor.getLocationY();
        moveData.z_destination = actor.getLocationZ();
        moveData.offset = offset;
        this.actor.setMoveData(moveData);
        GameTickController.getInstance().registerMovingObject(this.actor);
        this.actor.broadcastPacket(new MoveToPawn(this.actor, actor, offset));
    }

    public abstract void onAttacked(BaseActor source);

    public abstract void intentionAttackThink();
}
