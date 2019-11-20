package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.template.item.ArmorItem;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.item.ItemSlot;
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

public class ArmorLoader {

    private static final Logger log = LoggerFactory.getLogger(ArmorLoader.class);

    public static List<BaseItem> LoadArmor()
    {
        List<BaseItem> list = new ArrayList<>();
        String armorYaml = null;
        try {
            armorYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "items/armor/armor.yaml"));
        } catch (IOException e) {
            log.error("Can't read armor yaml file");
            log.error(e.getMessage());
            System.exit(1);
        }
        if(armorYaml == null)
        {
            log.error("Armor yaml is null");
            System.exit(1);
        }
        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(armorYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> armor =  (Map<String, Object>)entry.getValue();

            int id = (int)armor.get("id");
            String name = (String)armor.get("name");
            ItemSlot slot = null;
            switch ((String)armor.get("slot"))
            {
                case "UpperArmor":
                    slot = ItemSlot.UPPER_ARMOR;
                    break;
                case "LowerArmor":
                    slot = ItemSlot.LOWER_ARMOR;
                    break;
                case "Helmet":
                    slot = ItemSlot.HELMET;
                    break;
                case "Gloves":
                    slot = ItemSlot.GLOVES;
                    break;
                case "Boots":
                    slot = ItemSlot.BOOTS;
                    break;
                case "Belt":
                    slot = ItemSlot.BELT;
                    break;
                default:
                    log.error("Cant get item slot for armor_id: "+id);
                    System.exit(1);
            }

            list.add(new ArmorItem(id,name, slot));
        }

        return list;
    }
}
