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

    public ClientListenerThread getClientListenerThread() {
        return clientListenerThread;
    }

    public void setClientListenerThread(ClientListenerThread clientListenerThread) {
        this.clientListenerThread = clientListenerThread;
    }

    public int getId() {
        return id;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(int locationZ) {
        this.locationZ = locationZ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
