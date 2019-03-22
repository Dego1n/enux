package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class TargetSelected extends AbstractSendablePacket implements IServerPacket {

    private int objectId;

    public TargetSelected(int objectId)
    {
        super();

        this.objectId = objectId;

        build();
    }

    private void build() {
        writeH(ServerPackets.TARGET_SELECTED);

        writeD(objectId);
    }
}
