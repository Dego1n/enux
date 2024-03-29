package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Say extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(Say.class);

    private final ClientListenerThread clientListenerThread;

    public Say(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        String message = readS();

        clientListenerThread.playableCharacter.say(message);
    }
}
