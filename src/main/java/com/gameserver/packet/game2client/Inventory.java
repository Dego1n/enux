package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class Inventory extends AbstractSendablePacket implements IServerPacket {

    public Inventory()
    {
        super();
        build();
    }

    private void build() {
        writeH(ServerPackets.INVENTORY);
        writeD(3); //item size

        //3 Swords
        writeD(1);
        writeD(1);
        writeD(1);

    }
}
