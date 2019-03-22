package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class RequestConnectToGameServer extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public RequestConnectToGameServer(ClientListenerThread clientListenerThread, byte[] packet)
    {
        super(packet);
        _clientListenerThread = clientListenerThread;
        handle(); //TODO: может вызывается само из абстрактного класса? нужно закомментировать и проверить
    }

    private void handle() {

        int gameSessionKey = readD();

        _clientListenerThread.receivedGameSessionKey(gameSessionKey);
    }
}
