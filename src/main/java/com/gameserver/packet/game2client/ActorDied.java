package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.item.Item;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.List;
import java.util.Random;

public class ActorDied extends AbstractSendablePacket implements IServerPacket {

    private BaseActor actor;

    public ActorDied(BaseActor actor)
    {
        super();
        this.actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.ACTOR_DIED);
        writeD(actor.getObjectId());
        writeD(10); //Монтаж смерти -> через сколько вызвать Destroy актора
        if(actor instanceof NPCActor)
        {
            List<Item> items = ((NPCActor) actor).getLootData();
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
