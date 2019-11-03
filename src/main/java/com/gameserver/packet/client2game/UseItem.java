package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class UseItem extends AbstractReceivablePacket {

    private final ClientListenerThread clientListenerThread;

    public UseItem(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        int objectId = readD();
        boolean fromInventory = readH() == 1;
        clientListenerThread.playableCharacter.useItem(objectId, fromInventory);
    }
}
