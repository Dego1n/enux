package com.gameserver.database.presets;

import com.gameserver.database.dao.character.CharacterDao;
import com.gameserver.database.entity.character.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterPresets {

    private static final Logger log = LoggerFactory.getLogger(CharacterPresets.class);

    public static void Load()
    {
        CharacterDao characterDao = new CharacterDao();

        if(characterDao.getCharacterByName("PlayTest") == null)
        {
            log.info("Creating character PlayTest");

            Character character = new Character();

            character.setAccountId(1);

            character.setLocationX(2820);
            character.setLocationY(3990);
            character.setLocationZ(-5740);
            character.setName("PlayTest");
            character.setRace(Race.Human);
            character.setCharacterClass(CharacterClass.Fighter);

            characterDao.save(character);
        }
        else
        {
            log.info("Character already exists, skipping");
        }
    }
}
