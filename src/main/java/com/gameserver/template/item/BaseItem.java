package com.gameserver.template.item;

public abstract class BaseItem {

    private final int id;
    private final String name;

    BaseItem(int id, String name) {
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
