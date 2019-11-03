package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.item.Item;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.List;

public class LootDataUpdated extends AbstractSendablePacket implements IServerPacket {

    private BaseActor _actor;

    public LootDataUpdated(BaseActor actor)
    {
        super();
        _actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.LOOT_DATA_UPDATED);
        writeD(_actor.getObjectId());
        if(_actor instanceof NPCActor)
        {
            List<Item> items = ((NPCActor) _actor).getLootData();
            writeH(items.size());
            for(Item item :items)
            {
                writeD(item.getObjectId());
                writeD(item.getItemId());
                writeD(0);//TODO: count
            }
        }
        else
        {
            writeH(0);
        }
    }
}