package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.model.ability.Ability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbilitiesLoader {
    private static final Logger log = LoggerFactory.getLogger(AbilitiesLoader.class);

    public static List<Ability> loadAbilities()
    {
        List<Ability> abilities = new ArrayList<>();

        String abilitiesYaml = null;
        try {
            abilitiesYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "abilities/abilities.yaml"));
        } catch (IOException e) {
            log.error("Can't read abilities yaml file");
            log.error(e.getMessage());
            System.exit(1);
            return abilities;
        }
        if(abilitiesYaml == null)
        {
            log.error("Abilities Yaml file is empty.");
            System.exit(1);
            return abilities;
        }
        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(abilitiesYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> ability =  (Map<String, Object>)entry.getValue();

            int id = (int)ability.get("id");
            List<Map<String, Object>> levelsAbility = (List<Map<String, Object>>) ability.get("levels");

            List<Ability.AbilityLevel> levels = new ArrayList<>();
            for(Map<String,Object> levelAbility : levelsAbility)
            {
                int level = (int) levelAbility.get("level");
                int manaCost = (int) levelAbility.get("mana_cost");
                float castTime = (float)(double) levelAbility.get("cast_time");
                float range = (float)(double) levelAbility.get("range");
                levels.add(new Ability.AbilityLevel(level,manaCost, castTime, range));
            }
            abilities.add(new Ability(id,levels));

        }
        return abilities;
    }
}
