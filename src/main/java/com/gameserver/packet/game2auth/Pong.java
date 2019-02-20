package com.gameserver.packet.game2auth;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class Pong extends AbstractSendablePacket implements IServerPacket {

    public Pong()
    {
        build();
    }

    public void build() {
        writeH(0x02); //packetID
    }
}
