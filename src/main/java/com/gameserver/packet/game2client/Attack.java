package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class Attack extends AbstractSendablePacket implements IServerPacket {

    BaseActor _actor;
    BaseActor _target;

    public Attack(BaseActor actor,BaseActor target)
    {
        super();
        _target = target;
        _actor = actor;
        build();
    }

    @Override
    public void build() {
        writeH(ServerPackets.ATTACK);
        writeD(_actor.getObjectId());
        writeD(_target.getObjectId());
    }
}
