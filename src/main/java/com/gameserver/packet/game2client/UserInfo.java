package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

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

        writeH(ServerPackets.USER_INFO);
        writeD(_character.getObjectId());
        writeH(_character.getRace().getValue());
        writeH(_character.getCharacterClass().getValue());
        writeD(_character.getLocationX());
        writeD(_character.getLocationY());
        writeD(_character.getLocationZ());

        writeS(_character.getName());
    }
}
