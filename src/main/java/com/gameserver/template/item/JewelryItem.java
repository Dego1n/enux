package com.gameserver.template.item;

public class JewelryItem extends BaseItem {

    private final ItemSlot _slot;

    public JewelryItem(int id, String name, ItemSlot slot) {
        this(id, name, slot, 0);
    }

    public JewelryItem(int id, String name, ItemSlot slot, int sell_price) {
        this(id, name, slot,sell_price, true);
    }

    public JewelryItem(int id, String name, ItemSlot slot, int sell_price, boolean is_sellable) {
        this(id, name, slot, sell_price, is_sellable, false);
    }
    public JewelryItem(int id, String name, ItemSlot slot, int sell_price, boolean is_sellable, boolean is_stackable) {
        super(id, name, sell_price, is_sellable, is_stackable);
        _slot = slot;
    }

    public ItemSlot getSlot() {
        return _slot;
    }
}
