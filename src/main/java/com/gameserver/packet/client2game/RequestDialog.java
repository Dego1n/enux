package com.gameserver.packet.client2game;

import com.gameserver.model.World;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;

public class RequestDialog extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public RequestDialog(ClientListenerThread listenerThread, byte[] packet) {
        super(listenerThread, packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    @Override
    protected void handle() {
        int objectId = readH();
        String dialog = readS();
        BaseActor actor = World.getInstance().getActorByObjectId(objectId);
        if(actor instanceof NPCActor)
        {
            ((NPCActor) actor).getNpcAi().requestDialog(_clientListenerThread.playableCharacter,dialog);
        }
    }
}
