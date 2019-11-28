package com.gameserver.packet.client2game;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.item.Item;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.template.buylist.BuyList;

import java.util.HashMap;
import java.util.Map;

public class RequestSell extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public RequestSell(ClientListenerThread listenerThread, byte[] packet) {
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
            int object_id = readD();
            int amount = readD();
            items.put(object_id, amount);
        }
        BuyList buyList = DataEngine.getInstance().getBuyListById(buy_list_id);
        if(buyList == null)
        {
            //TODO: security audit
            return;
        }
        //TODO: check if buyListId can be used at character position! IMPORTANT!
        int currencyReward = 0;
        for(Map.Entry<Integer,Integer> requestedItem : items.entrySet())
        {
            Item inventoryItem = pc.getInventory().stream().filter(item -> item.getObjectId() == requestedItem.getKey()).findFirst().orElse(null);
            if(inventoryItem == null)
            {
                //TODO: security audit
                continue;
            }
            if(inventoryItem.getCount() < requestedItem.getValue())
            {
                //TODO: security audit
                continue;
            }
            int sellPrice = inventoryItem.getBaseItem().getSellPrice();
            if(inventoryItem.getCount() > requestedItem.getValue())
            {
                inventoryItem.setCount(inventoryItem.getCount() - requestedItem.getValue());
                currencyReward += sellPrice * requestedItem.getValue();
            }
            else {
                pc.getInventory().remove(inventoryItem);
                currencyReward += sellPrice;
            }
        }
        pc.giveItem(DataEngine.getInstance().getItemById(buyList.getCurrencyId()),currencyReward);
        pc.sendPacket(new SystemMessage("Sold items for "+currencyReward+" "+DataEngine.getInstance().getItemById(buyList.getCurrencyId()).getName()));
    }
}