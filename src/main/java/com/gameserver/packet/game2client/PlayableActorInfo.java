package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

public class PlayableActorInfo extends AbstractSendablePacket implements IServerPacket {

    private PlayableCharacter _character;
    public PlayableActorInfo(PlayableCharacter character)
    {
        super();
        _character = character;
        build();
    }

    @Override
    public void build() {

        writeH(0x07);
        writeH(_character.getId());
        writeH(_character.getRace().getValue());
        writeH(_character.getCharacterClass().getValue());

        writeD(_character.getLocationX());
        writeD(_character.getLocationY());
        writeD(_character.getLocationZ());

        writeS(_character.getName());

    }
}
