package com.gameserver.model.actor;

import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.*;
import com.gameserver.model.actor.playable.equip.EquipInfo;
import com.gameserver.model.item.Item;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.*;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AbilityCastEnd;
import com.gameserver.task.actortask.RemoveActorTask;
import com.gameserver.task.actortask.ResetAttackCooldown;
import com.gameserver.task.actortask.SpawnActorTask;
import com.gameserver.template.item.ArmorItem;
import com.gameserver.template.item.JewelryItem;
import com.gameserver.template.item.WeaponItem;
import com.gameserver.template.stats.BaseStats;
import com.gameserver.util.math.xy.Math2d;
import com.gameserver.util.math.xyz.Math3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayableCharacter extends BaseActor {

    private static final Logger log = LoggerFactory.getLogger(PlayableCharacter.class);

    private ClientListenerThread clientListenerThread;

    private CharacterClass characterClass;
    private final BaseStats baseStats;

    private final List<Item> _inventory;

    private final EquipInfo _equipInfo;

    private int currentExperience;

    private Map<Ability, Integer> _abilities;

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
        _abilities = new HashMap<>();

        //TODO: remove after
        for(int i = 1; i <= 22; i++) {
            _inventory.add(new Item(DataEngine.getInstance().getItemById(i)));
        }
        _equipInfo.setRightHand(_inventory.get(1));

        //TODO: remove after
        //TODO: Abilities
        _abilities.put(DataEngine.getInstance().getAbilityById(1),1);
        _abilities.put(DataEngine.getInstance().getAbilityById(2),1);
        _abilities.put(DataEngine.getInstance().getAbilityById(3),1);
        _abilities.put(DataEngine.getInstance().getAbilityById(4),1);
        _abilities.put(DataEngine.getInstance().getAbilityById(5),1);
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

    public Map<Ability, Integer> getAbilities() {
        return _abilities;
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
        if(item.getBaseItem() instanceof WeaponItem) {
            _equipInfo.setRightHand(item);
        }
        else if(item.getBaseItem() instanceof ArmorItem)
        {
            switch (((ArmorItem) item.getBaseItem()).getSlot())
            {
                case UPPER_ARMOR:
                    _equipInfo.setUpperArmor(item);
                    break;
                case LOWER_ARMOR:
                    _equipInfo.setLowerArmor(item);
                    break;
                case HELMET:
                    _equipInfo.setHelmet(item);
                    break;
                case GLOVES:
                    _equipInfo.setGloves(item);
                    break;
                case BOOTS:
                    _equipInfo.setBoots(item);
                    break;
                case BELT:
                    _equipInfo.setBelt(item);
                    break;
            }
        }
        else if(item.getBaseItem() instanceof JewelryItem)
        {
            switch (((JewelryItem) item.getBaseItem()).getSlot())
            {
                case Earring:
                    if(_equipInfo.getEarringSecond() == null)
                        _equipInfo.setEarringSecond(item);
                    else
                        _equipInfo.setEarringFirst(item);
                    break;
                case Ring:
                    if(_equipInfo.getRingSecond() == null)
                        _equipInfo.setRingSecond(item);
                    else
                        _equipInfo.setRingFirst(item);
                    break;
                case Necklace:
                    _equipInfo.setNecklace(item);
            }
        }

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
        setCanAttack(false);
        if (target.getCurrentHp() > 0) {
            double damage = calculateAttackDamageToTarget(target);
            doDamage(target, damage);
        }
        sendPacketAndBroadcastToNearbyPlayers(new Attack(this, target));
        new Task(new ResetAttackCooldown(this), (int) ((1 / 0.8f) * 1000)); //TODO: 0.8f - attack speed, get from stats instead of const
    }

    public void requestUseAbility(Ability ability)
    {
        if (target == null)
            return;
        if(target.isDead())
            return;

        if(Math3d.calculateBetweenTwoActors(this,target,true) <= ability.getAbilityByLevel(_abilities.get(ability)).getRange()) {
            _actorIntention.setIntention(new IntentionCast(target, ability));
        }
        else
        {
            sendPacket(new SystemMessage("Target is not in range!!!"));
        }
    }
    @Override
    public void useAbility(BaseActor target, Ability ability) {
            this.setCanAttack(false);
            float abilityCastTime = ability.getAbilityByLevel(_abilities.get(ability)).getCastTime();

            sendPacketAndBroadcastToNearbyPlayers(new UseAbility(this, target, ability, abilityCastTime));
            _tasks.add(new Task(new AbilityCastEnd(this, target, ability), (int) abilityCastTime * 1000).getTask());
            _actorIntention.setIntention(new IntentionAttack(target));

    }

    @Override
    public void doDamage(BaseActor target, double damage) {
        if (target instanceof NPCActor) {
            target.getAi().onAttacked(this);
        }
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

    @Override
    public void onAbilityCastEnd(AbilityCastEnd abilityCastEnd) {
        BaseActor target = abilityCastEnd.getTarget();
        Ability ability = abilityCastEnd.getAbility();

        _tasks.remove(abilityCastEnd);

        sendPacket(new SystemMessage("onAbilityCastEnd target: "+target.getName()+ "; ability_id: "+ability.getId()));
        doDamage(target, ability.getAbilityByLevel(_abilities.get(ability)).getCastTime()*20);
        this.setCanAttack(true);
    }

}
