package com.gameserver.database.presets;

import com.gameserver.database.DBDatastore;
import com.gameserver.database.repository.actor.CharacterRepository;
import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterPresets {

    private static final Logger log = LoggerFactory.getLogger(CharacterPresets.class);

    public static void Load()
    {
        CharacterRepository characterRepository = new CharacterRepository();

        if(characterRepository.getCharacterByName("PlayTest") == null)
        {
            log.info("Creating actor PlayTest");

            Character character = new Character(
                    "1",
                    "PlayTest",
                    Race.Human,
                    CharacterClass.Fighter,
                    -41560,
                    202984,
                    2150
            );
            DBDatastore.getInstance().save(character);
        }
        else
        {
            log.info("Character already exists, skipping");
        }
    }
}
