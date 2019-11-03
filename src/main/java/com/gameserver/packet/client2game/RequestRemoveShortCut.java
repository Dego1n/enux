package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestRemoveShortCut extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(RequestRemoveShortCut.class);

    private final ClientListenerThread clientListenerThread;

    public RequestRemoveShortCut(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        log.warn("Client sent not implemented packet: "+this.getClass().getName());
    }
}
