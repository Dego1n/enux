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
import com.gameserver.task.actortask.*;
import com.gameserver.template.item.ArmorItem;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.item.JewelryItem;
import com.gameserver.template.item.WeaponItem;
import com.gameserver.template.stats.BaseStats;
import com.gameserver.template.stats.StatModifier;
import com.gameserver.template.stats.Stats;
import com.gameserver.template.stats.enums.StatModifierOperation;
import com.gameserver.template.stats.enums.StatModifierType;
import com.gameserver.util.math.xy.Math2d;
import com.gameserver.util.math.xyz.Math3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
            if(i == 10 || i == 4)
                _inventory.add(new Item(DataEngine.getInstance().getItemById(i),10));
            else
            _inventory.add(new Item(DataEngine.getInstance().getItemById(i)));
        }
        _inventory.add(new Item(DataEngine.getInstance().getItemById(23),475));
        _equipInfo.setRightHand(_inventory.get(1));

        //TODO: remove after
        //TODO: Abilities
//        _abilities.put(DataEngine.getInstance().getAbilityById(1),1);
//        _abilities.put(DataEngine.getInstance().getAbilityById(2),1);
//        _abilities.put(DataEngine.getInstance().getAbilityById(3),1);
//        _abilities.put(DataEngine.getInstance().getAbilityById(4),1);
//        _abilities.put(DataEngine.getInstance().getAbilityById(5),1);
        stats = new Stats(baseStats);
        recalculateStats(false);

        //TODO: grab from database?
        setCurrentHp(getMaxHp());
        setCurrentMp(getMaxMp());
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
    public Item getItemFromInventoryById(int item_id)
    {
        for(Item item : getInventory())
        {
            if(item.getItemId() == item_id)
                return item;
        }
        return null;
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

    public void action(int objectId, boolean isShiftPressed) {
        BaseActor actor = World.getInstance().getActorByObjectId(objectId);
        if(actor != null) {

            if (target == actor)
            {
                if(actor instanceof NPCActor)
                {
                    if(isShiftPressed) //TODO: Проверять если админ
                    {
                        sendPacket(new SpecialActorInfo(target));
                        return;
                    }
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
        recalculateStats(true); //TODO: do not recalculate if not needed...
        sendPacket(new Inventory(_inventory, _equipInfo));
        //sendPacket(new PCActorInfo(this)); //TODO: also broadcast
    }
    public void acquireAbility(int ability_id)
    {
        //TODO: IMPORTANT
        //TODO: SECURITY AUDIT
        //TODO: Проверять в AbilityTree может ли персонаж выучить новую абилку!!!

        Ability ability = DataEngine.getInstance().getAbilityById(ability_id);
        Integer currentAbilityLevel = this.getAbilities().get(ability);
        if(currentAbilityLevel == null)
        {
            this.getAbilities().put(ability,1);
        }
        else
        {
            if(ability.getAbilityByLevel(currentAbilityLevel + 1) != null)
            {
                this.getAbilities().remove(ability);
                this.getAbilities().put(ability,currentAbilityLevel+1);
            }
            else
            {
                //TODO: Security Audit
                log.warn("Client requested acquire ability with non existent level");
            }
        }
        System.out.println("Sending new abilities list");
        sendPacket(new AbilitiesList(this));
    }
    public int getAbilityLevel(int ability_id)
    {
        Ability ability = DataEngine.getInstance().getAbilityById(ability_id);
        Integer currentAbilityLevel = this.getAbilities().get(ability);
        if(currentAbilityLevel != null)
            return currentAbilityLevel;
        return -1;
    }
    public void addExperience(int baseExperience) {
        this.setCurrentExperience(this.getCurrentExperience() + baseExperience);
        int level = DataEngine.getInstance().getLevelByExperience(this.getCurrentExperience());
        this.sendPacket(new SystemMessage("Level by Exp: "+level+". Your current level: "+this.getLevel()));
        if(level != this.getLevel())
        {
            this.setLevel(level);
            this.sendPacket(new SystemMessage("Level up! Your level is now:" +getLevel()));
            this.recalculateStats(true);
        }
        this.sendPacket(new StatusInfo(this));
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
            if(target.actorHitTarget(target)) {
                boolean critical = actorHitCritical();
                double damage = calculateAttackDamageToTarget(target, critical);
                if(critical)
                    sendPacket(new SystemMessage("Critical hit!"));
                doDamage(target, damage);
            }
            else
            {
                sendPacket(new SystemMessage("Your attack was missed!"));
            }
        }
        sendPacketAndBroadcastToNearbyPlayers(new Attack(this, target));
        new Task(new ResetAttackCooldown(this), (int) ((1 / (stats.getAttackSpeed() /1000)) * 1000));
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
    public void giveItem(BaseItem item, int count)
    {
        if(item.isStackable())
        {
            Item inventoryItem = getInventory().stream().filter(i -> i.getItemId() == item.getId()).findFirst().orElse(null);
            if(inventoryItem == null)
            {
                _inventory.add(new Item(item, count));
            }
            else
            {
                inventoryItem.setCount(inventoryItem.getCount() + count);
            }
        }
        else
        {
            _inventory.add(new Item(item, count));
        }
        sendPacket(new Inventory(getInventory(),getEquipInfo()));
    }
    public void showBuyList(int buylistId)
    {
        sendPacket(new BuyList(DataEngine.getInstance().getBuyListById(buylistId),getInventory()));
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

    @Override
    public double getMaxHp() {
        return stats.getMaxHp();
    }

    @Override
    public void recalculateStats(boolean sendToClient) {
        List<StatModifier> statModifiers = getEquipInfo().getAllEquipedStatsModifiers();

        //Считаем HP
        double _hp = baseStats.getLevelStats().get(this.getLevel()).getBaseHp() * stats.getConMod();
        stats.setMaxHp(_hp);
        if(getCurrentHp() > _hp)
        {
            setCurrentHp(_hp);
        }

        //Считаем MP
        double _mp = baseStats.getLevelStats().get(this.getLevel()).getBaseMp() * stats.getMenMod();
        stats.setMaxMp(_mp);
        if(getCurrentMp() > _mp)
        {
            setCurrentMp(_mp);
        }

        //Считаем HP regen
        double _hpReg = baseStats.getLevelStats().get(this.getLevel()).getBaseHpRegen() * ((getLevel()+89)/100.0)*stats.getConMod();
        stats.setHpRegen(Math.max(_hpReg,0));

        //Считаем MP regen
        double _mpReg = baseStats.getLevelStats().get(this.getLevel()).getBaseMpRegen() * ((getLevel()+89)/100.0)*stats.getMenMod();
        stats.setMpRegen(Math.max(_mpReg,0));

        //Считаем phys attack
        double itemsPAtk = 1;
        for(StatModifier modifier : statModifiers)
        {
            if(modifier.getType() == StatModifierType.PHYSICAL_ATTACK && modifier.getOperation() == StatModifierOperation.ADD)
                itemsPAtk += modifier.getValue();
        }
        double physAttack = itemsPAtk * ((getLevel()+89)/100.0) * stats.getStrMod() + baseStats.getPhysicalAttack();
        stats.setPhysicalAttack(Math.max(physAttack,0));

        //Считаем phys defence
        /* TODO for mystic null slots should be lower*/

        double itemsPdef = 0;
        for(StatModifier modifier : statModifiers)
        {
            if(modifier.getType() == StatModifierType.PHYSICAL_DEFENCE && modifier.getOperation() == StatModifierOperation.ADD)
                itemsPdef += modifier.getValue();
        }
        if(getEquipInfo().getHelmet() == null) itemsPdef += 12;
        if(getEquipInfo().getUpperArmor() == null) itemsPdef += 31;
        if(getEquipInfo().getLowerArmor() == null) itemsPdef += 18;
        if(getEquipInfo().getGloves() == null) itemsPdef += 8;
        if(getEquipInfo().getBoots() == null) itemsPdef += 7;
        if(getEquipInfo().getBelt() == null) itemsPdef += 5;
        double physDefence = (baseStats.getPhysicalDefence()+itemsPdef)*((getLevel()+89)/100.0);
        stats.setPhysicalDefence(Math.max(physDefence,0));

        //Считаем Evasion и accuracy
        double val = Math.sqrt(baseStats.getDex()) * 6 + getLevel();
        stats.setEvasion(val);
        stats.setAccuracy(val);

        //Считаем Attack Speed
        double attackSpeed = stats.getDexMod() * baseStats.getAttackSpeed();
        stats.setAttackSpeed(attackSpeed);

        //Считаем Move Speed
        double moveSpeed = stats.getDexMod() * baseStats.getSpeed();
        stats.setMoveSpeed(moveSpeed);

        //Считаем Critical
        double critical = stats.getDexMod() * 10;
        for(StatModifier modifier : statModifiers)
        {
            if(modifier.getType() == StatModifierType.CRIT_RATE && modifier.getOperation() == StatModifierOperation.MULTIPLY)
                critical *= modifier.getValue();
        }
        stats.setCritical(critical);

        if(sendToClient) {
            sendPacket(new StatusInfo(this));
            sendPacket(new PCActorInfo(this));
        }

        doRegenTaskIfNeeded();
    }

    @Override
    public double getMaxMp() {
        return stats.getMaxMp();
    }

    @Override
    public double getHpRegenRate() {
        return stats.getHpRegen();
    }

    @Override
    public double getMpRegenRate() {
        return stats.getMpRegen();
    }
}
