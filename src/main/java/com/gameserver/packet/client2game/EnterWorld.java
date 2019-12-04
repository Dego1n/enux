package com.gameserver.packet.client2game;

import com.gameserver.config.Config;
import com.gameserver.model.World;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.*;

public class EnterWorld extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public EnterWorld(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {

        if(_clientListenerThread.playableCharacter != null) {

            PlayableCharacter character = _clientListenerThread.playableCharacter;
            character.sendPacket(new UserInfo(character));
            character.sendPacket(new AbilitiesList(character));
            character.sendPacket(new Inventory(character.getInventory(), character.getEquipInfo()));
            character.sendPacket(new StatusInfo(character));
            character.sendPacket(new PCActorInfo(character));
            World.getInstance().addPlayerToWorld(character);
            for (BaseActor actor : character.nearbyActors())
            {
                if(actor instanceof PlayableCharacter) {
                    _clientListenerThread.sendPacket(new PlayableActorInfo((PlayableCharacter) actor));
                    ((PlayableCharacter)actor).getClientListenerThread().sendPacket(new PlayableActorInfo(character));
                }
                else if (actor instanceof NPCActor)
                {
                    _clientListenerThread.sendPacket(new ActorInfo(actor));
                }
            }
            character.sendPacket(new SystemMessage("Entered to server: "+ Config.GAME_SERVER_NAME));
            character.sendPacket(new SystemMessage("Welcome to the world!"));
            character.sendPacket(new SystemMessage("[Debug] Your level: "+character.getLevel()));
            character.sendPacket(new SystemMessage("[Debug] Your Experience: "+character.getCurrentExperience()));
        }
    }
}
