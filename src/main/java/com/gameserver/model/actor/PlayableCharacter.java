package com.gameserver.model.actor;

import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.model.World;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.Dialog;
import com.gameserver.packet.game2client.TargetSelected;

import java.util.List;

public class PlayableCharacter extends BaseActor {

    private ClientListenerThread clientListenerThread;

    private CharacterClass characterClass;

    private BaseActor target;

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

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public void sendPacket(AbstractSendablePacket packet)
    {
        clientListenerThread.sendPacket(packet);
    }

    public BaseActor getTarget() {
        return target;
    }

    public void setTarget(BaseActor target) {
        this.target = target;
    }

    public List<BaseActor> nearbyActors()
    {
        return World.getInstance().getActorsInRadius(this,10000);
    }

    public List<PlayableCharacter> nearbyPlayers()
    {
        return World.getInstance().getPlayableCharactersInRadius(this,10000);
    }

    public void action(int objectId) {
        BaseActor actor = World.getInstance().getActorByObjectId(objectId);
        if(actor != null) {

            if (target == actor)
            {
                if(actor instanceof NPCActor)
                {
                    //TODO: проверка растояния
                    ((NPCActor) actor).getNpcAi().onTalk(this);
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
        System.out.println("Should send dialog. Dialog: "+dialog);
        sendPacket(new Dialog(dialog));
    }
}
