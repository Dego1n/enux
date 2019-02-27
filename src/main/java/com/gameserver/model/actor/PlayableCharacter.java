package com.gameserver.model.actor;

import com.gameserver.database.entity.character.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;
import com.gameserver.model.World;
import com.gameserver.network.thread.ClientListenerThread;

import java.util.List;

public class PlayableCharacter {

    private ClientListenerThread clientListenerThread;

    private int id;
    private int locationX;
    private int locationY;
    private int locationZ;
    private String name;

    private Race race;
    private CharacterClass characterClass;

    public PlayableCharacter(ClientListenerThread clientListenerThread, Character character)
    {
        this.clientListenerThread = clientListenerThread;

        id = character.getId();
        locationX = character.getLocationX();
        locationY = character.getLocationY();
        locationZ = character.getLocationZ();

        name = character.getName();

        race = character.getRace();
        characterClass = character.getCharacterClass();
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

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }


    public List<PlayableCharacter> nearbyPlayers()
    {
        return World.getInstance().getPlayersInRadius(this,10000);
    }
}
