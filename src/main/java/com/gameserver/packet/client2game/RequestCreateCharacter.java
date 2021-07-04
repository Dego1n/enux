package com.gameserver.packet.client2game;

import com.gameserver.database.DBDatastore;
import com.gameserver.database.repository.actor.CharacterRepository;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.database.entity.actor.Character;
import com.gameserver.packet.game2client.CharacterList;

public class RequestCreateCharacter extends AbstractReceivablePacket {

    private final ClientListenerThread _clientListenerThread;

    public RequestCreateCharacter(ClientListenerThread clientListenerThread, byte[] packet)
    {
        super(packet);
        _clientListenerThread = clientListenerThread;
        handle();
    }
    private void handle() {
        String name = readS();
        int race = readH();
        int characterClass = readH();

        //TODO: Проверять полученные данные валидатором

        CharacterRepository characterDao = new CharacterRepository();
        Character character = new Character();

        character.setName(name);
        switch (race)
        {
            case 0:
                character.setRace(Race.Human);
                break;
            case 1:
                character.setRace(Race.Elf);
                break;
        }

        switch(characterClass)
        {
            case 0:
                character.setCharacterClass(CharacterClass.Fighter);
                break;
            case 1:
                character.setCharacterClass(CharacterClass.Mystic);
                break;
        }

        //TODO: change location
        character.setLocationX(-41560);
        character.setLocationY(202984);
        character.setLocationZ(2150);
        character.setAccountId("1"); // TODO set actual ID

        DBDatastore.getInstance().save(character);
        _clientListenerThread.sendPacket(new CharacterList(characterDao.getCharactersByAccount("1"))); //TODO: change account
    }
}
