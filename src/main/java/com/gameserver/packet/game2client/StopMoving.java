package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class StopMoving extends AbstractSendablePacket implements IServerPacket {

    private final BaseActor _actor;
    public StopMoving(BaseActor actor)
    {
        super();
        _actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.STOP_MOVING);
        writeD(_actor.getObjectId());
    }
}
