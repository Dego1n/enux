package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.UserInfo;

public class EnterWorld extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public EnterWorld(ClientListenerThread listenerThread, byte[] packet) {
        super(listenerThread, packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    @Override
    protected void handle() {
        _clientListenerThread.sendPacket(new UserInfo(_clientListenerThread.playableCharacter));
    }
}
