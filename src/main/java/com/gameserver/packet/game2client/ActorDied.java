package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class ActorDied extends AbstractSendablePacket implements IServerPacket {

    private BaseActor actor;

    public ActorDied(BaseActor actor)
    {
        super();
        this.actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.ACTOR_DIED);
        writeD(actor.getObjectId());
        writeD(10); //Монтаж смерти -> через сколько вызвать Destroy актора
    }
}
