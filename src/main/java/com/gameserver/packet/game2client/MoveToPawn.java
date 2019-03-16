package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class MoveToPawn extends AbstractSendablePacket implements IServerPacket {

    BaseActor _actor;
    BaseActor _actorTarget;
    float _radius;

    public MoveToPawn(BaseActor actor, BaseActor actorTarget, float radius)
    {
        super();
        _actor = actor;
        _actorTarget = actorTarget;
        _radius = radius;
        build();
    }

    @Override
    public void build() {
        writeH(0x0c);

        writeD(_actor.getObjectId());
        writeD(_actorTarget.getObjectId());
        writeD(Math.round(_radius));
    }
}
