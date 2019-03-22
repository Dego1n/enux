package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class Attack extends AbstractSendablePacket implements IServerPacket {

    private BaseActor _actor;
    private BaseActor _target;

    public Attack(BaseActor actor,BaseActor target)
    {
        super();
        _target = target;
        _actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.ATTACK);
        writeD(_actor.getObjectId());
        writeD(_target.getObjectId());
    }
}
