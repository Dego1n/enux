package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class SystemMessage extends AbstractSendablePacket implements IServerPacket {

    private String _message;

    public SystemMessage(String message)
    {
        super();
        _message = message;
        build();
    }

    private void build()
    {
        writeH(ServerPackets.SYSTEM_MESSAGE);
        writeS(_message);
    }
}
