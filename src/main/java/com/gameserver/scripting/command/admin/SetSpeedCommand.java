package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.ActorInfo;
import com.gameserver.packet.game2client.SystemMessage;

public class SetSpeedCommand extends AbstractAdminCommand{
    @Override
    public void execute(PlayableCharacter character, String command) {
        if (!command.contains(" ")) {
            character.sendPacket(new SystemMessage("Invalid command usage. Use: //speed <value>"));
            return;
        }
        int speed = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
        character.getStats().setMoveSpeed(speed);
        character.sendPacket(new ActorInfo(character,character));
        character.sendPacket(new SystemMessage("Your speed is set to "+speed));
    }

    @Override
    public String getCommand() {
        return "speed";
    }
}
