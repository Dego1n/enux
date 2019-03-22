package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUnequipItem extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(RequestUnequipItem.class);

    private ClientListenerThread clientListenerThread;

    public RequestUnequipItem(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        log.warn("Client sent not implemented packet: "+this.getClass().getName());
    }
}
