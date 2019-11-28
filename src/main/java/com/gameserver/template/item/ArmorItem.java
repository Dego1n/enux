package com.gameserver.template.item;

public class ArmorItem extends BaseItem {

    private final ItemSlot _slot;

    public ArmorItem(int id, String name, ItemSlot slot) {
        this(id,name,slot,0);
    }

    public ArmorItem(int id, String name, ItemSlot slot, int sellPrice) {
        this(id, name, slot, sellPrice, true);
    }
    public ArmorItem(int id, String name, ItemSlot slot, int sellPrice, boolean is_sellable) {
        this(id, name, slot, sellPrice, is_sellable, false);
    }
    public ArmorItem(int id, String name, ItemSlot slot, int sellPrice, boolean is_sellable, boolean is_stackable) {
        super(id, name, sellPrice, is_sellable, is_stackable);
        _slot = slot;
    }

    public ItemSlot getSlot() {
        return _slot;
    }
}
