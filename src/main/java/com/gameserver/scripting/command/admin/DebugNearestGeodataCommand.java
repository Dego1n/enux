package com.gameserver.scripting.command.admin;

import com.gameserver.geodata.GeoEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.DebugDrawSphere;
import com.gameserver.packet.game2client.SystemMessage;

public class DebugNearestGeodataCommand extends AbstractAdminCommand{
    private final int multiplicator = 100;
    @Override
    public void execute(PlayableCharacter character, String command) {
        int loc_x = character.getLocationX();
        int loc_y = character.getLocationY();
        for (int x = 0; x<=10; x++)
        {
            for (int y = 0; y <= 10; y++) {
                character.sendPacket(new DebugDrawSphere(loc_x - x * multiplicator, loc_y - y * multiplicator, Math.round(GeoEngine.getInstance().getNearestZ(loc_x - x * multiplicator, loc_y - y * multiplicator))));
                character.sendPacket(new DebugDrawSphere(loc_x + x * multiplicator, loc_y - y * multiplicator, Math.round(GeoEngine.getInstance().getNearestZ(loc_x + x * multiplicator, loc_y - y * multiplicator))));
                character.sendPacket(new DebugDrawSphere(loc_x + x * multiplicator, loc_y + y * multiplicator, Math.round(GeoEngine.getInstance().getNearestZ(loc_x + x * multiplicator, loc_y + y * multiplicator))));
                character.sendPacket(new DebugDrawSphere(loc_x - x * multiplicator, loc_y + y * multiplicator, Math.round(GeoEngine.getInstance().getNearestZ(loc_x - x * multiplicator, loc_y + y * multiplicator))));
            }
        }
        character.sendPacket(new SystemMessage("Sent nearest geodata!"));
    }

    @Override
    public String getCommand() {
        return "debug-geodata";
    }
}
