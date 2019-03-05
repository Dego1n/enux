package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;

import com.gameserver.database.entity.actor.Character;

import java.util.List;

public class CharacterList  extends AbstractSendablePacket implements IServerPacket {

    private List<Character> _characters;

    public CharacterList(List<Character> characters)
    {
        super();

        _characters = characters;

        build();
    }

    @Override
    public void build() {

        writeH(0x02);

        writeH(_characters.size());

        for(Character character : _characters)
        {
            writeD(character.getId());
            writeS(character.getName());
            writeD(character.getLocationX());
            writeD(character.getLocationY());
            writeD(character.getLocationZ());
            writeH(character.getRace().getValue());
            writeH(character.getCharacterClass().getValue());
        }
    }
}
