package com.gameserver.model.actor;

import com.gameserver.database.entity.character.Character;
import com.gameserver.network.thread.ClientListenerThread;

public class PlayableCharacter {

    private ClientListenerThread clientListenerThread;

    private int id;
    private int locationX;
    private int locationY;
    private int locationZ;
    private String name;

    public PlayableCharacter(ClientListenerThread clientListenerThread, Character character)
    {
        this.clientListenerThread = clientListenerThread;

        id = character.getId();
        locationX = character.getLocationX();
        locationY = character.getLocationY();
        locationZ = character.getLocationZ();

        name = character.getName();
    }
}
