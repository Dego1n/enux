package com.gameserver.packet.game2auth;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class RequestRegisterGameServer extends AbstractSendablePacket implements IServerPacket {

    public RequestRegisterGameServer()
    {
        build();
    }

    public void build() {
        writeH(0x01); //packetID
        writeD(0x00); //TODO: server key
        writeS("127.0.0.1"); //Server Address TODO: get value from config
        writeH(8719); //Server Port TODO: get value from config
        writeS("Alpha Test Server #1"); //Server name TODO: get this value from config
    }
}
