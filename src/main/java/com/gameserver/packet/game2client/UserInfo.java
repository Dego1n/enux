package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class UserInfo extends AbstractSendablePacket implements IServerPacket {

    private PlayableCharacter _character;

    public UserInfo(PlayableCharacter character)
    {
        super();
        _character = character;
        build();
    }

    @Override
    public void build() {

        writeH(0x06);
        writeD(_character.getId());
        writeD(_character.getLocationX());
        writeD(_character.getLocationY());
        writeD(_character.getLocationZ());

        writeS(_character.getName());
    }
}
