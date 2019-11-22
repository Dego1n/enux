package com.gameserver.model.item;

import com.gameserver.factory.idfactory.ItemIdFactory;
import com.gameserver.template.item.BaseItem;

public class Item {

    private final int objectId;

    private final BaseItem baseItem;

    private int count;

    public Item(BaseItem baseItem) {
        this(baseItem,1);
    }

    public Item(BaseItem baseItem, int count) {
        objectId = ItemIdFactory.getInstance().getFreeId();
        this.baseItem = baseItem;
        this.count = count;
    }

    public int getObjectId() {
        return objectId;
    }

    public BaseItem getBaseItem() {
        return baseItem;
    }

    public int getItemId()
    {
        return baseItem.getId();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
