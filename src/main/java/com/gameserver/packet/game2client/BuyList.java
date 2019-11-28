package com.gameserver.packet.game2client;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.item.Item;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.List;
import java.util.stream.Collectors;

public class BuyList extends AbstractSendablePacket implements IServerPacket {

    private final com.gameserver.template.buylist.BuyList _buyList;
    private final List<Item> _items;
    public BuyList(com.gameserver.template.buylist.BuyList buyList,  List<Item> items) {
        this._buyList = buyList;
        this._items = items;
        build();
    }

    private void build()
    {
        writeH(ServerPackets.BUYLIST);

        writeD(_buyList.getBuyListId());
        writeD(_buyList.getCurrencyId()); //Currency ID
        writeH(_buyList.getItems().size());
        for(com.gameserver.template.buylist.BuyList.BuyListItem buyListItem : _buyList.getItems())
        {
            writeD(buyListItem.getItemId());
            writeD(buyListItem.getPrice());
            writeH(DataEngine.getInstance().getItemById(buyListItem.getItemId()).isStackable() ? 1 : 0);
        }
        List<Item> itemsToSend = _items.stream().filter(item -> item.getBaseItem().isSellable()).collect(Collectors.toList());
        System.out.println(_items);
        System.out.println(itemsToSend);
        writeH(itemsToSend.size());
        for(Item item : itemsToSend)
        {
            writeD(item.getBaseItem().getId());
            writeD(item.getObjectId());
            writeD(item.getCount());
            writeD(item.getBaseItem().getSellPrice());
            writeH(item.getBaseItem().isStackable() ? 1 : 0);
        }
    }
}
