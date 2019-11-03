package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class ActorInfo extends AbstractSendablePacket implements IServerPacket {

    private final BaseActor actor;
    public ActorInfo(BaseActor actor)
    {
        super();
        this.actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.ACTOR_INFO);
        writeD(actor.getObjectId());
        writeD(actor.getTemplateId());

        writeD(actor.getLocationX());
        writeD(actor.getLocationY());
        writeD(actor.getLocationZ());

        writeD(actor.getCollisionHeight());
        writeD(actor.getCollisionRadius());

        writeH(actor.isFriendly() ? 1 : 0);

        writeD((int)actor.getCurrentHp());
        writeD((int)actor.getMaxHp());

        writeS(actor.getName());

    }
}
