package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.Inventory;

public class RequestInventory extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public RequestInventory(ClientListenerThread clientListenerThread, byte[] packet) {
        super(packet);
        _clientListenerThread = clientListenerThread;
        handle();
    }

    private void handle() {
        _clientListenerThread.playableCharacter.sendPacket(new Inventory(_clientListenerThread.playableCharacter.getInventory(), _clientListenerThread.playableCharacter.getEquipInfo()));
    }
}
