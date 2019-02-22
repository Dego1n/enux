package com.gameserver.database.presets;

import com.gameserver.database.dao.character.CharacterDao;
import com.gameserver.database.entity.character.Character;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;

public class CharacterPresets {

    public static void Load()
    {
        CharacterDao characterDao = new CharacterDao();

        if(characterDao.getCharacterByName("PlayTest") == null)
        {
            System.out.println("Creating character PlayTest");

            Character character = new Character();

            character.setAccountId(1);

            /* 165 -310 184 */

            character.setLocationX(165);
            character.setLocationY(-310);
            character.setLocationZ(184);
            character.setName("PlayTest");
            character.setRace(Race.Human);
            character.setCharacterClass(CharacterClass.Fighter);

            characterDao.save(character);
        }
        else
        {
            System.out.println("Character already exists, skipping");
        }
    }
}
