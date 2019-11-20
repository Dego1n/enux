package com.gameserver.template.item;

public class ArmorItem extends BaseItem {

    private ItemSlot _slot;

    public ArmorItem(int id, String name, ItemSlot slot) {
        super(id, name);
        _slot = slot;
    }

    public ItemSlot getSlot() {
        return _slot;
    }
}
