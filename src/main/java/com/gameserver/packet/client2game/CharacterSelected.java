package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class CharacterSelected extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public CharacterSelected(ClientListenerThread clientListenerThread, byte[] packet)
    {
        super(clientListenerThread, packet);
        _clientListenerThread = clientListenerThread;
        handle(); //TODO: может вызывается само из абстрактного класса? нужно закомментировать и проверить
    }

    @Override
    protected void handle() {

        int characterId = readD();
        _clientListenerThread.characterSelected(characterId);
    }
}
