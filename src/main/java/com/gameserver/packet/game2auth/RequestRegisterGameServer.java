package com.gameserver.packet.game2auth;

import com.gameserver.config.Config;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class RequestRegisterGameServer extends AbstractSendablePacket implements IServerPacket {

    public RequestRegisterGameServer()
    {
        build();
    }

    private void build() {
        writeH(0x01); //packetID
        writeD(0x00); //TODO: server key
        writeS(Config.GAME_SOCKET_LISTEN_ADDRESS); //Server Address
        writeH(Config.GAME_SOCKET_LISTEN_PORT); //Server Port
        writeS(Config.GAME_SERVER_NAME); //Server name
    }
}
