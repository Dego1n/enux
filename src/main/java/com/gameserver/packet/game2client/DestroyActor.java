package com.gameserver.packet.game2client;

/*
 * @author Dego1n
 * Copyright (c) 03.03.2019
 */

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class DestroyActor extends AbstractSendablePacket implements IServerPacket {

    private int _objectId;

    public DestroyActor(int objectId)
    {
        super();
        _objectId = objectId;
        build();
    }

    @Override
    public void build() {
        writeH(0x08);
        writeD(_objectId);
    }
}
