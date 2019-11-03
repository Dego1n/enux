package com.gameserver.model.actor;

import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.IntentionAction;
import com.gameserver.model.actor.ai.base.intention.IntentionIdle;
import com.gameserver.model.actor.ai.base.intention.IntentionMoveTo;
import com.gameserver.model.actor.playable.equip.EquipInfo;
import com.gameserver.model.item.Item;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.*;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.RemoveActorTask;
import com.gameserver.task.actortask.ResetAttackCooldown;
import com.gameserver.task.actortask.SpawnActorTask;
import com.gameserver.template.stats.BaseStats;
import com.gameserver.util.math.xy.Math2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayableCharacter extends BaseActor {

    private static final Logger log = LoggerFactory.getLogger(PlayableCharacter.class);

    private ClientListenerThread clientListenerThread;

    private CharacterClass characterClass;
    private final BaseStats baseStats;

    private final List<Item> _inventory;

    private final EquipInfo _equipInfo;

    private int currentExperience;

    public PlayableCharacter(ClientListenerThread clientListenerThread, Character character)
    {
        super();

        setClientListenerThread(clientListenerThread);

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

    public int getCurrentExperience() {
        return currentExperience;
    }

    private void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }

    private void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public void sendPacket(AbstractSendablePacket packet)
    {
        clientListenerThread.sendPacket(packet);
    }

    private void sendPacketAndBroadcastToNearbyPlayers(AbstractSendablePacket packet)
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
                        _actorIntention.setIntention(new IntentionAction(actor));
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

    private void addExperience(int baseExperience) {
        this.setCurrentExperience(this.getCurrentExperience() + baseExperience);
        int level = DataEngine.getInstance().getLevelByExperience(this.getCurrentExperience());
        this.sendPacket(new SystemMessage("Level by Exp: "+level+". Your current level: "+this.getLevel()));
        if(level != this.getLevel())
        {
            this.setLevel(level);
            this.sendPacket(new SystemMessage("Level up! Your level is now:" +getLevel()));
        }
    }

    public boolean isConnected() {
        return clientListenerThread != null;
    }

    public void addItemToInventory(Item item)
    {
        _inventory.add(item);
    }

    public void say(String message) {
        System.out.println("client " + getName() + " trying to say: " + message);
        ActorSay actorSayPacket = new ActorSay(this, this.getName(), message);
        sendPacketAndBroadcastToNearbyPlayers(actorSayPacket);
    }

    @Override
    public void onRespawn() {
        log.warn("onRespawn is not implemented in PlayableCharacter");
    }

    public void attack(BaseActor target)
    {
        if (target instanceof NPCActor) {
            target.getAi().onAttacked(this);
        }
        setCanAttack(false);
        if (target.getCurrentHp() > 0) {
            float damage = calculateAttackDamageToTarget(target);
            target.setCurrentHp(target.getCurrentHp() - damage);
            sendPacket(new SystemMessage(target.getName() + " received " + damage + " damage!"));
            if (target.getCurrentHp() <= 0) {
                getActorIntention().setIntention(new IntentionIdle());
                sendPacket(new SystemMessage(target.getName() + " died!"));
                setTarget(null);
                target.getActorIntention().setIntention(new IntentionIdle());
                target.setDead(true);
                if (target instanceof NPCActor) {
                    NPCActor npcTarget = (NPCActor) target;
                    npcTarget.generateLootData();
                    addExperience(npcTarget.getBaseExperience());
                    sendPacket(new SystemMessage("You received " + npcTarget.getBaseExperience() + " experience"));
                    sendPacket(new SystemMessage("You current EXP:  " + getCurrentExperience()));
                    new Task(new RemoveActorTask(npcTarget), 10 * 1000);
                    new Task(new SpawnActorTask(npcTarget), npcTarget.getRespawnTime() * 1000);
                }
                sendPacketAndBroadcastToNearbyPlayers(new ActorDied(target));

            } else {
                sendPacketAndBroadcastToNearbyPlayers(new ActorInfo(target));
            }
        }
        sendPacketAndBroadcastToNearbyPlayers(new Attack(this, target));
        new Task(new ResetAttackCooldown(this), (int) ((1 / 0.8f) * 1000)); //TODO: 0.8f - attack speed, get from stats instead of const
    }

}
