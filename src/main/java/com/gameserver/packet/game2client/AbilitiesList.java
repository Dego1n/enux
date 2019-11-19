package com.gameserver.packet.game2client;

import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.Map;

public class AbilitiesList extends AbstractSendablePacket implements IServerPacket {

    private final PlayableCharacter _character;

    public AbilitiesList(PlayableCharacter character)
    {
        super();
        this._character = character;
        build();
    }

    private void build() {
        writeH(ServerPackets.ABILITIES_LIST);
        Map<Ability, Integer> abilities = _character.getAbilities();
        writeD(abilities.size()); //Count
        //Loop start
        abilities.forEach(
            (ability,level) ->
            {
                writeD(ability.getId()); //Skill ID
                writeD(level); //Level
            }
        );
        //Loop end
    }
}