package com.gameserver.template.item;

public class WeaponItem extends BaseItem {

    public WeaponItem(int id, String name)
    {
        this(id,name,0);
    }
    public WeaponItem(int id, String name, int sell_price) {
        this(id, name, sell_price, true);
    }
    public WeaponItem(int id, String name, int sell_price, boolean is_sellable) {
        this(id, name, sell_price, is_sellable, false);
    }
    public WeaponItem(int id, String name, int sell_price, boolean is_sellable, boolean is_stackable) {
        super(id, name, sell_price, is_sellable, is_stackable);
    }
}
