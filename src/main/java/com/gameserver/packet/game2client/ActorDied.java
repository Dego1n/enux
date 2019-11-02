package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

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
        Random rnd = new Random();
        int loot_count = rnd.nextInt(5);
        writeH(loot_count);
        System.out.println("Sending loot count: "+loot_count);
        for(int i = 0; i<loot_count; i++)
        {
            writeD(-1); //object_id TODO:
            Random rnd2 = new Random();
            writeD(rnd2.nextInt(2)+1);//item _id
            writeD(1); //count

        }
    }
}
