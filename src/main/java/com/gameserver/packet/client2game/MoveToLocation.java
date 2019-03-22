package com.gameserver.packet.client2game;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class MoveToLocation extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public MoveToLocation(ClientListenerThread clientListenerThread, byte[] packet)
    {
        super(packet);
        _clientListenerThread = clientListenerThread;
        handle(); //TODO: может вызывается само из абстрактного класса? нужно закомментировать и проверить
    }

    private void handle() {

        int originX = readD();
        int originY = readD();
        int originZ = readD();

        int targetX = readD();
        int targetY = readD();
        int targetZ = readD();

        PlayableCharacter character = _clientListenerThread.playableCharacter;

        character.setLocationX(targetX);
        character.setLocationY(targetY);
        character.setLocationX(targetZ);

        character.moveToLocation(targetX,targetY,targetZ);
    }
}
