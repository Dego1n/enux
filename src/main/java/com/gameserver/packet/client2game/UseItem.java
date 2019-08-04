package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UseItem extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(UseItem.class);

    private ClientListenerThread clientListenerThread;

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
