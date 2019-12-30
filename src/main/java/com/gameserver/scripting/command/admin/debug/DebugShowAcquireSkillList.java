package com.gameserver.scripting.command.admin.debug;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.AcquireSkillList;
import com.gameserver.scripting.command.admin.AbstractAdminCommand;

public class DebugShowAcquireSkillList extends AbstractAdminCommand {
    @Override
    public void execute(PlayableCharacter character, String command) {
        character.sendPacket(new AcquireSkillList(character,DataEngine.getInstance().getAbilityTreeByClassId(character.getCharacterClass().getValue())));
    }

    @Override
    public String getCommand() {
        return "asl";
    }
}
