package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSpawnRowToServerCommand extends AbstractAdminCommand {

    private static final Logger log = LoggerFactory.getLogger(LogSpawnRowToServerCommand.class);

    @Override
    public void execute(PlayableCharacter character, String command) {
        BaseActor actor = character.getTarget();

        if(actor == null)
        {
            character.sendPacket(new SystemMessage("Target needs to be selected!"));
            return;
        }
        log.info("- { npc_id: {}, x: {}, y: {}, z: {} }", actor.getTemplateId(), actor.getLocationX(), actor.getLocationY(), actor.getLocationZ());
        character.sendPacket(new SystemMessage("Logged to server!"));

    }

    @Override
    public String getCommand() {
        return "log-spawn";
    }
}
