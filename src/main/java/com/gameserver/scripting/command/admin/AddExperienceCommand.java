/*
 * Author: Dego1n
 * 2.12.2019
 */

package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;

public class AddExperienceCommand  extends AbstractAdminCommand{

    @Override
    public void execute(PlayableCharacter character, String command) {
        if (!command.contains(" ")) {
            character.sendPacket(new SystemMessage("Invalid command usage. Use: //add_exp <exp amount>"));
            return;
        }
        int exp = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
        if (exp <= 0) {
            character.sendPacket(new SystemMessage("Exp amount should be >= 0"));
            return;
        }
        character.addExperience(exp);
    }

    @Override
    public String getCommand() {
        return "add_exp";
    }
}
