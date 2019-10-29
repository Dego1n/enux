package com.gameserver.scripting.command.admin.debug;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.scripting.command.admin.AbstractAdminCommand;

public class DebugAttackTargetCommand extends AbstractAdminCommand {
    @Override
    public void execute(PlayableCharacter character, String command) {
        if(character.getTarget() == null)
        {
            character.sendPacket(new SystemMessage("Select a target!"));
            return;
        }
        character.attack(character.getTarget());
    }

    @Override
    public String getCommand() {
        return "debug-attack";
    }
}
