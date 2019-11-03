package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ExperienceLoader {
    private static final Logger log = LoggerFactory.getLogger(ExperienceLoader.class);

    @SuppressWarnings("unchecked")
    public static Map<Integer, Integer> loadExperienceTable()
    {
        Map<Integer,Integer> experienceTable = new HashMap<>();
        
        String experienceYaml;
        try {
            experienceYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "stats/experience_table.yaml"));
        } catch (IOException e)
        {
            log.error("Can't read experience file.");
            log.error(e.getMessage());
            System.exit(1);
            return null;
        }

        Yaml yaml = new Yaml();
        Map<Object, Object> object = yaml.load(experienceYaml);

        Map<Integer, Integer> levels = ( Map<Integer, Integer>)object.get("Levels");
        for(Map.Entry<Integer, Integer> level : levels.entrySet())
        {
            experienceTable.put(level.getKey(), level.getValue());
        }
        return experienceTable;
    }
}
