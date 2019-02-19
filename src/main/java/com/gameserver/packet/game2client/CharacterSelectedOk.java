package com.gameserver.packet.game2client;

import com.gameserver.database.entity.character.Character;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class CharacterSelectedOk  extends AbstractSendablePacket implements IServerPacket {

    private Character character;
    public CharacterSelectedOk(Character character)
    {
        super();

        this.character = character;

        build();
    }
    @Override
    public void build() {
        writeH(0x03);

    }
}
