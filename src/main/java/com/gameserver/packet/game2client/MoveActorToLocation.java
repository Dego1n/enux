package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class MoveActorToLocation extends AbstractSendablePacket implements IServerPacket {

    private int objectId;
    private int x;
    private int y;
    private int z;

    public MoveActorToLocation(int objectId, int x, int y, int z)
    {
        super();
        this.objectId = objectId;
        this.x = x;
        this.y = y;
        this.z = z;
        build();
    }

    @Override
    public void build() {
        writeH(0x05);
        writeD(objectId);
        writeD(x);
        writeD(y);
        writeD(z);
    }
}
