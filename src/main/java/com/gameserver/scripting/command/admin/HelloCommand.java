package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;

public class HelloCommand extends AbstractAdminCommand {

    private final String _command = "hello";

    @Override
    public void execute(PlayableCharacter character, String command) {
        character.sendPacket(new SystemMessage("Received hello command"));
    }

    public String getCommand()
    {
        return _command;
    }

}
