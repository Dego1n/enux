package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.template.item.ArmorItem;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.item.ItemSlot;
import com.gameserver.template.item.JewelryItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JewelryLoader {

    private static final Logger log = LoggerFactory.getLogger(JewelryLoader.class);

    public static List<BaseItem> LoadJewelry()
    {
        List<BaseItem> list = new ArrayList<>();
        String jewelryYaml = null;
        try {
            jewelryYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "items/jewelry/jewelry.yaml"));
        } catch (IOException e) {
            log.error("Can't read jewelry yaml file");
            log.error(e.getMessage());
            System.exit(1);
        }
        if(jewelryYaml == null)
        {
            log.error("jewelry yaml is null");
            System.exit(1);
        }
        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(jewelryYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> jewelry =  (Map<String, Object>)entry.getValue();

            int id = (int)jewelry.get("id");
            String name = (String)jewelry.get("name");
            ItemSlot slot = null;
            switch ((String)jewelry.get("slot"))
            {
                case "Earring":
                    slot = ItemSlot.Earring;
                    break;
                case "Ring":
                    slot = ItemSlot.Ring;
                    break;
                case "Necklace":
                    slot = ItemSlot.Necklace;
                    break;
                default:
                    log.error("Cant get item slot for jewelry_id: "+id);
                    System.exit(1);
            }

            list.add(new JewelryItem(id,name, slot));
        }

        return list;
    }
}
