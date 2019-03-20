package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDestroyItem extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(RequestDestroyItem.class);

    private ClientListenerThread clientListenerThread;

    public RequestDestroyItem(ClientListenerThread listenerThread, byte[] packet) {
        super(listenerThread, packet);
        clientListenerThread = listenerThread;
        handle();
    }

    @Override
    protected void handle() {
        log.warn("Client sent not implemented packet: "+this.getClass().getName());
    }
}
