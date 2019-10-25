package com.gameserver.scripting.command.admin;

import com.gameserver.database.entity.spawn.Spawn;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
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
        int npc_id = Integer.valueOf(command.substring(command.indexOf(" ") + 1));
        NPC npc = DataEngine.getInstance().getNPCById(npc_id);
        if (npc == null) {
            character.sendPacket(new SystemMessage("Npc " + npc_id + " not found!"));
            return;
        }
        Spawn spawn = new Spawn();
        spawn.setActorId(npc_id);
        int loc_x = character.getLocationX();
        int loc_y = character.getLocationY();
        int loc_z = character.getLocationZ();
        spawn.setLocationX(loc_x);
        spawn.setLocationY(loc_y);
        spawn.setLocationZ(loc_z);
        World.getInstance().spawnNpcAndBroadcast(spawn);
    }

    @Override
    public String getCommand() {
        return "spawn";
    }
}
