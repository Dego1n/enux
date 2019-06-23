package com.gameserver.instance;

import com.gameserver.config.Config;
import com.gameserver.database.staticdata.Race;
import com.gameserver.template.NPC;
import com.gameserver.template.stats.BaseStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataEngine {

    private static final Logger log = LoggerFactory.getLogger(DataEngine.class);

    private static DataEngine _instance;

    public static DataEngine getInstance()
    {
        if(_instance == null)
            _instance = new DataEngine();

        return _instance;
    }

    private List<NPC> npcList;
    private List<BaseStats> baseStats;

    private DataEngine()
    {
        npcList = new ArrayList<>();
        baseStats = new ArrayList<>();

        log.info("Loaded {} NPC Data",LoadNPCData());
        log.info("Loaded {} PC Base Stats",LoadPCBaseStats());
    }

    private int LoadNPCData()
    {
        int count = 0;
        String npcYaml;
        try {
            npcYaml = new String(Files.readAllBytes(Paths.get(Config.DATAPACK_PATH + "npc/npc.yaml")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Can't read npc yaml file");
            log.error(e.getMessage());
            return -1;
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

            @SuppressWarnings("unchecked")
            Map<String, Object> collision = (Map<String,Object>)npc.get("collision");
            int collisionHeight = (int)collision.get("height");
            int collisionRadius = (int)collision.get("radius");
            npcList.add(new NPC(id,templateId,name,isFriendly,collisionHeight,collisionRadius, hp, respawnTime));
            count++;
        }

        return count;
    }

    private int LoadPCBaseStats()
    {
        int count = 0;
        String pcBaseStatsYaml;
        try {
            pcBaseStatsYaml = new String(Files.readAllBytes(Paths.get(Config.DATAPACK_PATH + "pc/pc_base_stats.yaml")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Can't read pc_base_stats.yml file");
            log.error(e.getMessage());
            return -1;
        }

        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(pcBaseStatsYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> pc =  (Map<String, Object>)entry.getValue();
            Race race = Race.valueOf((int)pc.get("race_id"));
            int collisionHeight = (int)pc.get("collisionHeight");
            int collisionRadius = (int)pc.get("collisionRadius");

            BaseStats pcbs = new BaseStats(race);
            pcbs.setCollisionHeight(collisionHeight);
            pcbs.setCollisionRadius(collisionRadius);
            baseStats.add(pcbs);
            count++;
        }

        return count;
    }

    public NPC getNPCById(int id) {
        for(NPC npc : npcList)
        {
            if(npc.getId() == id)
                return npc;
        }
        return null;
    }

    public BaseStats GetPCBaseStatsByRace(Race race)
    {
        for(BaseStats pcbs : baseStats)
        {
            if(pcbs.getRace() == race)
                return pcbs;
        }

        return null;
    }
}
