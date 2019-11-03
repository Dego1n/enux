package com.gameserver.packet.client2game;

import com.gameserver.model.World;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.item.Item;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.LootDataUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class RequestTakeLoot extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(RequestTakeLoot.class);

    private ClientListenerThread clientListenerThread;

    public RequestTakeLoot(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {

        int object_id = readD();
        int item_object_id = readD();

        log.warn("Client trying to take loot. object_id: "+object_id+"; item_object_id: "+item_object_id);
        BaseActor targetActor = World.getInstance().getActorByObjectId(object_id);
        if(targetActor instanceof NPCActor)
        {
            ((NPCActor) targetActor).lootItem(clientListenerThread.playableCharacter, item_object_id);
        }
        clientListenerThread.sendPacket(new LootDataUpdated(targetActor));
    }
}
