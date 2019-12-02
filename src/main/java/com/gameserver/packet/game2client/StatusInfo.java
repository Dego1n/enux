package com.gameserver.packet.game2client;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class StatusInfo extends AbstractSendablePacket implements IServerPacket {

    private final PlayableCharacter _character;
    public StatusInfo(PlayableCharacter character)
    {
        super();
        _character = character;
        build();
    }

    private void build() {
        writeH(ServerPackets.STATUS_INFO);
        writeD(_character.getObjectId());
        writeD(_character.getLevel());
        writeD((int)_character.getCurrentHp()); //TODO: float when client support it
        writeD((int)_character.getMaxHp()); //TODO: float when client support it
        writeD((int)_character.getCurrentMp()); //TODO: float when client support it
        writeD((int)_character.getMaxMp()); //TODO: float when client support it
        writeD(DataEngine.getInstance().getStartExperienceForLevel(_character.getLevel()));
        writeD(_character.getCurrentExperience());
        writeD(DataEngine.getInstance().getExperienceValueForNextLevel(_character.getLevel()));
    }


}
