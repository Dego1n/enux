package com.gameserver.packet.client2game;

import com.gameserver.instance.DataEngine;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUseAbility extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(Say.class);

    private final ClientListenerThread clientListenerThread;

    public RequestUseAbility(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        int ability_id = readD();
        System.out.println("Client asked to use ability "+ability_id);
        clientListenerThread.playableCharacter.requestUseAbility(DataEngine.getInstance().getAbilityById(ability_id));
    }
}
