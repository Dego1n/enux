package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.model.actor.npc.LootGroupData;
import com.gameserver.model.actor.npc.LootItemData;
import com.gameserver.model.actor.npc.LootTableData;
import com.gameserver.template.NPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NPCLoader {
    private static final Logger log = LoggerFactory.getLogger(NPCLoader.class);

    @SuppressWarnings("unchecked")
    public static List<NPC> LoadNpcData()
    {
        List<NPC> npcs = new ArrayList<>();
        String npcYaml;
        try {
            npcYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "npc/npc.yaml"));
        } catch (IOException e) {
            log.error("Can't read npc yaml file");
            log.error(e.getMessage());
            System.exit(1);
            return npcs;
        }
        if(npcYaml == null)
        {
            log.error("NPC Yaml file is empty.");
            System.exit(1);
            return npcs;
        }

        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(npcYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> npc =  (Map<String, Object>)entry.getValue();

            int id = (int)npc.get("id");
            String name = (String)npc.get("name");
            int templateId = (int)npc.get("template_id");
            boolean isFriendly = (boolean)npc.get("is_friendly");
            double hp = (double)npc.get("hp");
            int respawnTime = (int)npc.get("respawn_time");

            int baseExperience = (int) Optional.ofNullable(npc.get("base_experience")).orElse(0);

            @SuppressWarnings("unchecked")
            Map<String, Object> collision = (Map<String,Object>)npc.get("collision");
            int collisionHeight = (int)collision.get("height");
            int collisionRadius = (int)collision.get("radius");
            LootTableData lootTableData = new LootTableData();
            Map<Object, Object> lootData = (Map<Object, Object>)npc.get("loot");
            if(lootData != null)
            {
                List<Map<String, Object>> groups = (List<Map<String, Object>>) lootData.get("groups");
                List<LootGroupData> lootGroupDataList = new ArrayList<>();
                for(Map<String, Object> group : groups)
                {
                    double group_chance = (double)group.get("chance");
                    List<LootItemData> lootItemData = new ArrayList<>();
                    for(Map<String, Object> item : (List<Map<String, Object>>)group.get("items"))
                    {
                        LootItemData lootItem = new LootItemData();
                        lootItem.item_id = (int) item.get("id");
                        lootItem.chance = (double)item.get("chance");
                        lootItem.min_count = (int)item.get("min_count");
                        lootItem.max_count = (int)item.get("max_count");
                        lootItemData.add(lootItem);
                    }
                    lootGroupDataList.add(new LootGroupData(lootItemData, group_chance));
                }
                lootTableData = new LootTableData(lootGroupDataList);
            }
            npcs.add(
                    new NPC(
                            id,
                            templateId,
                            name,
                            isFriendly,
                            collisionHeight,
                            collisionRadius,
                            hp,
                            respawnTime,
                            baseExperience,
                            lootTableData
                    )
            );
        }
        return npcs;
    }
}
