package com.gameserver.database.presets;

import com.gameserver.database.dao.actor.CharacterDao;
import com.gameserver.database.dao.actor.NPCDao;
import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.entity.actor.NPCActor;
import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NPCPresets {

    private static final Logger log = LoggerFactory.getLogger(NPCPresets.class);

    public static void Load()
    {
        NPCDao npcDao = new NPCDao();

        if(npcDao.getNpcById(100) == null)
        {
            log.info("Creating NPC Arissa");

            NPCActor npcActor = new NPCActor();

            npcActor.setId(100);
            npcActor.setTemplateId(100);

            npcActor.setLocationX(5680);
            npcActor.setLocationY(6090);
            npcActor.setLocationZ(-5740);
            npcActor.setName("Arissa");
            npcActor.setRace(Race.Human); //TODO: change this

            npcDao.save(npcActor);
        }
        else
        {
            log.info("NPC Arissa already exists, skipping");
        }
    }
}
