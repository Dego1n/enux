package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class DebugDrawSphere extends AbstractSendablePacket implements IServerPacket {

    private final int loc_x;
    private final int loc_y;
    private final int loc_z;
    private final int radius;
    private final int segments;
    private final int duration;
    private final int thickness;

    public DebugDrawSphere(int loc_x, int loc_y, int loc_z)
    {
        super();
        this.loc_x = loc_x;
        this.loc_y = loc_y;
        this.loc_z = loc_z;
        this.radius = 5;
        this.segments = 12;
        this.duration = 15;
        this.thickness = 0;
        build();
    }

    private void build()
    {
        writeH(ServerPackets.DEBUG_DRAW_SPHERE);
        writeD(this.loc_x);
        writeD(this.loc_y);
        writeD(this.loc_z);
        writeD(this.radius);
        writeD(this.segments);
        writeD(this.duration);
        writeD(this.thickness);
    }
}
