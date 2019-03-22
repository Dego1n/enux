package com.gameserver.packet.client2game;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class ValidatePosition extends AbstractReceivablePacket {

    private ClientListenerThread _clientListenerThread;

    public ValidatePosition(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        int x = readD();
        int y = readD();
        int z = readD();

        boolean stoppedMoving = readH() == 1; //TODO: handle

        //TODO:
        PlayableCharacter pc = _clientListenerThread.playableCharacter;
        pc.setLocationX(x);
        pc.setLocationY(y);
        pc.setLocationZ(z);

        if(stoppedMoving)
        {
            pc.setIsMoving(false);
        }

        pc.getActorIntention().intentionThink();

        System.out.println("Validated position for "+pc.getName()+" x:"+x+" y:"+y+" z:"+z);
    }
}
