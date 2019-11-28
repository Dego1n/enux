package com.gameserver.template.item;

public class CurrencyItem extends BaseItem {

    public CurrencyItem(int id, String name) {
        this(id, name,0);
    }
    public CurrencyItem(int id, String name, int sell_price) {
        this(id, name, sell_price, true);
    }
    public CurrencyItem(int id, String name, int sell_price, boolean is_sellable) {
        this(id, name, sell_price, is_sellable, false);
    }
    public CurrencyItem(int id, String name, int sell_price, boolean is_sellable, boolean is_stackable) {
        super(id, name, sell_price, is_sellable, is_stackable);
    }
}
