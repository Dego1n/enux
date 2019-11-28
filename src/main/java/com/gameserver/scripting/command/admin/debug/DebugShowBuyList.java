package com.gameserver.scripting.command.admin.debug;

import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.BuyList;
import com.gameserver.scripting.command.admin.AbstractAdminCommand;

public class DebugShowBuyList extends AbstractAdminCommand {
    @Override
    public void execute(PlayableCharacter character, String command) {

        character.sendPacket(new BuyList(DataEngine.getInstance().getBuyListById(1),character.getInventory()));
    }

    @Override
    public String getCommand() {
        return "show-buylist";
    }
}
