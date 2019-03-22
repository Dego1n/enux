package com.gameserver.packet.client2game;

import com.gameserver.model.World;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.ActorInfo;
import com.gameserver.packet.game2client.PlayableActorInfo;
import com.gameserver.packet.game2client.UserInfo;

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
            _clientListenerThread.sendPacket(new UserInfo(character));
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
        }
    }
}
