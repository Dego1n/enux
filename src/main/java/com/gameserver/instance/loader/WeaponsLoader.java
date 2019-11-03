package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.item.WeaponItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponsLoader {

    private static final Logger log = LoggerFactory.getLogger(WeaponsLoader.class);

    public static List<BaseItem> LoadWeapons()
    {
        List<BaseItem> list = new ArrayList<>();
        String weaponsYaml = null;
        try {
            weaponsYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "items/weapons/weapons.yaml"));
        } catch (IOException e) {
            log.error("Can't read weapons yaml file");
            log.error(e.getMessage());
            System.exit(1);
        }
        if(weaponsYaml == null)
        {
            log.error("Weapons yaml is null");
            System.exit(1);
        }
        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(weaponsYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> weapon =  (Map<String, Object>)entry.getValue();

            int id = (int)weapon.get("id");
            String name = (String)weapon.get("name");

            list.add(new WeaponItem(id,name));
        }

        return list;
    }
}
