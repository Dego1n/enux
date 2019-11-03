package com.gameserver.model.actor;

import com.gameserver.config.Config;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.ai.type.AttackableAI;
import com.gameserver.model.item.Item;
import com.gameserver.scripting.ai.npc.NpcAi;
import com.gameserver.template.NPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class NPCActor extends BaseActor {

    private static final Logger log = LoggerFactory.getLogger(NPCActor.class);

    private NpcAi npcAi;

    private int respawnTime;

    private int baseExperience;

    private int npc_id;

    private int orig_x;

    private int orig_y;

    private int orig_z;

    private List<Item> lootData;

    public NPCActor(int npc_id, int loc_x, int loc_y, int loc_z)
    {
        super();
        this.npc_id = npc_id;
        this.orig_x = loc_x;
        this.orig_y = loc_y;
        this.orig_z = loc_z;
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
        setMaxHp(npc.getHp());
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
    }

    public NpcAi getNpcAi() {
        return npcAi;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(int respawnTime) {
        this.respawnTime = respawnTime;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
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
}
