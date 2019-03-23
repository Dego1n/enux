package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class ActorSay extends AbstractSendablePacket implements IServerPacket {

    private BaseActor _actor;
    private String _actorName;
    private String _message;

    public ActorSay(BaseActor actor, String actorName, String message)
    {
        super();
        _actor = actor;
        _actorName = actorName;
        _message = message;
        build();
    }

    private void build()
    {
        writeH(ServerPackets.ACTOR_SAY);
        writeD(_actor.getObjectId());
        writeS(_actorName);
        writeS(_message);
    }

}
