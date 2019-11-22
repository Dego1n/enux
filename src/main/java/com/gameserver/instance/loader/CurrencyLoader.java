package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.template.item.ArmorItem;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.item.CurrencyItem;
import com.gameserver.template.item.ItemSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyLoader {

    private static final Logger log = LoggerFactory.getLogger(CurrencyLoader.class);

    public static List<BaseItem> LoadCurrency()
    {
        List<BaseItem> list = new ArrayList<>();
        String currencyYaml = null;
        try {
            currencyYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "items/currency/currency.yaml"));
        } catch (IOException e) {
            log.error("Can't read currency yaml file");
            log.error(e.getMessage());
            System.exit(1);
        }
        if(currencyYaml == null)
        {
            log.error("Currency yaml is null");
            System.exit(1);
        }
        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(currencyYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> armor =  (Map<String, Object>)entry.getValue();

            int id = (int)armor.get("id");
            String name = (String)armor.get("name");

            list.add(new CurrencyItem(id,name));
        }

        return list;
    }
}
