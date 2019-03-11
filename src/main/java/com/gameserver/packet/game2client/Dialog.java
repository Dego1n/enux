package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class Dialog extends AbstractSendablePacket implements IServerPacket {

    private String _dialog;

    public Dialog(String dialog)
    {
        super();
        _dialog = dialog;
        build();
    }

    @Override
    public void build() {
        writeH(0x0b);
        writeS(_dialog);
    }
}
