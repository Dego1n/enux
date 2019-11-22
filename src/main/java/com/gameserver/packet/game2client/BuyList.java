package com.gameserver.packet.game2client;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.item.Item;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;
import com.gameserver.template.item.BaseItem;

import java.util.List;
import java.util.Random;

public class BuyList extends AbstractSendablePacket implements IServerPacket {

    private List<Item> _items;
    //TODO: model buylist
    public BuyList(List<Item> items) {
        this._items = items;
        build();
    }

    private void build()
    {
        writeH(ServerPackets.BUYLIST);
        writeH(10);
        Random rnd = new Random();
        for(int i = 0; i < 10; i++)
        {
            writeD(rnd.nextInt(20) + 1);
            writeD(rnd.nextInt());
        }
        writeH(_items.size());
        for(Item item : _items)
        {
            writeD(item.getBaseItem().getId());
            writeD(item.getObjectId());
            writeD(item.getCount());
            writeD(rnd.nextInt());
        }
    }
}
