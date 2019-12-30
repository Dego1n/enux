package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.model.ability.Ability;
import com.gameserver.model.ability.AbilityTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbilitiesTreeLoader {
    private static final Logger log = LoggerFactory.getLogger(AbilitiesTreeLoader.class);

    public static List<AbilityTree> loadAbilitiesTree()
    {
        List<AbilityTree> abilities = new ArrayList<>();

        String abilitiesYaml;
        try {
            abilitiesYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "abilities_tree/abilities_tree.yaml"));
        } catch (IOException e) {
            log.error("Can't read abilities_tree yaml file");
            log.error(e.getMessage());
            System.exit(1);
            return abilities;
        }
        if(abilitiesYaml == null)
        {
            log.error("AbilitiesTree Yaml file is empty.");
            System.exit(1);
            return abilities;
        }
        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(abilitiesYaml);
        List<Map<String,Object>> classes = (List<Map<String, Object>>) object.get("classes");
        for(Map<String, Object> _class : classes)
        {
            AbilityTree abilityTree = new AbilityTree();
            abilityTree.class_id = (int) _class.get("class_id");
            int level = (int) _class.get("level");
            List<Map<String,Integer>> skills = (List<Map<String, Integer>>) _class.get("abilities");
            List<AbilityTree.AcquirableAbility> listAcquirableAbility = new ArrayList<>();
            for(Map<String,Integer> skill : skills)
            {
                AbilityTree.AcquirableAbility acquirableAbility = new AbilityTree.AcquirableAbility();
                acquirableAbility.ability_id = skill.get("ability_id");
                acquirableAbility.level = skill.get("level");
                acquirableAbility.required_sp = skill.get("required_sp");
                listAcquirableAbility.add(acquirableAbility);
            }
            abilityTree.addTreeAbilities(level, listAcquirableAbility);
            abilities.add(abilityTree);
        }
        return abilities;
    }
}
