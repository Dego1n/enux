package com.gameserver.packet.client2game;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.actor.ai.base.ActorIntention;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.AbstractIntention;
import com.gameserver.model.actor.ai.base.intention.IntentionMoveTo;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.MoveActorToLocation;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AttackTask;

import java.util.Iterator;
import java.util.List;

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

        PlayableCharacter character = _clientListenerThread.playableCharacter;

        character.setLocationX(targetX);
        character.setLocationY(targetY);
        character.setLocationX(targetZ);

        character.moveToLocation(targetX,targetY,targetZ);
    }
}
