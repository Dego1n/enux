package com.gameserver.packet.client2game;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.MoveActorToLocation;

public class MoveToLocation extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public MoveToLocation(ClientListenerThread clientListenerThread, byte[] packet)
    {
        super(clientListenerThread, packet);
        _clientListenerThread = clientListenerThread;
        handle(); //TODO: может вызывается само из абстрактного класса? нужно закомментировать и проверить
    }

    @Override
    protected void handle() {

        int originX = readD();
        int originY = readD();
        int originZ = readD();

        int targetX = readD();
        int targetY = readD();
        int targetZ = readD();

        System.out.println("OriginX: "+originX+ " OriginY: "+originY+" OriginZ: "+originZ);
        System.out.println("TargetX: "+targetX+ " TargetY: "+targetY+" TargetZ: "+targetZ);
        _clientListenerThread.sendPacket(new MoveActorToLocation(targetX,targetY,targetZ));
    }
}
