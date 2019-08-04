package com.gameserver.template.item;

public abstract class BaseItem {

    private int id;
    private String name;

    public BaseItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
