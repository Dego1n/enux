package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class Action extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public Action(ClientListenerThread clientListenerThread, byte[] packet) {
        super(clientListenerThread, packet);
        _clientListenerThread = clientListenerThread;
        handle();
    }

    @Override
    protected void handle() {
        int objectId = readD();
        _clientListenerThread.playableCharacter.action(objectId);
    }
}
