package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class Action extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public Action(ClientListenerThread clientListenerThread, byte[] packet) {
        super(packet);
        _clientListenerThread = clientListenerThread;
        handle();
    }

    private void handle() {
        int objectId = readD();
        _clientListenerThread.playableCharacter.action(objectId);
    }
}
