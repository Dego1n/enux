package com.gameserver.packet.game2client;

import com.gameserver.database.entity.actor.Character;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

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
        writeH(ServerPackets.CHARACTER_SELECTED_OK);
        writeH(character.getRace().getValue());
    }
}
