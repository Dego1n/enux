package com.gameserver.model.actor;

import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.IntentionAction;
import com.gameserver.model.actor.ai.base.intention.IntentionMoveTo;
import com.gameserver.model.actor.playable.equip.EquipInfo;
import com.gameserver.model.item.Item;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.*;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.item.WeaponItem;
import com.gameserver.template.stats.BaseStats;
import com.gameserver.util.math.xy.Math2d;

import java.util.ArrayList;
import java.util.List;

public class PlayableCharacter extends BaseActor {

    private ClientListenerThread clientListenerThread;

    private CharacterClass characterClass;
    private BaseStats baseStats;

    private List<Item> _inventory;

    private EquipInfo _equipInfo;

    private long currentExperience;

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
        setLevel(character.getLevel());
        setCurrentExperience(character.getExperience());
        setCharacterClass(character.getCharacterClass());
        baseStats = DataEngine.getInstance().GetPCBaseStatsByRace(character.getRace());

        _equipInfo = new EquipInfo();
        _inventory = new ArrayList<>();
        //TODO: remove after
        _inventory.add(new Item(DataEngine.getInstance().getItemById(1)));
        _inventory.add(new Item(DataEngine.getInstance().getItemById(2)));
        _inventory.add(new Item(DataEngine.getInstance().getItemById(1)));
        _inventory.add(new Item(DataEngine.getInstance().getItemById(2)));
        _inventory.add(new Item(DataEngine.getInstance().getItemById(1)));
        _equipInfo.setRightHand(_inventory.get(1));
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

    public BaseStats getBaseStats() {
        return baseStats;
    }

    public List<Item> getInventory() {
        return _inventory;
    }

    public EquipInfo getEquipInfo() {
        return _equipInfo;
    }

    public long getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(long currentExperience) {
        this.currentExperience = currentExperience;
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
                    if(Math2d.calculateBetweenTwoActorsIn2d(this,actor) <= 400)
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

    public void useItem(int objectId, boolean fromInventory) {
        Item item = getInventory().stream().filter(i -> (i.getObjectId() == objectId)).findFirst().orElse(null);
        if (item == null)
        {
            //TODO: security audit
            return;
        }

        _equipInfo.setRightHand(item);

        if(fromInventory) {
            sendPacket(new Inventory(_inventory, _equipInfo));
        }
        sendPacket(new PCActorInfo(this)); //TODO: also broadcast
    }
}
