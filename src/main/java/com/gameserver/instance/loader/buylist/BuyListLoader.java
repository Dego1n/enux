package com.gameserver.instance.loader.buylist;

import com.gameserver.config.Config;
import com.gameserver.template.buylist.BuyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyListLoader {
    private static final Logger log = LoggerFactory.getLogger(BuyListLoader.class);

    @SuppressWarnings("unchecked")
    public static List<BuyList> LoadBuyListData()
    {
        List<BuyList> buyLists = new ArrayList<>();
        String buyListYaml;
        try {
            buyListYaml = Files.readString(Paths.get(Config.DATAPACK_PATH + "buylist/buylists.yaml"));
        } catch (IOException e) {
            log.error("Can't read buy list yaml file");
            log.error(e.getMessage());
            System.exit(1);
            return buyLists;
        }
        if(buyListYaml == null)
        {
            log.error("BuyList Yaml file is empty.");
            System.exit(1);
            return buyLists;
        }

        Yaml yaml = new Yaml();
        List<Object> objects = yaml.load(buyListYaml);
        for(Object object: objects)
        {
            Map<String, Object> buylist_data = (Map<String, Object>)object;
            int buylist_id = (int) buylist_data.get("buylist_id");
            int currency_id = (int) buylist_data.get("currency_id");
            List<Integer> allowed_npcs = (List<Integer>) buylist_data.get("allowed_npc");
            List<Map<String, Integer>> items = (List<Map<String, Integer>>) buylist_data.get("items");
            List<BuyList.BuyListItem> itemsList = new ArrayList<>();
            for(Map<String,Integer> entry : items)
            {
                itemsList.add(new BuyList.BuyListItem(entry.get("id"), entry.get("price")));
            }
            buyLists.add(new BuyList(buylist_id, currency_id, allowed_npcs, itemsList));
        }
        return buyLists;
    }
}
