package com.gameserver.template.item;

public abstract class BaseItem {

    private final int id;
    private final String name;
    private final int sell_price;
    private final boolean is_sellable;
    private final boolean is_stackable;

    BaseItem(int id, String name, int sell_price) {
        this(id,name,sell_price,true, false);
    }

    BaseItem(int id, String name, int sell_price, boolean is_sellable)
    {
        this(id,name,sell_price,true, false);
    }

    BaseItem(int id, String name, int sell_price, boolean is_sellable, boolean is_stackable)
    {
        this.id = id;
        this.name = name;
        this.sell_price = sell_price;
        this.is_sellable = is_sellable;
        this.is_stackable = is_stackable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sell_price;
    }

    public boolean isSellable() {
        return is_sellable;
    }

    public boolean isStackable() {
        return is_stackable;
    }
}
