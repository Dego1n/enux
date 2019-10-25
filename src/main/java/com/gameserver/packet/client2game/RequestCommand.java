package com.gameserver.packet.client2game;

import com.gameserver.instance.CommandEngine;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestCommand extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(RequestCommand.class);

    private ClientListenerThread clientListenerThread;

    public RequestCommand(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        String command = readS();

        CommandEngine.getInstance().handleAdminCommand(clientListenerThread.playableCharacter, command);
    }
}
