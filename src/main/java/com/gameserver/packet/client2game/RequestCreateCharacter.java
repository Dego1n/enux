package com.gameserver.packet.client2game;

import com.gameserver.database.dao.actor.CharacterDao;
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
        super(clientListenerThread,packet);
        _clientListenerThread = clientListenerThread;
        handle();
    }
    @Override
    protected void handle() {
        String name = readS();
        int race = readH();
        int characterClass = readH();

        //TODO: Проверять полученные данные валидатором

        CharacterDao characterDao = new CharacterDao();
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
        character.setLocationX(2820);
        character.setLocationY(3990);
        character.setLocationZ(-5740);

        character.setAccountId(1); //TODO change to account

        characterDao.save(character);
        _clientListenerThread.sendPacket(new CharacterList(characterDao.getCharactersByAccount(1))); //TODO: change account
    }
}
