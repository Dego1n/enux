package com.gameserver.scripting.command.admin.debug;

import com.gameserver.geodata.GeoEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.DebugDrawSphere;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.scripting.command.admin.AbstractAdminCommand;

public class DebugNearestGeodataCommand extends AbstractAdminCommand {
    @Override
    public void execute(PlayableCharacter character, String command) {
        int loc_x = character.getLocationX();
        int loc_y = character.getLocationY();
        int offset = 100;
        for (int x = 0; x<=10; x++)
        {
            for (int y = 0; y <= 10; y++) {
                character.sendPacket(new DebugDrawSphere(loc_x - x * offset, loc_y - y * offset, Math.round(GeoEngine.getInstance().getNearestZ(loc_x - x * offset, loc_y - y * offset))));
                character.sendPacket(new DebugDrawSphere(loc_x + x * offset, loc_y - y * offset, Math.round(GeoEngine.getInstance().getNearestZ(loc_x + x * offset, loc_y - y * offset))));
                character.sendPacket(new DebugDrawSphere(loc_x + x * offset, loc_y + y * offset, Math.round(GeoEngine.getInstance().getNearestZ(loc_x + x * offset, loc_y + y * offset))));
                character.sendPacket(new DebugDrawSphere(loc_x - x * offset, loc_y + y * offset, Math.round(GeoEngine.getInstance().getNearestZ(loc_x - x * offset, loc_y + y * offset))));
            }
        }
        character.sendPacket(new SystemMessage("Sent nearest geodata!"));
    }

    @Override
    public String getCommand() {
        return "debug-geodata";
    }
}
