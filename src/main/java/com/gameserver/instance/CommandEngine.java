package com.gameserver.instance;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.scripting.command.admin.*;
import com.gameserver.scripting.command.admin.debug.DebugAttackTargetCommand;
import com.gameserver.scripting.command.admin.debug.DebugNearestGeodataCommand;
import com.gameserver.scripting.command.admin.debug.DebugShowBuyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandEngine {

    private static final Logger log = LoggerFactory.getLogger(CommandEngine.class);

    private static CommandEngine _instance;

    public static CommandEngine getInstance()
    {
        if(_instance == null)
            _instance = new CommandEngine();

        return _instance;
    }

    private final Map<String, AbstractAdminCommand> _adminCommands = new HashMap<>();

    private CommandEngine()
    {
        this.registerCommand(new HelloCommand());
        this.registerCommand(new SendActorInfoCommand());
        this.registerCommand(new SpawnCommand());
        this.registerCommand(new DebugNearestGeodataCommand());
        this.registerCommand(new DebugAttackTargetCommand());
        this.registerCommand(new DebugShowBuyList());
        this.registerCommand(new AddExperienceCommand());
        log.info("Loaded {} admin commands", _adminCommands.size());
    }

    private void registerCommand(AbstractAdminCommand command)
    {
        _adminCommands.put(command.getCommand(), command);
    }

    public void handleAdminCommand(PlayableCharacter character, String command)
    {
        String searchCommand = command.contains(" ") ? command.substring(0, command.indexOf(" ")) : command;
        AbstractAdminCommand adminCommand = _adminCommands.get(searchCommand);
        if(adminCommand != null)
        {
            try {
                adminCommand.execute(character, command);
            } catch (Exception e)
            {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        else
        {
            log.info("Character "+character.getName()+" requested not existing admin command: "+command);
            //TODO: security audit
        }
    }

}
