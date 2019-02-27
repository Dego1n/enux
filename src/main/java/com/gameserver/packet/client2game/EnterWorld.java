package com.gameserver.packet.client2game;

import com.gameserver.model.World;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.PlayableActorInfo;
import com.gameserver.packet.game2client.UserInfo;

import java.util.List;

public class EnterWorld extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public EnterWorld(ClientListenerThread listenerThread, byte[] packet) {
        super(listenerThread, packet);
        _clientListenerThread = listenerThread;
        handle();
    }

    @Override
    protected void handle() {

        if(_clientListenerThread.playableCharacter != null) {

            PlayableCharacter character = _clientListenerThread.playableCharacter;
            _clientListenerThread.sendPacket(new UserInfo(character));
            World.getInstance().addPlayerToWorld(character);

            for (PlayableCharacter actor : character.nearbyPlayers())
            {
                _clientListenerThread.sendPacket(new PlayableActorInfo(actor));
            }
        }
    }
}
