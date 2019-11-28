package com.gameserver.packet.client2game;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.item.Item;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.Inventory;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.template.buylist.BuyList;
import com.gameserver.template.item.BaseItem;

import java.util.HashMap;
import java.util.Map;

public class RequestBuy extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;
    public RequestBuy(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        PlayableCharacter pc = _clientListenerThread.playableCharacter;

        int buy_list_id = readD();
        int size = readD();
        Map<Integer,Integer> items = new HashMap<>();
        for(int i = 0; i< size; i++)
        {
            int item_id = readD();
            int amount = readD();
            items.put(item_id, amount);
        }
        BuyList buyList = DataEngine.getInstance().getBuyListById(buy_list_id);
        if(buyList == null)
        {
            //TODO: security audit
            return;
        }
        //TODO: check if buyListId can be used at character position! IMPORTANT!
        Map<BaseItem, Integer> requestedItems = new HashMap<>();
        int total_price = 0;
        for(Map.Entry<Integer,Integer> requestedItem : items.entrySet())
        {
            BaseItem item = DataEngine.getInstance().getItemById(requestedItem.getKey());
            requestedItems.put(item, requestedItem.getValue());
            int price = buyList.getPriceForItem(item.getId());
            if(price == -1)
            {
                //TODO: security audit
                return;
            }
            total_price += price * requestedItem.getValue();

        }
        Item currencyItem = pc.getItemFromInventoryById(buyList.getCurrencyId());
        if(currencyItem == null)
        {
            //TODO: security audit??
            return;
        }
        if(currencyItem.getCount() < total_price)
        {
            pc.sendPacket(new SystemMessage("Not enough currency"));
            return;
        }
        currencyItem.setCount(currencyItem.getCount() - total_price);
        for(Map.Entry<BaseItem, Integer> requestedItem : requestedItems.entrySet())
        {
            Item inventory_item = new Item(requestedItem.getKey(),requestedItem.getValue());
            pc.getInventory().add(inventory_item);
        }
        pc.sendPacket(new Inventory(pc.getInventory(),pc.getEquipInfo()));
    }
}
