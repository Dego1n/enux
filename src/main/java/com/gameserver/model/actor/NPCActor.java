package com.gameserver.model.actor;

import com.gameserver.config.Config;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.ai.base.intention.IntentionIdle;
import com.gameserver.model.actor.ai.type.AttackableAI;
import com.gameserver.model.item.Item;
import com.gameserver.packet.game2client.*;
import com.gameserver.scripting.ai.npc.NpcAi;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AbilityCastEnd;
import com.gameserver.task.actortask.RemoveActorTask;
import com.gameserver.task.actortask.ResetAttackCooldown;
import com.gameserver.task.actortask.SpawnActorTask;
import com.gameserver.template.NPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class NPCActor extends BaseActor {

    private static final Logger log = LoggerFactory.getLogger(NPCActor.class);

    private NpcAi npcAi;

    private final int respawnTime;

    private int baseExperience;

    private final int npc_id;

    private List<Item> lootData;
    private double maxHp;

    public NPCActor(int npc_id, int loc_x, int loc_y, int loc_z)
    {
        super();
        this.npc_id = npc_id;
        NPC npc = DataEngine.getInstance().getNPCById(npc_id);
        setLocationX(loc_x);
        setLocationY(loc_y);
        setLocationZ(loc_z);
        setFriendly(npc.isFriendly());
        if(!npc.isFriendly())
        {
            setAi(new AttackableAI(this));
        }
        setName(npc.getName());
        setTemplateId(npc.getTemplateId());
        setCollisionHeight(npc.getCollisionHeight());
        setCollisionRadius(npc.getCollisionRadius());
        setCurrentHp(npc.getHp());
        maxHp = npc.getHp();
        setBaseExperience(npc.getBaseExperience());
        respawnTime = npc.getRespawnTime();

        String path = Config.DATAPACK_PATH + "scripts/ai/npc/"+npc_id;

        if(new File(path).exists())
        {
            npcAi = new NpcAi(this,path);
        }
        else
        {
            log.warn("Not found AI script for npc id: {}",npc_id);
        }
        stats = npc.getStats();
    }

    public NpcAi getNpcAi() {
        return npcAi;
    }

    int getRespawnTime() {
        return respawnTime;
    }

    int getBaseExperience() {
        return baseExperience;
    }

    private void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public void generateLootData()
    {
        lootData = DataEngine.getInstance().getNPCById(npc_id).getLootTableData().generateLoot();
    }

    public List<Item> getLootData() {
        return lootData;
    }

    public void lootItem(PlayableCharacter character, int item_object_id)
    {
        Iterator<Item> items = lootData.iterator();

        while(items.hasNext())
        {
            Item item = items.next();
            if(item.getObjectId() == item_object_id)
            {
                character.addItemToInventory(item);
                items.remove();
            }
        }
    }

    public void attack(BaseActor target)
    {
        setCanAttack(false);
        if (target.getCurrentHp() > 0) {
            if(target.actorHitTarget(target)) {
                boolean critical = actorHitCritical();
                double damage = calculateAttackDamageToTarget(target, critical);
                doDamage(target, damage);
            }
            else
            {
                if(target instanceof PlayableCharacter)
                    ((PlayableCharacter) target).sendPacket(new SystemMessage(this.getName()+" attack missed"));
            }
        }
        broadcastPacket(new Attack(this, target));
        new Task(new ResetAttackCooldown(this), (int) ((1 / (stats.getAttackSpeed() /1000)) * 1000));
    }

    @Override
    public void useAbility(BaseActor target, Ability ability) {

    }

    @Override
    public void doDamage(BaseActor target, double damage) {
        if (target instanceof NPCActor) {
            target.getAi().onAttacked(this);
        }
        target.setCurrentHp(target.getCurrentHp() - damage);
        if (target.getCurrentHp() < 0) {
            getActorIntention().setIntention(new IntentionIdle());
            setTarget(null);
            if (target instanceof NPCActor) {
                ((NPCActor) target).generateLootData();
            }

            broadcastPacket(new ActorDied(target));
            if (target instanceof NPCActor) {
                new Task(new RemoveActorTask(target), 10 * 1000);
                new Task(new SpawnActorTask(target), (((NPCActor) target).getRespawnTime()) * 1000);
                target.setDead(true);
                target.getActorIntention().setIntention(new IntentionIdle());
            }
        } else {
            broadcastPacket(new ActorInfo(target));
            if (target instanceof PlayableCharacter)
            {
                ((PlayableCharacter) target).sendPacket(new StatusInfo((PlayableCharacter) target));
            }
            target.doRegenTaskIfNeeded();
        }
    }

    @Override
    public void onAbilityCastEnd(AbilityCastEnd abilityCastEnd) {

    }

    @Override
    public double getMaxHp() {
        return maxHp;
    }

    @Override
    public double getMaxMp() {
        return 0;
    }

    @Override
    public double getHpRegenRate() {
        return 1;
    }

    @Override
    public double getMpRegenRate() {
        return 1;
    }

    @Override
    public void recalculateStats(boolean sendToClient) {
        log.warn("RecalculateStats is not implemented in NPCActor");

    }

    @Override
    public void say(String message) {
        log.warn("Say method is not implemented in NPCActor");
    }

    public void onRespawn() {
        setCurrentHp(getMaxHp());
        if (getAi() != null) {
            getAi().resetAi();
        }
        setDead(false);
    }
}
