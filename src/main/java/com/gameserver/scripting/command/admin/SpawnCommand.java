package com.gameserver.scripting.command.admin;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.template.NPC;

public class SpawnCommand  extends AbstractAdminCommand{

    @Override
    public void execute(PlayableCharacter character, String command) {
        if (!command.contains(" ")) {
            character.sendPacket(new SystemMessage("Invalid command usage. Use: //spawn <npc_id>"));
            return;
        }
        int npc_id = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
        NPC npc = DataEngine.getInstance().getNPCById(npc_id);
        if (npc == null) {
            character.sendPacket(new SystemMessage("Npc " + npc_id + " not found!"));
            return;
        }
        NPCActor actor = new NPCActor(
                npc_id,
                character.getLocationX(),
                character.getLocationY(),
                character.getLocationZ()
        );
        World.getInstance().spawnNpcAndBroadcast(actor);
    }

    @Override
    public String getCommand() {
        return "spawn";
    }
}
