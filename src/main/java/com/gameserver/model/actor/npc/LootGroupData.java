package com.gameserver.model.actor.npc;

import java.util.List;

public class LootGroupData {
    public double chance;

    private List<LootItemData> itemData;

    public LootGroupData(List<LootItemData> itemData, double chance) {
        this.itemData = itemData;
        this.chance = chance;
    }

    public List<LootItemData> getItemData() {
        return itemData;
    }

    public void setItemData(List<LootItemData> itemData) {
        this.itemData = itemData;
    }
}
