package com.gameserver.database.presets;

import com.gameserver.database.dao.spawn.SpawnDao;
import com.gameserver.database.entity.spawn.Spawn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpawnPresets {

    private static final Logger log = LoggerFactory.getLogger(SpawnPresets.class);

    public static void Load()
    {
        SpawnDao spawnDao = new SpawnDao();

        if(spawnDao.getFirstSpawnForActorId(100) == null)
        {
            log.info("Spawning npc Arissa");

            Spawn actorSpawn = new Spawn();

            actorSpawn.setActorId(100);

            actorSpawn.setLocationX(5680);
            actorSpawn.setLocationY(6090);
            actorSpawn.setLocationZ(-5740);

            spawnDao.save(actorSpawn);
        }
        else
        {
            log.info("NPC Arissa already exists in spawn list, skipping");
        }

        if(spawnDao.getFirstSpawnForActorId(101) == null)
        {
            log.info("Spawning npc Zombie");

            Spawn actorSpawn = new Spawn();

            actorSpawn.setActorId(101);

            actorSpawn.setLocationX(5760);
            actorSpawn.setLocationY(3380);
            actorSpawn.setLocationZ(-5740);

            spawnDao.save(actorSpawn);
        }
        else
        {
            log.info("NPC Zombie already exists in spawn list, skipping");
        }
    }
}
