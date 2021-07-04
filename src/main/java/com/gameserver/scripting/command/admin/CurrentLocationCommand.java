package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;

public class CurrentLocationCommand extends AbstractAdminCommand {

    @Override
    public void execute(PlayableCharacter character, String command) {
        character.sendPacket(
                new SystemMessage(
                        String.format("Server location: X:%d Y:%d Z:%d", character.getLocationX(), character.getLocationY(), character.getLocationZ())
                )
        );
    }

    @Override
    public String getCommand() {
        return "loc";
    }
}
