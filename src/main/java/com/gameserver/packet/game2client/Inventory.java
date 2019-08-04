package com.gameserver.packet.game2client;

import com.gameserver.model.actor.playable.equip.EquipInfo;
import com.gameserver.model.item.Item;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;
import com.gameserver.template.item.BaseItem;

import java.util.List;

public class Inventory extends AbstractSendablePacket implements IServerPacket {

    private List<Item> _items;
    private EquipInfo _equipInfo;

    public Inventory(List<Item> items, EquipInfo equipInfo)
    {
        super();
        _items = items;
        _equipInfo = equipInfo;
        build();
    }

    private void build() {
        writeH(ServerPackets.INVENTORY);

        writeD(_equipInfo.getRightHand().getItemId()); //Right hand Item id
        writeD(_equipInfo.getRightHand().getObjectId()); //Right hand Object ID

        writeD(_items.size()); //item size
        for(Item item : _items) {
            writeD(item.getObjectId());
            writeD(item.getItemId()); //item id
            System.out.println("Sending item id "+item.getItemId());
        }
    }
}
