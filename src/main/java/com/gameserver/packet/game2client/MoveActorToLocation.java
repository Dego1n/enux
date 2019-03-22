package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

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

    private void build() {
        writeH(ServerPackets.MOVE_ACTOR_TO_LOCATION);
        writeD(objectId);
        writeD(x);
        writeD(y);
        writeD(z);
    }
}
