package com.gameserver.scripting.command.admin;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.ActorInfo;
import com.gameserver.packet.game2client.PlayableActorInfo;
import com.gameserver.packet.game2client.SystemMessage;

public class SendActorInfoCommand extends AbstractAdminCommand{
    @Override
    public void execute(PlayableCharacter character, String command) {
        for (BaseActor actor : character.nearbyActors())
        {
            if(actor instanceof PlayableCharacter) {
                character.sendPacket(new PlayableActorInfo((PlayableCharacter) actor));
                ((PlayableCharacter)actor).getClientListenerThread().sendPacket(new PlayableActorInfo(character));
            }
            else if (actor instanceof NPCActor)
            {
                character.sendPacket(new ActorInfo(actor, character));
            }
        }
        character.sendPacket(new SystemMessage("Sent nearby actors info"));
    }

    @Override
    public String getCommand() {
        return "send-actors";
    }
}
