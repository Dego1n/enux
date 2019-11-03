package com.gameserver.model.actor.npc;

import java.util.List;

public class LootGroupData {
    public final double chance;

    private final List<LootItemData> itemData;

    public LootGroupData(List<LootItemData> itemData, double chance) {
        this.itemData = itemData;
        this.chance = chance;
    }

    public List<LootItemData> getItemData() {
        return itemData;
    }

}
