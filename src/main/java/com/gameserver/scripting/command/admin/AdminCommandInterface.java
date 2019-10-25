package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.PlayableCharacter;

public interface AdminCommandInterface {

    void execute(PlayableCharacter character, String command);

    String getCommand();
}
