package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class ActorInfo extends AbstractSendablePacket implements IServerPacket {

    BaseActor actor;
    public ActorInfo(BaseActor actor)
    {
        super();
        this.actor = actor;
        build();
    }

    @Override
    public void build() {
        System.out.println("Notifying about npc: "+actor.getName());
        writeH(0x09);
        writeD(actor.getObjectId());
        writeD(actor.getTemplateId());

        writeD(actor.getLocationX());
        writeD(actor.getLocationY());
        writeD(actor.getLocationZ());

        writeH(actor.isFriendly() ? 1 : 0);

        writeS(actor.getName());
    }
}
