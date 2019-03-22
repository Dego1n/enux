package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class Dialog extends AbstractSendablePacket implements IServerPacket {

    private String _dialog;

    public Dialog(String dialog)
    {
        super();
        _dialog = dialog;
        build();
    }

    private void build() {
        writeH(ServerPackets.DIALOG);
        writeS(_dialog);
    }
}
