package com.gameserver.model.item;

import com.gameserver.factory.idfactory.ItemIdFactory;
import com.gameserver.template.item.BaseItem;

public class Item {

    private final int objectId;

    private final BaseItem baseItem;

    public Item(BaseItem baseItem) {
        objectId = ItemIdFactory.getInstance().getFreeId();
        this.baseItem = baseItem;
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

}
