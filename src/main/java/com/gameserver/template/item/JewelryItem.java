package com.gameserver.template.item;

public class JewelryItem extends BaseItem {

    private ItemSlot _slot;

    public JewelryItem(int id, String name, ItemSlot slot) {
        super(id, name);
        _slot = slot;
    }

    public ItemSlot getSlot() {
        return _slot;
    }
}
