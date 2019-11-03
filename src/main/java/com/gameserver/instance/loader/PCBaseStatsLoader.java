package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.database.staticdata.Race;
import com.gameserver.template.stats.BaseStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PCBaseStatsLoader {
    private static final Logger log = LoggerFactory.getLogger(PCBaseStatsLoader.class);

    public static List<BaseStats> LoadPCBaseStats()
    {
        List<BaseStats> stats = new ArrayList<>();
        String pcBaseStatsYaml = null;
        try {
            pcBaseStatsYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "pc/pc_base_stats.yaml"));
        } catch (IOException e) {
            log.error("Can't read pc_base_stats.yml file");
            log.error(e.getMessage());
            System.exit(1);
        }
        if(pcBaseStatsYaml == null)
        {
            log.error("Base stats yaml is null");
            System.exit(1);
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
            stats.add(pcbs);
        }

        return stats;
    }
}
