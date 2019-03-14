package com.gameserver.packet.client2game;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class RequestAttack extends AbstractReceivablePacket {

    private ClientListenerThread _clientListenerThread;
    public RequestAttack(ClientListenerThread listenerThread, byte[] packet) {
        super(listenerThread, packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    @Override
    protected void handle() {
        PlayableCharacter pc = _clientListenerThread.playableCharacter;
        pc.requestAttack();
    }
}
