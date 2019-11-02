package com.gameserver.model.actor.npc;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootTableData {
    List<LootGroupData> groupData;

    public LootTableData(List<LootGroupData> groupData) {
        this.groupData = groupData;
    }

    public LootTableData()
    {
        groupData = new ArrayList<>();
    }

    public List<LootGroupData> getGroupData() {
        return groupData;
    }

    public List<Item> generateLoot()
    {
        List<Item> loot = new ArrayList<>();
        Random rnd = new Random();
        for(LootGroupData group : groupData)
        {
            double roll = rnd.nextFloat() * 100;
            if(roll >= group.chance)
            {
                for(LootItemData item: group.getItemData())
                {
                    roll = rnd.nextFloat() * 100;
                    if(roll >= item.chance)
                    {
                        loot.add(new Item(DataEngine.getInstance().getItemById(item.item_id)));
                    }
                }
            }
        }

        return loot;
    }

}
