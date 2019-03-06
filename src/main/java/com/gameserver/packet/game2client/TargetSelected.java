package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class TargetSelected extends AbstractSendablePacket implements IServerPacket {

    private int objectId;

    public TargetSelected(int objectId)
    {
        super();

        this.objectId = objectId;

        build();
    }

    @Override
    public void build() {
        writeH(0x0A);

        writeD(objectId);
    }
}
