package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class PlayableActorInfo extends AbstractSendablePacket implements IServerPacket {

    private final PlayableCharacter _character;
    public PlayableActorInfo(PlayableCharacter character)
    {
        super();
        _character = character;
        build();
    }

    private void build() {
        writeH(ServerPackets.PLAYABLE_ACTOR_INFO);
        writeD(_character.getObjectId());
        writeH(_character.getRace().getValue());
        writeH(_character.getCharacterClass().getValue());

        writeD(_character.getLocationX());
        writeD(_character.getLocationY());
        writeD(_character.getLocationZ());

        writeD(_character.getBaseStats().getCollisionHeight());
        writeD(_character.getBaseStats().getCollisionRadius());

        writeS(_character.getName());

    }
}
