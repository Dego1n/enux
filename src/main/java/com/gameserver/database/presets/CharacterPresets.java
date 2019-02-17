package com.gameserver.database.presets;

import com.gameserver.database.dao.character.CharacterDao;
import com.gameserver.database.entity.character.Character;

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

            characterDao.save(character);
        }
        else
        {
            System.out.println("Character already exists, skipping");
        }
    }
}
