package com.gameserver.packet.client2game;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.actor.ai.AiState;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.MoveActorToLocation;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AttackTask;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;

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

        if(character.getAi().GetStatus() == AiState.ATTACKING)
        {
            List<Task> tasks = character.getTasks();
            for(Iterator<Task> taskIterator = tasks.iterator(); taskIterator.hasNext();)
            {
                Task task = taskIterator.next();
                if(task.task instanceof AttackTask)
                {
                    character.stopTask(task);
                }
            }
        }

        character.getAi().changeStatus(AiState.MOVING);
            character.sendPacket(new MoveActorToLocation(character.getObjectId(),targetX,targetY,targetZ));

        for (PlayableCharacter pc : character.nearbyPlayers())
        {
            pc.sendPacket(new MoveActorToLocation(character.getObjectId(),targetX,targetY,targetZ));
        }
    }
}
