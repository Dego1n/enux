package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class StateInfo extends AbstractSendablePacket implements IServerPacket {

    private BaseActor _actor;

    public StateInfo(BaseActor actor)
    {
        super();
        _actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.STATE_INFO);
        writeD(_actor.getObjectId());
        writeH(_actor.isAttacking() ? 1 : 0);
    }
}
