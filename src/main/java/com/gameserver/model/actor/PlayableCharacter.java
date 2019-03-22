package com.gameserver.model.actor;

import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.IntentionAction;
import com.gameserver.model.actor.ai.base.intention.IntentionMoveTo;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.*;
import com.gameserver.template.pc.PCBaseStats;
import com.gameserver.util.math.xy.Math2d;

import java.util.List;

public class PlayableCharacter extends BaseActor {

    private ClientListenerThread clientListenerThread;

    private CharacterClass characterClass;
    private PCBaseStats pcBaseStats;

    public PlayableCharacter(ClientListenerThread clientListenerThread, Character character)
    {
        super();

        setClientListenerThread(clientListenerThread);
        this.clientListenerThread = clientListenerThread;

        id = character.getId();
        setLocationX(character.getLocationX());
        setLocationY(character.getLocationY());
        setLocationZ(character.getLocationZ());
        setName(character.getName());
        setRace(character.getRace());
        setCharacterClass(character.getCharacterClass());
        pcBaseStats = DataEngine.getInstance().GetPCBaseStatsByRace(character.getRace());
    }

    public ClientListenerThread getClientListenerThread() {
        return clientListenerThread;
    }

    public void setClientListenerThread(ClientListenerThread clientListenerThread) {
        this.clientListenerThread = clientListenerThread;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public PCBaseStats getPcBaseStats() {
        return pcBaseStats;
    }

    private void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public void sendPacket(AbstractSendablePacket packet)
    {
        clientListenerThread.sendPacket(packet);
    }

    public void sendPacketAndBroadcastToNearbyPlayers(AbstractSendablePacket packet)
    {
        sendPacket(packet);

        for(PlayableCharacter pc : nearbyPlayers())
        {
            pc.sendPacket(packet);
        }
    }

    public List<BaseActor> nearbyActors()
    {
        return World.getInstance().getActorsInRadius(this,10000);
    }

    public List<PlayableCharacter> nearbyPlayers()
    {
        return World.getInstance().getPlayableCharactersInRadius(this,100000);
    }

    public void moveToLocation(int x, int y, int z)
    {
        if(getActorIntention().getIntention().intentionType == IntentionType.INTENTION_IDLE)
        {
            setIsMoving(true);
            sendPacketAndBroadcastToNearbyPlayers(new MoveActorToLocation(getObjectId(),x,y,z));
        }
        else
        {
            getActorIntention().setIntention(new IntentionMoveTo(x,y,z));
        }
    }

    public void moveToActor(BaseActor actor, int distance)
    {
        setIsMoving(true);
        sendPacketAndBroadcastToNearbyPlayers(new MoveToPawn(this,actor,distance));
    }

    public void stopMoving()
    {
        setIsMoving(false);
        sendPacketAndBroadcastToNearbyPlayers(new StopMoving(this));
    }

    public void action(int objectId) {
        BaseActor actor = World.getInstance().getActorByObjectId(objectId);
        if(actor != null) {

            if (target == actor)
            {
                if(actor instanceof NPCActor)
                {
                    if(Math2d.calculateBetweenTwoActorsIn2d(this,actor) <= 400 && !isAttacking())
                    {
                        ((NPCActor) actor).getNpcAi().onTalk(this);
                    }
                    else
                    {
                        _actorIntention.setIntention(new IntentionAction(actor, true));
                        _actorIntention.intentionThink();
                    }
                }
            }
            else
            {
                target = actor;
                sendPacket(new TargetSelected(objectId));
            }
        }
        else
        {
            System.out.println("Client requested action for non existing target. Id: "+objectId);
        }
    }

    public void sendDialog(String dialog)
    {
        sendPacket(new Dialog(dialog));
    }
}
